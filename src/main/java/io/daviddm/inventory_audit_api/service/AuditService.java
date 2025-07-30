package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AuditService {
    void logInsert(String entityName, Object before, Object after);

    void logUpdate(String entityName, Object before, Object after);

    void logDelete(String entityName, Object before, Object after);

    List<AuditResponseDTO> getAllAudits();

    List<AuditResponseDTO> getAllAuditByDate(LocalDate date);

    List<AuditResponseDTO> getAllAuditByDateBetween(LocalDate start, LocalDate end);

    List<AuditResponseDTO> getAuditsByEntity(String name);

    List<AuditResponseDTO> getAuditsByEntityAndDate(String name, LocalDate date);

    List<AuditResponseDTO> getAuditsByEntityAndDateBetween(String name, LocalDate start, LocalDate end);

    List<AuditResponseDTO> getAuditsByOperation(String operation);
}
