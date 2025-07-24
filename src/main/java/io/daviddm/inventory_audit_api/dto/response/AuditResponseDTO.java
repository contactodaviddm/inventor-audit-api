package io.daviddm.inventory_audit_api.dto.response;

import io.daviddm.inventory_audit_api.enums.AuditOperation;

import java.time.LocalDateTime;

public record AuditResponseDTO (Long id, String entityName, AuditOperation operation,
                                String data_before, String data_after, Long user, LocalDateTime date){
}
