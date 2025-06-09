package com.aliyev.yerassyl.mapper;

import com.aliyev.yerassyl.model.Product;
import com.aliyev.yerassyl.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getProductId());
        product.setName(dto.getProductName());
        product.setDescription(dto.getProductDescription());
        product.setPrice(dto.getProductPrice());
        return product;
    }

    public void updateEntityFromDTO(ProductDTO dto, Product entity) {
        entity.setName(dto.getProductName());
        entity.setDescription(dto.getProductDescription());
        entity.setPrice(dto.getProductPrice());
    }
}