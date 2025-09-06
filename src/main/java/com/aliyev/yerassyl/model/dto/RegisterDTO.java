package com.aliyev.yerassyl.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank String name,
        @Email(message = "Неверный e-mail") String email,
        @NotBlank(message = "Пароль обязателен") String password) {
}




