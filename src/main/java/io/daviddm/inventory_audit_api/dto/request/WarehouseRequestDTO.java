package io.daviddm.inventory_audit_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WarehouseRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 45, message = "El nombre no puede superar los 45 caracteres")
        String name,
        @NotBlank(message = "La dirección no puede estar vacía")
        @Size(max = 150, message = "La dirección no puede superar los 150 caracteres")
        String address
) {
}
