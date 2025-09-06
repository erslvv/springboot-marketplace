package com.aliyev.yerassyl.service;

import com.aliyev.yerassyl.model.dto.UserDTO;
import com.aliyev.yerassyl.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    User createUser(User user);

    Optional<UserDTO> updateUser(Long id, UserDTO dto);

    void deleteAllUsers();

    void deleteUserById(Long id);

    List<UserDTO> createUsers(List<UserDTO> userDTOs);
}