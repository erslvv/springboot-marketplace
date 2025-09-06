package com.aliyev.yerassyl.MarketplaceProject.service;

import com.aliyev.yerassyl.mapper.ProductMapper;
import com.aliyev.yerassyl.model.dto.ProductDTO;
import com.aliyev.yerassyl.model.entity.Product;
import com.aliyev.yerassyl.repository.ProductRepository;
import com.aliyev.yerassyl.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void getProductReturnsDto() {
        Product product = new Product();
        product.setId(1L);
        ProductDTO dto = new ProductDTO();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(dto);

        Optional<ProductDTO> result = productService.getProduct(1L);

        assertThat(result).isPresent();
    }
}
