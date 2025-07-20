package io.daviddm.inventory_audit_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
        String name) {
}
