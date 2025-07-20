package io.daviddm.inventory_audit_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "El código no puede estar vacío")
        @Size(min = 3, max = 45, message = "El código de producto debe estar entre 3 y 45 caracteres")
        String code,
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 45, message = "El nombre del producto debe estar entre 2 y 45 caracteres")
        String name,
        @NotNull(message = "La categoría no puede ser nula")
        @Positive(message = "La categoria debe tener un ID positivo")
        Long categoryId,
        @NotNull(message = "La marca no puede ser nula")
        @Positive(message = "La marca debe tener un ID positivo")
        Long brandId,
        @NotNull(message = "El stock no puede ser nulo")
        @Positive(message = "El stock debe tener valores positivos")
        Integer stock,
        @NotNull(message = "La bodega no puede ser nula")
        @Positive(message = "La bodega debe tener un ID positivo")
        Long warehouseId,
        @NotBlank(message = "El estante no puede estar vacio")
        String shelf,
        @NotNull(message = "El precio no puede estar vacío")
        BigDecimal price,
        @NotBlank(message = "El estado no puede estar vacío")
        String status) {
}
