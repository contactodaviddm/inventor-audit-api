package io.daviddm.inventory_audit_api.dto.response;


import io.daviddm.inventory_audit_api.enums.ProductStatus;

import java.math.BigDecimal;

public record ProductResponseDTO(Long id, String code, String name, CategoryResponseDTO category,
                                 BrandResponseDTO brand, Integer stock, WarehouseResponseDTO warehouse, String shelf, BigDecimal price,
                                 ProductStatus status) {
}
