package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductMovementsService {
    ProductMovementsResponseDTO createProductMovements(ProductMovementsRequestDTO dto);

    ProductMovementsResponseDTO getProductMovementById(Long id);

    List<ProductMovementsResponseDTO> getAllProductMovements();

    void deleteProductMovements(Long id);

    List<ProductMovementsResponseDTO> findProductMovementsFilters(Specification<ProductMovements> spec);
}
