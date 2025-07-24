package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.WarehouseRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.WarehouseResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.WarehouseMapper;
import io.daviddm.inventory_audit_api.model.Warehouse;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.repository.WarehouseRepository;
import io.daviddm.inventory_audit_api.service.WarehouseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Override
    public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO dto) {
        Warehouse warehouse = warehouseMapper.toEntity(dto);
        return warehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseResponseDTO updateWarehouse(WarehouseRequestDTO dto, Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega a actualizar: " + id));
        warehouseMapper.updateEntityFromDto(dto, warehouseMapper);
        return warehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    public WarehouseResponseDTO getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega buscada: " + id));
        return warehouseMapper.toResponse(warehouse);
    }

    @Override
    public List<WarehouseResponseDTO> getAllWarehouse() {
        return warehouseRepository.findAll().stream().map(warehouseMapper::toResponse).toList();
    }

    @Override
    public void deleteWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new BusinessRuleException("No se encontró la bodega a eliminar"));
        if (productRepository.existsWarehouse_Id(id))
            throw new BusinessRuleException("No se puede eliminar la bodega porque tiene productos asignados");
        warehouseRepository.delete(warehouse);
    }
}
