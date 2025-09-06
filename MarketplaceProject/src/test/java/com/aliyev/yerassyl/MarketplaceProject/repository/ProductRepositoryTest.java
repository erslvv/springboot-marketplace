package com.aliyev.yerassyl.MarketplaceProject.repository;

import com.aliyev.yerassyl.model.entity.Product;
import com.aliyev.yerassyl.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void saveAndFindProduct() {
        Product product = new Product();
        product.setName("Book");
        product.setDescription("Interesting book");
        product.setPrice(20);
        Product saved = productRepository.save(product);

        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isPresent();
    }
}
