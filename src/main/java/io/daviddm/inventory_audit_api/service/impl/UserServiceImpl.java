package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.UserRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.UserResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.UserMapper;
import io.daviddm.inventory_audit_api.model.User;
import io.daviddm.inventory_audit_api.repository.AuditRepository;
import io.daviddm.inventory_audit_api.repository.ProductMovementsRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProductMovementsRepository productMovementsRepository;
    private final AuditRepository auditRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario a actualizar: " + id));
        userMapper.updateEntityFromDto(dto, user);
        return userMapper.toResponse(userRepository.save(user));
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

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("El usuario a eliminar no existe: " + id));
        if (productMovementsRepository.existsByUser_Id(user.getId()) || auditRepository.existsByUser_Id(user.getId()))
            throw new BusinessRuleException("El usuario no puede borrarse porque ya ha tenido actividad en el sistema.");
        userRepository.delete(user);
    }
}
