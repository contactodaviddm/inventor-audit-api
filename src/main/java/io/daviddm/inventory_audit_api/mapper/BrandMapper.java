package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.BrandRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.BrandResponseDTO;
import io.daviddm.inventory_audit_api.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandRequestDTO dto);

    BrandResponseDTO toResponse(Brand brand);

    void updateEntityFromDto(BrandRequestDTO dto, @MappingTarget Brand brand);
}
