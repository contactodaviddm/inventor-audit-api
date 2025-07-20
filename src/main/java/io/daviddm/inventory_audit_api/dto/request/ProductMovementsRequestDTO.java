package io.daviddm.inventory_audit_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductMovementsRequestDTO(
        @NotNull(message = "El producto no puede estar nulo")
        @Positive(message = "El producto debe tener un codigo positivo")
        Long productId,
        @NotNull(message = "El usuario no puede estar nulo")
        @Positive(message = "El usuario debe tener un codigo positivo")
        Long userId,
        @NotNull(message = "La cantidad no puede estar nulo")
        @Positive(message = "La cantidad debe tener un codigo positivo")
        Integer quantity,
        @NotBlank(message = "El tipo de movimiento no puede estar vacío")
        String type) {
}
