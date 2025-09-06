package com.aliyev.yerassyl.service;

import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProduct(Long id);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Long id, ProductDTO productDTO);
    void deleteAllProduct();
    List<ProductDTO> createProducts(List<ProductDTO> productDTOs);
    void deleteProductById(Long id);
}
