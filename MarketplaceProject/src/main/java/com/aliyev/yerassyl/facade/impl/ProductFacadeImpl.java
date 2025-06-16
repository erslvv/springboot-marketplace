package com.aliyev.yerassyl.facade.impl;

import com.aliyev.yerassyl.facade.ProductFacade;
import com.aliyev.yerassyl.mapper.ProductMapper;
import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;
import com.aliyev.yerassyl.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class ProductFacadeImpl implements ProductFacade {
    final ProductService productService;
    final ProductMapper productMapper;

    public ProductFacadeImpl(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, BindingResult br) {
        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }
        Product product = productMapper.toEntity(productDTO);
        productService.createProduct(product);
        ProductDTO createProductt = productMapper.toDTO(product);
        log.debug("Create product on Facade Layer");
        return createProductt;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> getAllProductss = productService.getAllProducts();
        log.debug("Get all products on Facade Layer");
        return getAllProductss;
    }

    @Override
    public Optional<ProductDTO> getProduct(Long id) {
        Optional<ProductDTO> productOpt = productService.getProduct(id);
        log.debug("Get product on Facade Layer with ID: {}", id);
        return productOpt;
    }

    @Override
    @Transactional
    public List<ProductDTO> createProducts(List<ProductDTO> productDTOs) {
        List<ProductDTO> createdList = productService.createProducts(productDTOs);
        log.debug("Created {} products on Facade Layer", createdList.size());
        return createdList;
    }

    @Override
    @Transactional
    public Optional<Product> updateProduct(Long id, ProductDTO dto) {
        Optional<Product> updated = productService.updateProduct(id, dto);
        log.debug("Update product on Facade Layer with ID: {}", id);
        return updated;
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        try {
            productService.deleteProductById(id);
            log.debug("Deleted product on Facade Layer with ID: {} — no errors", id);
        } catch (Exception e) {
            log.error("Error deleting product with ID: {}", id, e);
        }
    }

    @Override
    @Transactional
    public void deleteAllProduct() {
        try {
            productService.deleteAllProduct();
            log.debug("Deleted all products on Facade Layer — no errors");
        } catch (Exception e) {
            log.error("Error deleting all products", e);
        }
    }
}
