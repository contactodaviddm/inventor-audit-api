package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.daviddm.inventory_audit_api.dto.request.BrandRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.BrandResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.BrandMapper;
import io.daviddm.inventory_audit_api.model.Brand;
import io.daviddm.inventory_audit_api.repository.BrandRepository;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ProductRepository productRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO dto) {
        if (brandRepository.existsByNameIgnoreCase(dto.name()))
            throw new BusinessRuleException("La marca ingresada ya está registrada, intente con una distinta.");
        Brand brand = brandMapper.toEntity(dto);
        brand = brandRepository.save(brand);
        auditService.logInsert("Brand", null, brand);
        return brandMapper.toResponse(brand);
    }

    @Transactional
    @Override
    public BrandResponseDTO updateBrand(BrandRequestDTO dto, Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca a actualizar: " + id));
        if (brand.getName().equals(dto.name())) throw new BusinessRuleException("No hay cambios para actualizar");
        Brand brandBefore = objectMapper.convertValue(brand, Brand.class);
        if (brandRepository.existsByNameIgnoreCase(dto.name()) && !brandRepository.existsByIdAndNameIgnoreCase(id, dto.name()))
            throw new BusinessRuleException("La marca ingresada ya está asignada a otro registro, intente con una distinta.");
        brandMapper.updateEntityFromDto(dto, brand);
        brand = brandRepository.save(brand);
        auditService.logUpdate("Brand", brandBefore, brand);
        return brandMapper.toResponse(brand);
    }

    @Override
    public BrandResponseDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca buscada: " + id));
        return brandMapper.toResponse(brand);
    }

    @Override
    public List<BrandResponseDTO> getAllBrand() {
        return brandRepository.findAll().stream().map(brandMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca a eliminar: " + id));
        if (productRepository.existsByBrand_Id(id))
            throw new BusinessRuleException("No se puede eliminar la marca, tiene productos asignados");
        brandRepository.delete(brand);
        auditService.logDelete("Brand", brand, null);
    }
}
