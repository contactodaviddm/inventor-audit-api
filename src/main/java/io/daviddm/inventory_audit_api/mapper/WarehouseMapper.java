package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.WarehouseRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.WarehouseResponseDTO;
import io.daviddm.inventory_audit_api.model.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseRequestDTO dto);

    WarehouseResponseDTO toResponse(Warehouse warehouse);

    void updateEntityFromDto(WarehouseRequestDTO dto, @MappingTarget Warehouse warehouseMapper);
}
