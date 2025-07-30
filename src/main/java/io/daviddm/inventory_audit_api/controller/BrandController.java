package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.request.BrandRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.BrandResponseDTO;
import io.daviddm.inventory_audit_api.service.BrandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@Validated
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponseDTO> createBrand(@Valid @RequestBody BrandRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.createBrand(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> updateBrand(@Valid @RequestBody BrandRequestDTO dto,
                                                        @PathVariable @NotNull(message = "El id a actualizar no puede ser nulo")
                                                        @Positive(message = "El id a actualizar debe ser positivo") Long id) {
        return ResponseEntity.ok(brandService.updateBrand(dto, id));
    }

    @GetMapping
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandByID(@PathVariable @NotNull(message = "El id a buscar no puede ser nulo")
                                                         @Positive(message = "El id a buscar debe ser positivo") Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BrandResponseDTO> deleteBrand(@PathVariable @NotNull(message = "El id a eliminar no puede ser nulo")
                                                        @Positive(message = "El id a eliminar debe ser positivo") Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
