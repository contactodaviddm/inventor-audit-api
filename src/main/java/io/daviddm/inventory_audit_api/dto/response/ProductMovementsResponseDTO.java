package io.daviddm.inventory_audit_api.dto.response;

import io.daviddm.inventory_audit_api.enums.MovementType;

import java.time.LocalDateTime;

public record ProductMovementsResponseDTO(Long id, ProductResponseDTO product,
                                          CompactUserResponseDTO user,
                                          Integer quantity, LocalDateTime date, MovementType type) {
}
