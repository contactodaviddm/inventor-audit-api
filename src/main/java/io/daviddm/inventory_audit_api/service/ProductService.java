package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.ProductRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductResponseDTO;
import io.daviddm.inventory_audit_api.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    List<ProductResponseDTO> findAllByFilters(Specification<Product> spec);
}
