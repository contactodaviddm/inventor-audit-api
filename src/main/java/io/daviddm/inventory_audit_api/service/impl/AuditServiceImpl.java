package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.enums.AuditOperation;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.AuditMapper;
import io.daviddm.inventory_audit_api.model.Audit;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    private void logOperation(String entityName, AuditOperation operation, Object before, Object after, Long userId) {
        try {
            String dataBefore = objectMapper.writeValueAsString(before);
            String dataAfter = objectMapper.writeValueAsString(after);
            Audit audit = new Audit();
            audit.setOperation(operation);
            audit.setDataBefore(dataBefore);
            audit.setDataAfter(dataAfter);
            audit.setEntityName(entityName);
            audit.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("No se encuentra el usuario: " + userId)));
            auditRepository.save(audit);
        } catch (JsonProcessingException e) {
            throw new BusinessRuleException("Error al serializar datos para auditoría");
        }
    }

    @Override
    public void logInsert(String entityName, Object before, Object after, Long userId) {
        logOperation(entityName, AuditOperation.validateEnum("INSERT"), before, after, userId);
    }

    @Override
    public void logUpdate(String entityName, Object before, Object after, Long userId) {
        logOperation(entityName, AuditOperation.validateEnum("UPDATE"), before, after, userId);
    }

    @Override
    public void logDelete(String entityName, Object before, Object after, Long userId) {
        logOperation(entityName, AuditOperation.validateEnum("DELETE"), before, after, userId);
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

    @Override
    public List<AuditResponseDTO> getAuditsByOperation(String type) {
        AuditOperation movementType = AuditOperation.validateEnum(type);
        return auditRepository.findByOperation(movementType).stream().map(auditMapper::toResponse).toList();
    }
}
