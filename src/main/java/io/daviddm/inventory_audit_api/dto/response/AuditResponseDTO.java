package io.daviddm.inventory_audit_api.dto.response;

import io.daviddm.inventory_audit_api.enums.AuditOperation;

import java.time.LocalDateTime;

public record AuditResponseDTO(String entityName, AuditOperation operation, String dataBefore, String dataAfter,
                               CompactUserResponseDTO user,
                               LocalDateTime date
) {
}