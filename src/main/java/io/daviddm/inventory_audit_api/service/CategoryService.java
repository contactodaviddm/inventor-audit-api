package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.CategoryRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);

    CategoryResponseDTO updateCategory(CategoryRequestDTO dto, Long id);

    CategoryResponseDTO getCategoryById(Long id);

    List<CategoryResponseDTO> getAllCategory();

    void deleteCategory(Long id);
}
