package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.request.WarehouseRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.WarehouseResponseDTO;
import io.daviddm.inventory_audit_api.service.WarehouseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
@Validated
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> createWarehouse(@Valid @RequestBody WarehouseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(warehouseService.createWarehouse(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> updateWarehouse(@Valid @RequestBody WarehouseRequestDTO dto,
                                                                @PathVariable @NotNull(message = "El id a actualizar no puede ser nulo")
                                                                @Positive(message = "El id a actualizar debe ser positivo") Long id) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> deleteWarehouse(@PathVariable @NotNull(message = "El id a eliminar no debe ser nulo")
                                                                @Positive(message = "El id a elimnar debe ser positivo") Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponseDTO>> getAllWarehouse() {
        return ResponseEntity.ok(warehouseService.getAllWarehouse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponseDTO> getWarehouseById(@PathVariable @NotNull(message = "El id a buscar no debe ser nulo")
                                                                 @Positive(message = "El id a buscar debe ser positivo") Long id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }
}
