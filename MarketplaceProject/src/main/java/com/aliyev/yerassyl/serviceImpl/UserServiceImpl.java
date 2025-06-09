package com.aliyev.yerassyl.serviceImpl;

import com.aliyev.yerassyl.dto.UserDTO;
import com.aliyev.yerassyl.mapper.UserMapper;
import com.aliyev.yerassyl.model.User;
import com.aliyev.yerassyl.repository.UserRepository;
import com.aliyev.yerassyl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream() .
                map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserDTO);
    }

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    public Optional<UserDTO> updateUser (Long id, UserDTO dto){
        return userRepository.findById(id).map(existing -> {
            userMapper.updateEntityFromDTO(dto, existing);
            return userMapper.toUserDTO(userRepository.save(existing));
        });
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
