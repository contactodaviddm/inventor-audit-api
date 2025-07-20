package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMovementsMapper {
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    ProductMovements toEntity(ProductMovementsRequestDTO dto);

    ProductMovementsResponseDTO toResponse(ProductMovements productMovements);
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromDto(ProductMovementsRequestDTO dto, @MappingTarget ProductMovements productMovements);
}
