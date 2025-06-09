package com.aliyev.yerassyl.repository;

import com.aliyev.yerassyl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameContaining(String name);
}
