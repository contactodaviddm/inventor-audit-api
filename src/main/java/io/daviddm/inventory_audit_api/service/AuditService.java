package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.model.Audit;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AuditService {
    void logInsert(String entityName, Object before, Object after);

    void logUpdate(String entityName, Object before, Object after);

    void logDelete(String entityName, Object before, Object after);

    List<AuditResponseDTO> getAllAudits();

    AuditResponseDTO getAuditById(Long id);

    List<AuditResponseDTO> getAuditsByFilters(Specification<Audit> spec);
}
