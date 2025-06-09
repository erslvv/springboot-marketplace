package com.aliyev.yerassyl.repository;

import com.aliyev.yerassyl.model.Product;
import com.aliyev.yerassyl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
}
