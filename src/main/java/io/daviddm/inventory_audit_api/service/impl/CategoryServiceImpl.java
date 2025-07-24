package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.CategoryRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.CategoryResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.CategoryMapper;
import io.daviddm.inventory_audit_api.model.Category;
import io.daviddm.inventory_audit_api.repository.CategoryRepository;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO updateCategory(CategoryRequestDTO dto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría a actualizar: " + id));
        categoryMapper.updateEntityFromDto(dto, category);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría buscada: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategory() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria a eliminar: " + id));
        if (productRepository.existsCategory_Id(id))
            throw new BusinessRuleException("No se puede eliminar la categoria, porque tiene productos asignados");
        categoryRepository.delete(category);
    }
}
