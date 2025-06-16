package com.aliyev.yerassyl.service.impl;

import com.aliyev.yerassyl.mapper.UserMapper;
import com.aliyev.yerassyl.model.dto.UserDTO;
import com.aliyev.yerassyl.model.entity.User;
import com.aliyev.yerassyl.repository.UserRepository;
import com.aliyev.yerassyl.service.UserService;
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
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream().
                map(userMapper::toUserDTO)
                .collect(Collectors.toList());
        log.debug("Get all users");
        return users;
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<UserDTO> user = userRepository.findById(id).map(userMapper::toUserDTO);
        log.debug("Get user by id: {}", id);
        return user;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User newUser = userRepository.save(user);
        log.debug("Created a new user email: {}", user.getEmail());
        return newUser;
    }

    @Override
    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO dto) {
        Optional<UserDTO> updated = userRepository.findById(id).map(existing -> {
            userMapper.updateEntityFromDTO(dto, existing);
            return userMapper.toUserDTO(userRepository.save(existing));
        });
        log.debug("User is updated with id: {}", id);
        return updated;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        log.debug("Delete user with id: {}", id);

    }

    @Override
    @Transactional
    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        List<UserDTO> createdUsers = userDTOs.stream()
                .map(dto -> {
                    User entity = userMapper.toUser(dto);
                    User saved = userRepository.save(entity);
                    return userMapper.toUserDTO(saved);
                })
                .collect(Collectors.toList());

        log.debug("Created users with gmails:");
        for (UserDTO userDTO : userDTOs) {
            log.debug("{} ,", userDTO.getEmail());
        }

        return createdUsers;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
        log.debug("Delete all users");
    }

}
