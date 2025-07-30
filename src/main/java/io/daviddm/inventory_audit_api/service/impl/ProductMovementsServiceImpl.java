package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.enums.MovementType;
import io.daviddm.inventory_audit_api.mapper.ProductMovementsMapper;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import io.daviddm.inventory_audit_api.repository.ProductMovementsRepository;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.repository.UserRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.service.ProductMovementsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMovementsServiceImpl implements ProductMovementsService {
    private final ProductMovementsRepository productMovementsRepository;
    private final ProductMovementsMapper productMovementsMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    @Override
    public ProductMovementsResponseDTO createProductMovements(ProductMovementsRequestDTO dto) {
        MovementType.validateEnum(dto.type());
        ProductMovements productMovements = productMovementsMapper.toEntity(dto);
        productMovements = productMovementsRepository.save(productMovements);
        auditService.logInsert("ProductMovements", dto, productMovements);
        return productMovementsMapper.toResponse(productMovements);
    }

    @Override
    public ProductMovementsResponseDTO updateProductMovementsResponse(ProductMovementsRequestDTO dto, Long id) {
        ProductMovements productMovements = productMovementsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el movimiento a actualizar: " + id));
        MovementType.validateEnum(dto.type());
        productMovementsMapper.updateEntityFromDto(dto, productMovements);
        productMovements.setProduct(productRepository.findById(dto.productId()).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto: " + dto.productId())));
        productMovements.setUser(userRepository.findById(dto.userId()).orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario: " + dto.userId())));
        productMovements = productMovementsRepository.save(productMovements);
        auditService.logUpdate("ProductMovements", dto, productMovements);
        return productMovementsMapper.toResponse(productMovements);
    }

    @Override
    public ProductMovementsResponseDTO getProductMovementById(Long id) {
        return productMovementsMapper.toResponse(productMovementsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el movimiento buscado: " + id)));
    }

    @Override
    public List<ProductMovementsResponseDTO> getAllProductMovements() {
        return productMovementsRepository.findAll().stream().map(productMovementsMapper::toResponse).toList();
    }

    @Override
    public void deleteProductMovements(Long id) {
        ProductMovements productMovements = productMovementsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el movimiento a eliminar: " + id));
        productMovementsRepository.delete(productMovements);
        auditService.logInsert("ProductMovements", productMovements, productMovements);
    }

    @Override
    public List<ProductMovementsResponseDTO> findProductMovementsFilters(Specification<ProductMovements> spec) {
        return productMovementsRepository.findAll(spec).stream().map(productMovementsMapper::toResponse).toList();
    }

}
