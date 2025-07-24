package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.BrandRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.BrandResponseDTO;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.BrandMapper;
import io.daviddm.inventory_audit_api.model.Brand;
import io.daviddm.inventory_audit_api.repository.BrandRepository;
import io.daviddm.inventory_audit_api.repository.ProductRepository;
import io.daviddm.inventory_audit_api.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ProductRepository productRepository;

    @Override
    public BrandResponseDTO createBrand(BrandRequestDTO dto) {
        Brand brand = brandMapper.toEntity(dto);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponseDTO updateBrand(BrandRequestDTO dto, Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca a actualizar: " + id));
        brandMapper.updateEntityFromDto(dto, brand);
        return brandMapper.toResponse(brandRepository.save(brand));
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

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca a eliminar: " + id));
        if (productRepository.existsBrand_Id(id))
            throw new BusinessRuleException("No se puede eliminar la marca, tiene productos asignados");
        brandRepository.delete(brand);
    }
}
