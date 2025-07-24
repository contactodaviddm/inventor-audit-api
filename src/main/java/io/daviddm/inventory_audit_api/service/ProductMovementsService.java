package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.enums.MovementType;

import java.util.List;

public interface ProductMovementsService {
    ProductMovementsResponseDTO createProductMovements(ProductMovementsRequestDTO dto);

    ProductMovementsResponseDTO updateProductMovementsResponse(ProductMovementsRequestDTO dto, Long id);

    ProductMovementsResponseDTO getProductMovementById(Long id);

    List<ProductMovementsResponseDTO> getAllProductMovements();

    void deleteProductMovements(Long id);

    List<ProductMovementsResponseDTO> getProductMovementsByProductId(Long id);

    List<ProductMovementsResponseDTO> getProductMovementsByUserId(Long id);

    List<ProductMovementsResponseDTO> getProductMovementsByProductName(String name);

    List<ProductMovementsResponseDTO> getProductMovementsByType(String type);
}
