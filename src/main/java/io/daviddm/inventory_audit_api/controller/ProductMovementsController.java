package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import io.daviddm.inventory_audit_api.service.ProductMovementsService;

import io.daviddm.inventory_audit_api.specification.ProductMovementsSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/product-movements")
@RequiredArgsConstructor
public class ProductMovementsController {
    private final ProductMovementsService productMovementsService;

    @PostMapping
    public ResponseEntity<ProductMovementsResponseDTO> createProductMovements(@Valid @RequestBody ProductMovementsRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productMovementsService.createProductMovements(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductMovementsResponseDTO> updateProductMovements(@Valid @RequestBody ProductMovementsRequestDTO dto,
                                                                              @PathVariable Long id) {
        return ResponseEntity.ok(productMovementsService.updateProductMovementsResponse(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductMovementsResponseDTO> deleteProductMovements(@PathVariable Long id) {
        productMovementsService.deleteProductMovements(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductMovementsResponseDTO>> getAllProductMovements() {
        return ResponseEntity.ok(productMovementsService.getAllProductMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductMovementsResponseDTO> getProductMovementsById(@PathVariable Long id) {
        return ResponseEntity.ok(productMovementsService.getProductMovementById(id));
    }

    @GetMapping("/filters")
    public ResponseEntity<List<ProductMovementsResponseDTO>> getProductsMovementsFilters(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Integer quantityMax,
            @RequestParam(required = false) Integer quantityMin,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) LocalDate dateMax,
            @RequestParam(required = false) LocalDate dateMin,
            @RequestParam(required = false) String type) {
        Specification<ProductMovements> spec = ProductMovementsSpecification.findByFilters(
                productId, productCode, productName, userId, quantity, quantityMax, quantityMin, date, dateMax, dateMin, type
        );
        return ResponseEntity.ok(productMovementsService.findProductMovementsFilters(spec));
    }
}
