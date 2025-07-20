package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.WarehouseRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.WarehouseResponseDTO;
import io.daviddm.inventory_audit_api.model.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseRequestDTO dto);
    WarehouseResponseDTO toResponse(Warehouse warehouse);
}
