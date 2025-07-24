package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.enums.AuditOperation;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditService {
    void logInsert(String entityName, Object before, Object after, Long userId);

    void logUpdate(String entityName, Object before, Object after, Long userId);

    void logDelete(String entityName, Object before, Object after, Long userId);

    List<AuditResponseDTO> getAllAudits();

    List<AuditResponseDTO> getAllAuditByDate(LocalDateTime date);

    List<AuditResponseDTO> getAllAuditByDateBetween(LocalDateTime start, LocalDateTime end);

    List<AuditResponseDTO> getAuditsByEntity(String name);

    List<AuditResponseDTO> getAuditsByEntityAndDate(String name, LocalDateTime date);

    List<AuditResponseDTO> getAuditsByEntityAndDateBetween(String name, LocalDateTime start, LocalDateTime end);

    List<AuditResponseDTO> getAuditsByOperation(String operation);
}
