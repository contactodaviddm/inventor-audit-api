package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.CategoryRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.CategoryResponseDTO;
import io.daviddm.inventory_audit_api.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponse(Category category);

    void updateEntityFromDto(CategoryRequestDTO dto, @MappingTarget Category category);
}
