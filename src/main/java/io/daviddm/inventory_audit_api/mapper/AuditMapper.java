package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.AuditRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.model.Audit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuditMapper {
    @Mapping(target = "user", ignore = true)
    Audit toEntity(AuditRequestDTO dto);

    AuditResponseDTO toResponse(Audit audit);

    @Mapping(target = "user", ignore = true)
    void updateEntityFromDto(AuditRequestDTO dto, @MappingTarget Audit audit);
}
