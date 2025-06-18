package com.aliyev.yerassyl.controllers;

import com.aliyev.yerassyl.facade.AuthFacade;
import com.aliyev.yerassyl.security.JwtUtil;
import com.aliyev.yerassyl.model.dto.*;
import com.aliyev.yerassyl.model.entity.User;
import com.aliyev.yerassyl.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwt;
    private final AuthFacade facade;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PermitAll
    public void register(@Valid @RequestBody RegisterDTO dto) {
       facade.register(dto);
    }

    @PostMapping("/login")
    @PermitAll
    public TokenDTO login(@RequestBody LoginDTO dto) {
        return facade.login(dto);
    }
}
