package com.aliyev.yerassyl.facade;

import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface ProductFacade {
    ProductDTO createProduct(ProductDTO productDTO, BindingResult br);

    List<ProductDTO> getAllProducts();

    Optional<ProductDTO> getProduct(Long id);

    List<ProductDTO> createProducts(List<ProductDTO> productDTOs);

    Optional<Product> updateProduct(Long id, ProductDTO dto);

    void deleteProductById(Long id);

    void deleteAllProduct();
}
