package io.daviddm.inventory_audit_api.service;

import io.daviddm.inventory_audit_api.dto.request.BrandRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.BrandResponseDTO;

import java.util.List;

public interface BrandService {
    BrandResponseDTO createBrand(BrandRequestDTO dto);

    BrandResponseDTO updateBrand(BrandRequestDTO dto, Long id);

    BrandResponseDTO getBrandById(Long id);

    List<BrandResponseDTO> getAllBrand();

    void deleteBrand(Long id);
}
