package com.aliyev.yerassyl.controllers;


import com.aliyev.yerassyl.dto.ProductDTO;
import com.aliyev.yerassyl.model.Product;
import com.aliyev.yerassyl.repository.ProductRepository;
import com.aliyev.yerassyl.service.ProductService;
import com.aliyev.yerassyl.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@CrossOrigin(origins = "http://localhost:8081") //разрешаю принимать запросы от указанного адреса
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return productService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        try{
            return new ResponseEntity<>(productServiceImpl.createProduct(product), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<String> deleteAllProduct() {
        try{
            productRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }










}
