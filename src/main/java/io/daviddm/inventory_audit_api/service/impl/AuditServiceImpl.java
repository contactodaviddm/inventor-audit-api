package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.dto.response.CompactUserResponseDTO;
import io.daviddm.inventory_audit_api.enums.AuditOperation;
import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.AuditMapper;
import io.daviddm.inventory_audit_api.model.Audit;
import io.daviddm.inventory_audit_api.model.User;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
            System.out.println("Before: " + before);
            String dataBefore = before == null ? objectMapper.writeValueAsString("empty") : objectMapper.writeValueAsString(before);
            String dataAfter = after == null ? objectMapper.writeValueAsString("empty") : objectMapper.writeValueAsString(after);
            System.out.println("After: " + after);
            Audit audit = new Audit();
            audit.setOperation(operation);
            audit.setDataBefore(dataBefore);
            audit.setDataAfter(dataAfter);
            audit.setEntityName(entityName);
            audit.setUser(user);
            auditRepository.save(audit);
        } catch (JsonProcessingException e) {
            throw new BusinessRuleException("Error al serializar datos para auditoría ");
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

    private List<AuditResponseDTO> mapAuditListManuallyWithJson(List<Audit> dto) {
        return dto.stream().map(audit -> {
            try {
                JsonNode before = objectMapper.readTree(audit.getDataBefore());
                JsonNode after = objectMapper.readTree(audit.getDataAfter());
                CompactUserResponseDTO user = auditMapper.toResponse(audit).user();
                return new AuditResponseDTO(
                        audit.getId(),
                        audit.getEntityName(),
                        audit.getOperation(),
                        before,
                        after,
                        user,
                        audit.getDate()
                );
            } catch (JsonProcessingException e) {
                throw new BusinessRuleException("Error al leer JSON desde base de datos");
            }
        }).toList();
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        return mapAuditListManuallyWithJson(auditRepository.findAll());
    }

    @Override
    public AuditResponseDTO getAuditById(Long id) {
        Audit audit = auditRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la auditoría con ID: " + id));
        try {
            JsonNode before = objectMapper.readTree(audit.getDataBefore());
            JsonNode after = objectMapper.readTree(audit.getDataAfter());
            CompactUserResponseDTO user = auditMapper.toResponse(audit).user();
            return new AuditResponseDTO(
                    audit.getId(),
                    audit.getEntityName(),
                    audit.getOperation(),
                    before,
                    after,
                    user,
                    audit.getDate()
            );
        } catch (JsonProcessingException e) {
            throw new BusinessRuleException("Error al leer JSON desde base de datos");
        }
    }

    @Override
    public List<AuditResponseDTO> getAuditsByFilters(Specification<Audit> spec) {
        return mapAuditListManuallyWithJson(auditRepository.findAll(spec));
    }


}
