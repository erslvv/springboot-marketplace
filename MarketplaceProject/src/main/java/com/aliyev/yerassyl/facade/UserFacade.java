package com.aliyev.yerassyl.facade;

import com.aliyev.yerassyl.model.dto.UserDTO;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UserFacade {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    UserDTO createUser(UserDTO userDTO, BindingResult br);

    List<UserDTO> createUsers(List<UserDTO> userDTOs);

    Optional<UserDTO> updateUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

    void deleteUsers();


}
