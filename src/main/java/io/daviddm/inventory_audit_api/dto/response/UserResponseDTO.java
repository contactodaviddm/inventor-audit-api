package io.daviddm.inventory_audit_api.dto.response;

public record UserResponseDTO(Long id, Long documentNumber, String name, String lastName, String phoneNumber,
                              String email, String password) {
}
