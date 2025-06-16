package com.aliyev.yerassyl.facade.impl;

import com.aliyev.yerassyl.facade.UserFacade;
import com.aliyev.yerassyl.mapper.UserMapper;
import com.aliyev.yerassyl.model.dto.UserDTO;
import com.aliyev.yerassyl.model.entity.User;
import com.aliyev.yerassyl.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class UserFacadeImpl implements UserFacade {
    final UserMapper userMapper;
    final UserService userService;

    public UserFacadeImpl(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO, BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }
        User user = userMapper.toUser(userDTO);
        userService.createUser(user);
        UserDTO createdUser = userMapper.toUserDTO(user);
        log.debug("User is creating on Facade layer");
        return createdUser;

    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.debug("Get all users on Facade layer");
        return userService.getAllUsers();
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<UserDTO> getUserByIdd = userService.getUserById(id);
        return getUserByIdd;
    }

    @Override
    @Transactional
    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        List<UserDTO> createdList = userService.createUsers(userDTOs);
        log.debug("Create users on Facade layer");

        return createdList;
    }

    @Override
    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO dto) {
        Optional<UserDTO> updateUserr = userService.updateUser(id, dto);

        log.debug("Update user by id on Facade layer with ID:  {}", id);
        return updateUserr;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        try {
            userService.deleteUserById(id);
            log.debug("Deleted user on Facade layer with ID: {} — no errors", id);
        } catch (Exception e) {
            log.error("Error deleting user with ID: {}", id, e);
        }
    }

    @Override
    @Transactional
    public void deleteUsers() {
        try {
            userService.deleteAllUsers();
            log.debug("Deleted all users on Facade layer — no errors");
        } catch (Exception e) {
            log.error("Error deleting all users", e);
        }
    }
}
