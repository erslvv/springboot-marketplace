package com.aliyev.yerassyl.service;

import com.aliyev.yerassyl.dto.UserDTO;
import com.aliyev.yerassyl.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    User createUser(User user);
    Optional<UserDTO> updateUser(Long id, UserDTO dto);
    void deleteAllUsers();

}