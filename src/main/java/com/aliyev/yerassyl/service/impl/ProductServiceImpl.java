package com.aliyev.yerassyl.service.impl;

import com.aliyev.yerassyl.mapper.ProductMapper;
import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;
import com.aliyev.yerassyl.repository.ProductRepository;
import com.aliyev.yerassyl.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class ProductServiceImpl implements ProductService {
    final ProductRepository productRepository;
    final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> allProducts = productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
        log.debug("Get all products");
        return allProducts;
    }

    @Override
    public Optional<ProductDTO> getProduct(Long id) {
        Optional<ProductDTO> product = productRepository.findById(id)
                .map(productMapper::toDTO);
        log.debug("Get product with Id: {}", id);
        return product;
    }

    @Transactional
    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        log.debug("createProduct from ProductService with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Transactional
    @Override
    public Optional<Product> updateProduct(Long id, ProductDTO dto) {
        Optional<Product> updated = productRepository.findById(id).map(existing -> {
            productMapper.updateEntityFromDTO(dto, existing);
            return productRepository.save(existing);
        });
        log.debug("Update product with ID: {}", id);
        return updated;
    }

    @Transactional
    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
        log.debug("Delete product with ID: {}", id);

    }

    @Override
    @Transactional
    public List<ProductDTO> createProducts(List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = productDTOs.stream()
                .map(dto -> {
                    Product entity = productMapper.toEntity(dto);
                    Product saved = productRepository.save(entity);
                    return productMapper.toDTO(saved);
                })
                .collect(Collectors.toList());
        log.debug("Created products");
        return createdProducts;
    }

    @Transactional
    @Override
    public void deleteAllProduct() {
        productRepository.deleteAll();
        log.debug("Delete all products");

    }
}
