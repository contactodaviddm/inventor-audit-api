package io.daviddm.inventory_audit_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull(message = "El documento no puede ser nulo")
        Long documentNumber,
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
        String name,
        @NotBlank(message = "El apellido no puede estar vacío")
        @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
        String lastName,
        @NotBlank(message = "El teléfono no puede estar vacío")
        @Size(min = 7, max = 10, message = "El teléfono debe tener entre 7 dígitos (fijos) y 10 dígitos (móviles)")
        String phoneNumber,
        @NotBlank(message = "El correo no puede estar vacío")
        @Email(message = "Correo no válido")
        @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
        String email) {
}
