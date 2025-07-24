package io.daviddm.inventory_audit_api.service.impl;

import io.daviddm.inventory_audit_api.dto.request.ProductRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductResponseDTO;
import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.ProductMapper;
import io.daviddm.inventory_audit_api.model.Product;
import io.daviddm.inventory_audit_api.repository.*;
import io.daviddm.inventory_audit_api.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        ProductStatus.validateEnum(dto.status());
        Product product = productMapper.toEntity(dto);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto a actualizar: " + id));
        ProductStatus.validateEnum(dto.status());
        productMapper.updateEntityFromDto(dto, product);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        return productMapper.toResponse(productRepository.save(product));
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
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategoryName(String name) {
        return productRepository.findAllByCategory_NameIgnoreCase(name).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategoryId(Long id) {
        return productRepository.findAllByCategory_Id(id).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByBrandName(String name) {
        return productRepository.findAllByBrand_NameIgnoreCase(name).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByBrandId(Long id) {
        return productRepository.findAllByBrand_Id(id).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByWarehouseName(String name) {
        return productRepository.findAllByWarehouse_NameIgnoreCase(name).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByWarehouseId(Long id) {
        return productRepository.findAllByWarehouse_Id(id).stream().map(productMapper::toResponse).toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByStatus(String status) {
        ProductStatus productStatus=ProductStatus.validateEnum(status);
        return productRepository.findAllByStatus(productStatus).stream().map(productMapper::toResponse).toList();
    }
}
