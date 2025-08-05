package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import org.springframework.transaction.annotation.Transactional;

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
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        if (productRepository.existsByCodeIgnoreCase(dto.code()))
            throw new BusinessRuleException("El codigo del producto ya fue asignado, intente con otro.");
        ProductStatus.validateEnum(dto.status());
        Product product = productMapper.toEntity(dto);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        product = productRepository.save(product);
        auditService.logInsert("Product", null, product);
        return productMapper.toResponse(product);
    }

    private ObjectNode ConvertToFormatJson(Product product) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ObjectNode node = objectMapper.createObjectNode();

        node.put("id", product.getId());
        node.put("code", product.getCode());
        node.put("name", product.getName());
        node.put("stock", product.getStock());
        node.put("shelf", product.getShelf());
        node.put("price", product.getPrice());
        node.put("status", product.getStatus().toString());

        ObjectNode categoryNode = objectMapper.createObjectNode();
        categoryNode.put("id", product.getCategory().getId());
        categoryNode.put("name", product.getCategory().getName());
        node.set("category", categoryNode);

        ObjectNode brandNode = objectMapper.createObjectNode();
        brandNode.put("id", product.getBrand().getId());
        brandNode.put("name", product.getBrand().getName());
        node.set("brand", brandNode);

        ObjectNode warehouseNode = objectMapper.createObjectNode();
        warehouseNode.put("id", product.getWarehouse().getId());
        warehouseNode.put("name", product.getWarehouse().getName());
        warehouseNode.put("address", product.getWarehouse().getAddress());
        node.set("warehouse", warehouseNode);

        return node;
    }

    @Transactional
    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO dto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto a actualizar: " + id));
        Object productBefore = ConvertToFormatJson(product);
        if (productRepository.existsByCodeIgnoreCase(dto.code()) && !productRepository.existsByIdAndCodeIgnoreCase(id, dto.code()))
            throw new BusinessRuleException("El codigo del producto pertenece a otro registro, intente con otro.");
        ProductStatus.validateEnum(dto.status());
        productMapper.updateEntityFromDto(dto, product);
        product.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la categoria: " + dto.categoryId())));
        product.setBrand(brandRepository.findById(dto.brandId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la marca: " + dto.brandId())));
        product.setWarehouse(warehouseRepository.findById(dto.warehouseId()).orElseThrow(() -> new EntityNotFoundException("No se encontró la bodega: " + dto.warehouseId())));
        Object productAfter = ConvertToFormatJson(product);
        if (productBefore.equals(productAfter)) throw new BusinessRuleException("No hay cambios a actualizar");
        product = productRepository.save(product);
        auditService.logUpdate("Product", productBefore, productAfter);
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

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el producto a eliminar: " + id));
        if (productMovementsRepository.existsByProduct_Id(id))
            throw new BusinessRuleException("No se puede eliminar el producto, porque tiene movimientos asociados.");
        productRepository.delete(product);
        auditService.logDelete("Product", ConvertToFormatJson(product), null);
    }

    @Override
    public List<ProductResponseDTO> findAllByFilters(Specification<Product> spec) {
        return productRepository.findAll(spec).stream().map(productMapper::toResponse).toList();
    }
}
