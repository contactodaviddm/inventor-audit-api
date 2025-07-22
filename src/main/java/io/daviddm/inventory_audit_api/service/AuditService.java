package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.AuditRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditService {
    AuditResponseDTO createAudit(AuditRequestDTO dto);

    List<AuditResponseDTO> getAllAudits();

    List<AuditResponseDTO> getAllAuditByDate(LocalDateTime date);

    List<AuditResponseDTO> getAllAuditByDateBetween(LocalDateTime start, LocalDateTime end);

    List<AuditResponseDTO> getAuditsByEntity(String name);

    List<AuditResponseDTO> getAuditsByEntityAndDate(String name, LocalDateTime date);

    List<AuditResponseDTO> getAuditsByEntityAndDateBetween(String name, LocalDateTime start, LocalDateTime end);
}
