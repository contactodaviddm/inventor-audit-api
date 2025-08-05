package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.request.CategoryRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.CategoryResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.CategoryMapper;
import io.daviddm.inventory_audit_api.model.Category;
import io.daviddm.inventory_audit_api.repository.CategoryRepository;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        if (categoryRepository.existsByNameIgnoreCase(dto.name()))
            throw new BusinessRuleException("La categoria ingresada ya está registrada, intente con una distinta.");
        category = categoryRepository.save(category);
        auditService.logInsert("Category", null, category);
        return categoryMapper.toResponse(category);
    }

    @Transactional
    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO dto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría a actualizar: " + id));
        if (category.getName().equals(dto.name())) throw new BusinessRuleException("No hay cambios a actualizar");
        if (categoryRepository.existsByNameIgnoreCase(dto.name()) && !categoryRepository.existsByIdAndNameIgnoreCase(id, dto.name()))
            throw new BusinessRuleException("La categoria ingresada ya está asignada a otro registro, intente con una distinta.");
        Category categoryBefore = objectMapper.convertValue(category, Category.class);
        categoryMapper.updateEntityFromDto(dto, category);
        category = categoryRepository.save(category);
        auditService.logUpdate("Category", categoryBefore, category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría buscada: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria a eliminar: " + id));
        if (productRepository.existsByCategory_Id(id))
            throw new BusinessRuleException("No se puede eliminar la categoria, porque tiene productos asignados");
        categoryRepository.delete(category);
        auditService.logDelete("Category", category, null);
    }
}
