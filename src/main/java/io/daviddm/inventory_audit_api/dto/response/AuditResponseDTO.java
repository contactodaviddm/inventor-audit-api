package io.daviddm.inventory_audit_api.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import io.daviddm.inventory_audit_api.enums.AuditOperation;

import java.time.LocalDateTime;

public record AuditResponseDTO (Long id, String entityName, AuditOperation operation,
                                JsonNode dataBefore, JsonNode dataAfter, CompactUserResponseDTO user, LocalDateTime date){
}
