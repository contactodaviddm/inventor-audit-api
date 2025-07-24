package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.ProductRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    List<ProductResponseDTO> getProductsByCategoryName(String name);

    List<ProductResponseDTO> getProductsByCategoryId(Long id);

    List<ProductResponseDTO> getProductsByBrandName(String name);

    List<ProductResponseDTO> getProductsByBrandId(Long id);

    List<ProductResponseDTO> getProductsByWarehouseName(String name);

    List<ProductResponseDTO> getProductsByWarehouseId(Long id);

    List<ProductResponseDTO> getProductsByStatus(String status);
}
