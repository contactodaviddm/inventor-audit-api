package io.daviddm.inventory_audit_api.mapper;

import io.daviddm.inventory_audit_api.dto.request.UserRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.UserResponseDTO;
import io.daviddm.inventory_audit_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);
}
