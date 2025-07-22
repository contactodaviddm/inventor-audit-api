package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.AuditRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.mapper.AuditMapper;
import io.daviddm.inventory_audit_api.model.Audit;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    @Override
    public AuditResponseDTO createAudit(AuditRequestDTO dto) {
        Audit audit = auditMapper.toEntity(dto);
        return auditMapper.toResponse(auditRepository.save(audit));
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        return auditRepository.findAll().stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAllAuditByDate(LocalDateTime date) {
        return auditRepository.findByDate(date).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAllAuditByDateBetween(LocalDateTime start, LocalDateTime end) {
        return auditRepository.findByDateBetween(start, end).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntity(String name) {
        return auditRepository.findByEntityName(name).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntityAndDate(String name, LocalDateTime date) {
        return auditRepository.findByEntityNameAndDate(name, date).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntityAndDateBetween(String name, LocalDateTime start, LocalDateTime end) {
        return auditRepository.findByEntityNameAndDateBetween(name, start, end).stream().map(auditMapper::toResponse).toList();
    }
}
