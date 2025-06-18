package com.aliyev.yerassyl.mapper;

import com.aliyev.yerassyl.model.dto.RegisterDTO;
import com.aliyev.yerassyl.model.dto.UserDTO;
import com.aliyev.yerassyl.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;

    }
    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;

    }

    public User toUser(RegisterDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        return user;

    }

    public void updateEntityFromDTO(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
    }
}
