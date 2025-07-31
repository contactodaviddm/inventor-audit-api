package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.request.UserRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.UserResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.UserMapper;
import io.daviddm.inventory_audit_api.model.User;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.repository.ProductMovementsRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProductMovementsRepository productMovementsRepository;
    private final AuditRepository auditRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        boolean emailUnique = userRepository.existsByEmailIgnoreCase(dto.email());
        boolean documentUnique = userRepository.existsByDocumentNumber(dto.documentNumber());
        boolean phoneUnique = userRepository.existsByPhoneNumber(dto.phoneNumber());
        if (emailUnique || documentUnique || phoneUnique) {
            String errorMessages = emailUnique ? "Correo, " : "";
            errorMessages += documentUnique ? "Documento, " : "";
            errorMessages += phoneUnique ? "Teléfono, " : "";
            errorMessages = errorMessages.substring(0, errorMessages.length() - 2);
            throw new BusinessRuleException("El " + errorMessages + " ingresado/s ya está/están asignado a otro usuario, intente con información distinta");
        }
        User user = userMapper.toEntity(dto);
        User userSave = userRepository.save(user);
        auditService.logInsert("User", dto, userSave);
        return userMapper.toResponse(userSave);
    }

    @Transactional
    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario a actualizar: " + id));
        User userBefore = objectMapper.convertValue(user, User.class);
        boolean emailUnique = !userRepository.existsByIdAndEmailIgnoreCase(id, dto.email()) && userRepository.existsByEmailIgnoreCase(dto.email());
        boolean documentUnique = !userRepository.existsByIdAndDocumentNumber(id, dto.documentNumber()) && userRepository.existsByDocumentNumber(dto.documentNumber());
        boolean phoneUnique = !userRepository.existsByIdAndPhoneNumber(id, dto.phoneNumber()) && userRepository.existsByPhoneNumber(dto.phoneNumber());
        if (emailUnique || documentUnique || phoneUnique) {
            String errorMessages = emailUnique ? "Correo, " : "";
            errorMessages += documentUnique ? "Documento, " : "";
            errorMessages += phoneUnique ? "Teléfono, " : "";
            errorMessages = errorMessages.substring(0, errorMessages.length() - 2);
            throw new BusinessRuleException("El " + errorMessages + " ingresado/s ya está/están asignado a otro usuario, intente con información distinta");
        }
        userMapper.updateEntityFromDto(dto, user);
        user = userRepository.save(user);
        auditService.logUpdate("User", userBefore, user);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario buscado: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El usuario a eliminar no existe: " + id));
        if (productMovementsRepository.existsByUser_Id(user.getId()) || auditRepository.existsByUser_Id(user.getId()))
            throw new BusinessRuleException("El usuario no puede borrarse porque ya ha tenido actividad en el sistema.");
        userRepository.delete(user);
        auditService.logDelete("User", user, null);
    }
}
