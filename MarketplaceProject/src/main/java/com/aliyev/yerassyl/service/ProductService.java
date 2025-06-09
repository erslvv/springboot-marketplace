package com.aliyev.yerassyl.service;

import com.aliyev.yerassyl.dto.ProductDTO;
import com.aliyev.yerassyl.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProduct(Long id);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Long id, ProductDTO productDTO);
    void deleteAllProduct();
}
