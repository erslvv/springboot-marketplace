package com.aliyev.yerassyl.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long id;
    String name;
    @Email(message = "Некорректный email")
    @NotBlank(message = "Email обязателен")
    String email;
    @Size(min = 8)
    String password;


    public @Size(min = 8) String getPassword() {
        return password;
    }
}