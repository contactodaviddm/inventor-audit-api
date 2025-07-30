package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.enums.AuditOperation;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.AuditMapper;
import io.daviddm.inventory_audit_api.model.Audit;
import io.daviddm.inventory_audit_api.model.User;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    private void logOperation(String entityName, AuditOperation operation, Object before, Object after) {
        //Temporary user
        User user = userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("No hay usuario ligado al correo"));
        try {
            String dataBefore = objectMapper.writeValueAsString(before);
            String dataAfter = objectMapper.writeValueAsString(after);
            Audit audit = new Audit();
            audit.setOperation(operation);
            audit.setDataBefore(dataBefore);
            audit.setDataAfter(dataAfter);
            audit.setEntityName(entityName);
            audit.setUser(user);
            auditRepository.save(audit);
        } catch (JsonProcessingException e) {
            throw new BusinessRuleException("Error al serializar datos para auditoría");
        }
    }

    @Override
    public void logInsert(String entityName, Object before, Object after) {
        logOperation(entityName, AuditOperation.validateEnum("INSERT"), before, after);
    }

    @Override
    public void logUpdate(String entityName, Object before, Object after) {
        logOperation(entityName, AuditOperation.validateEnum("UPDATE"), before, after);
    }

    @Override
    public void logDelete(String entityName, Object before, Object after) {
        logOperation(entityName, AuditOperation.validateEnum("DELETE"), before, after);
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        return auditRepository.findAll().stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAllAuditByDate(LocalDate date) {
        return auditRepository.findByDate(date).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAllAuditByDateBetween(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) throw new BusinessRuleException("La fecha inicial no puede superar a la final");
        return auditRepository.findByDateBetween(start, end).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntity(String name) {
        return auditRepository.findByEntityName(name).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntityAndDate(String name, LocalDate date) {
        return auditRepository.findByEntityNameAndDate(name, date).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByEntityAndDateBetween(String name, LocalDate start, LocalDate end) {
        if (start.isAfter(end)) throw new BusinessRuleException("La fecha inicial no puede superar a la final");
        return auditRepository.findByEntityNameAndDateBetween(name, start, end).stream().map(auditMapper::toResponse).toList();
    }

    @Override
    public List<AuditResponseDTO> getAuditsByOperation(String type) {
        AuditOperation movementType = AuditOperation.validateEnum(type);
        return auditRepository.findByOperation(movementType).stream().map(auditMapper::toResponse).toList();
    }
}
