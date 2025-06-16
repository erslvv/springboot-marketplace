package com.aliyev.yerassyl.controllers;


import com.aliyev.yerassyl.facade.UserFacade;
import com.aliyev.yerassyl.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserFacade userFacade;

    @GetMapping
    @Operation(summary = "Получить всех пользователей", description = "Возвращает всех пользователей")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        log.debug("Request to get all users");
        return ResponseEntity.ok(userFacade.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает пользователя с указанным ID")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.debug("Request to get user by ID: {}", id);
        return userFacade.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @Operation(summary = "Создать пользователя", description = "Создает пользователя")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult br) {
        log.debug("Request to create user");
        return new ResponseEntity<>(userFacade.createUser(userDTO, br), HttpStatus.CREATED);
    }

    @PostMapping("/batch")
    @Operation(summary = "Создать несколько пользователей", description = "Создает несколько пользователей по списку")
    public ResponseEntity<List<UserDTO>> createUsers(@RequestBody List<UserDTO> userDTOs) {
        log.debug("Request to create users");
        List<UserDTO> created = userFacade.createUsers(userDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по ID", description = "Обновляет пользователя с указанным ID")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        log.debug("Request to update user by ID: {}", id);
        return userFacade.updateUser(id, dto).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по ID", description = "Удаляет пользователя с указанным ID")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        log.debug("Request to delete user by ID: {}", id);
        userFacade.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    @Operation(summary = "Удалить всех пользователей", description = "Удаляет всех пользователей")
    public ResponseEntity<String> deleteUsers() {
        log.debug("Request to delete all users");
        userFacade.deleteUsers();
        return ResponseEntity.ok("All users deleted");
    }
}
