package com.aliyev.yerassyl.facade.impl;

import com.aliyev.yerassyl.facade.AuthFacade;
import com.aliyev.yerassyl.mapper.UserMapper;
import com.aliyev.yerassyl.model.dto.LoginDTO;
import com.aliyev.yerassyl.model.dto.RegisterDTO;
import com.aliyev.yerassyl.model.dto.TokenDTO;
import com.aliyev.yerassyl.model.entity.User;
import com.aliyev.yerassyl.repository.UserRepository;
import com.aliyev.yerassyl.security.JwtUtil;
import com.aliyev.yerassyl.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@Slf4j
@AllArgsConstructor

public class AuthFacadeImpl implements AuthFacade {
    final UserRepository repo;
    final UserMapper userMapper;
    final UserService userService;
    final AuthenticationManager authManager;
    final JwtUtil jwt;



    @Override
    @Transactional
    public TokenDTO login(LoginDTO dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        var user = repo.findByEmail(dto.email()).orElseThrow();
        return new TokenDTO(jwt.generateToken(user));
    }

    @Override
    public void register(RegisterDTO dto){
        if (repo.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email уже зарегистрирован");
        }
        User user = userMapper.toUser(dto);
        userService.createUser(user);
        log.debug("User is creating on Facade layer");
    }
}
