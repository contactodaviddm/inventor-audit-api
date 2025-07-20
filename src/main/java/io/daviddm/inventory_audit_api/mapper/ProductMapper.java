package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.ProductRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductResponseDTO;
import io.daviddm.inventory_audit_api.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    void updateEntityFromDto(ProductRequestDTO dto, @MappingTarget Product product);
}
