package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.WarehouseRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.WarehouseResponseDTO;

import java.util.List;

public interface WarehouseService {
    WarehouseResponseDTO createWarehouse(WarehouseRequestDTO dto);

    WarehouseResponseDTO updateWarehouse(WarehouseRequestDTO dto, Long id);

    WarehouseResponseDTO getWarehouseById(Long id);

    List<WarehouseResponseDTO> getAllWarehouse();

    void deleteWarehouse(Long id);
}
