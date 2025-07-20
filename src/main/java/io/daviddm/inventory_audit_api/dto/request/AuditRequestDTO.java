package io.daviddm.inventory_audit_api.dto.request;

import java.time.LocalDateTime;

public record AuditRequestDTO(
        String entityName,
        String operation,
        String dataBefore,
        String dataAfter,
        Long userId,
        LocalDateTime date
) {
}
