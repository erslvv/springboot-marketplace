package com.aliyev.yerassyl.serviceImpl;

import com.aliyev.yerassyl.dto.ProductDTO;
import com.aliyev.yerassyl.mapper.ProductMapper;
import com.aliyev.yerassyl.model.Product;
import com.aliyev.yerassyl.model.User;
import com.aliyev.yerassyl.repository.ProductRepository;
import com.aliyev.yerassyl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProduct(Long id){
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Long id, ProductDTO dto) {
        return productRepository.findById(id).map(existing -> {
            productMapper.updateEntityFromDTO(dto, existing);
            return productRepository.save(existing);
        });
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllProduct(){
        productRepository.deleteAll();
    }
}
