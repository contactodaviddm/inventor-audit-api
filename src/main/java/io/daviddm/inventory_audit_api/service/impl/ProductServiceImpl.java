package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.ProductRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductResponseDTO;
import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.ProductMapper;
import io.daviddm.inventory_audit_api.model.Product;
import io.daviddm.inventory_audit_api.repository.*;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductMovementsRepository productMovementsRepository;
    private final AuditService auditService;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        ProductStatus.validateEnum(dto.status());
        Product product = productMapper.toEntity(dto);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        product = productRepository.save(product);
        auditService.logInsert("Product", dto, product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto a actualizar: " + id));
        ProductStatus.validateEnum(dto.status());
        productMapper.updateEntityFromDto(dto, product);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        product = productRepository.save(product);
        auditService.logUpdate("Product", dto, product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto buscado: " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toResponse).toList();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto a eliminar: " + id));
        if (productMovementsRepository.existsByProduct_Id(id))
            throw new BusinessRuleException("No se puede eliminar el producto, porque tiene movimientos asociados.");
        productRepository.delete(product);
        auditService.logDelete("Product", product, product);
    }

    @Override
    public List<ProductResponseDTO> findAllByFilters(Specification<Product> spec) {
        return productRepository.findAll(spec).stream().map(productMapper::toResponse).toList();
    }


}
