package com.aliyev.yerassyl.controllers;

import com.aliyev.yerassyl.facade.AuthFacade;
import com.aliyev.yerassyl.model.dto.LoginDTO;
import com.aliyev.yerassyl.model.dto.RegisterDTO;
import com.aliyev.yerassyl.model.dto.TokenDTO;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

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
