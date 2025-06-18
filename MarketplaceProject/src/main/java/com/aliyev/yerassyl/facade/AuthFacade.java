package com.aliyev.yerassyl.facade;

import com.aliyev.yerassyl.model.dto.LoginDTO;
import com.aliyev.yerassyl.model.dto.RegisterDTO;
import com.aliyev.yerassyl.model.dto.TokenDTO;

import java.util.Optional;
public interface AuthFacade {
    TokenDTO login( LoginDTO dto);
    void register(RegisterDTO dto);
}
