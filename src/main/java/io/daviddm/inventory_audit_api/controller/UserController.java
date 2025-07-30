package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.request.UserRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.UserResponseDTO;
import io.daviddm.inventory_audit_api.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable @NotNull(message = "El id a actualizar no puede estar vacío")
            @Positive(message = "El id a actualizar debe ser positivo") Long id, @Valid @RequestBody UserRequestDTO dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable @NotNull(message = "El id a buscar no puede estar vacío")
            @Positive(message = "El id a buscar debe ser positivo") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @NotNull(message = "El id a eliminar no puede estar vacío")
            @Positive(message = "El id a eliminar debe ser positivo") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
