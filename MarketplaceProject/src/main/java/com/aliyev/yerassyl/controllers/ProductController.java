package com.aliyev.yerassyl.controllers;


import com.aliyev.yerassyl.facade.ProductFacade;
import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/products")
public class ProductController {

    ProductFacade productFacade;

    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Operation(summary = "Получить все продукты", description = "Возвращает все продукты")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("Request to get all products");
        return ResponseEntity.ok(productFacade.getAllProducts());
    }


    @Operation(summary = "Получить продукт по ID", description = "Возвращает продукт по ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("Request to get product with id {}", id);
        return productFacade.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать продукт", description = "Создает продукт")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult br) {
        log.debug("Request to create product");
        return new ResponseEntity<>(productFacade.createProduct(productDTO, br), HttpStatus.CREATED);
    }

    @Operation(summary = "Создать продукты", description = "Создает несколько продуктов ")
    @PostMapping("/batch")
    public ResponseEntity<List<ProductDTO>> createProducts(@RequestBody List<ProductDTO> productDTOs) {
        log.debug("Request to create products");
        List<ProductDTO> created = productFacade.createProducts(productDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Обновить продукт по ID", description = "Обновляет продукт по ID")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDTO dto) {
        log.debug("Request to update product with id {}", id);
        return productFacade.updateProduct(id, dto)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить продукт по ID", description = "Удаляет продукт по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        log.debug("Request to delete product with id {}", id);
        productFacade.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить все продукты", description = "Удаляет все продукты")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllProduct() {
        log.debug("Request to delete all products");
        productFacade.deleteAllProduct();
        return ResponseEntity.ok("All products deleted");
    }


}
