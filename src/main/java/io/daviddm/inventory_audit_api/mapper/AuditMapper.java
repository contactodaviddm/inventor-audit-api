package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    @Mapping(target = "user", ignore = true)
    AuditResponseDTO toResponse(Audit audit);
}
