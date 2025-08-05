package io.daviddm.inventory_audit_api.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daviddm.inventory_audit_api.dto.request.ProductMovementsRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.ProductMovementsResponseDTO;
import io.daviddm.inventory_audit_api.enums.MovementType;
import io.daviddm.inventory_audit_api.exception.BusinessRuleException;
import io.daviddm.inventory_audit_api.mapper.ProductMovementsMapper;
import io.daviddm.inventory_audit_api.model.Product;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMovementsServiceImpl implements ProductMovementsService {
    private final ProductMovementsRepository productMovementsRepository;
    private final ProductMovementsMapper productMovementsMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    private void manageStockToProduct(Integer quantity, Integer stock, String movementType, Long productId) {
        if ("ENTRADA".equals(movementType)) {
            productRepository.updateStockById(quantity, productId);
        } else {
            if (quantity > stock)
                throw new BusinessRuleException("La cantidad a sacar supera el stock del producto. Stock: " + stock);
            productRepository.updateStockById(quantity * -1, productId);
        }
        productRepository.updateStatusById(productId);
    }

    @Transactional
    @Override
    public ProductMovementsResponseDTO createProductMovements(ProductMovementsRequestDTO dto) {
        MovementType.validateEnum(dto.type());
        ProductMovements productMovements = productMovementsMapper.toEntity(dto);
        Product product = productRepository.findById(dto.productId()).orElseThrow(() -> new BusinessRuleException("No se encontró el producto: " + dto.productId()));
        productMovements.setProduct(product);
        //Temporary User
        productMovements.setUser(userRepository.findById(1L).orElseThrow(() -> new BusinessRuleException("No se encontró el usuario: " + 1)));
        productMovements.setStatus("ACTIVO");
        Object dataBefore = ConvertToFormatJson(productMovements);
        if (dto.quantity() <= 0) throw new BusinessRuleException("Ingrese una cantidad válida superior a 0");
        productMovements = productMovementsRepository.save(productMovements);
        manageStockToProduct(dto.quantity(), product.getStock(), dto.type(), dto.productId());
        productMovements.setProduct(productRepository.findById(dto.productId()).orElseThrow(() -> new BusinessRuleException("No se encontró el producto: " + dto.productId())));
        auditService.logInsert("ProductMovements", dataBefore, ConvertToFormatJson(productMovements));
        return productMovementsMapper.toResponse(productMovements);
    }

    private ObjectNode ConvertToFormatJson(ProductMovements productMovements) {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        ObjectNode node = objectMapper.createObjectNode();

        node.put("id", productMovements.getId());
        node.put("quantity", productMovements.getQuantity());
        node.put("date", String.valueOf(productMovements.getDate()));
        node.put("type", productMovements.getType().toString());

        ObjectNode productNode = objectMapper.createObjectNode();
        productNode.put("id", productMovements.getProduct().getId());
        productNode.put("code", productMovements.getProduct().getCode());
        productNode.put("name", productMovements.getProduct().getName());
        productNode.put("stock", productMovements.getProduct().getStock());
        node.set("product", productNode);

        ObjectNode userNode = objectMapper.createObjectNode();
        userNode.put("id", productMovements.getUser().getId());
        userNode.put("document", productMovements.getUser().getDocumentNumber());
        userNode.put("name", productMovements.getUser().getName());
        userNode.put("lastName", productMovements.getUser().getLastName());
        node.set("user", userNode);
        return node;
    }

    @Override
    public ProductMovementsResponseDTO getProductMovementById(Long id) {
        return productMovementsMapper.toResponse(productMovementsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el movimiento buscado: " + id)));
    }

    @Override
    public List<ProductMovementsResponseDTO> getAllProductMovements() {
        return productMovementsRepository.findAll().stream().map(productMovementsMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public void deleteProductMovements(Long id) {
        ProductMovements productMovements = productMovementsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No se encontró el movimiento a eliminar: " + id));
        if (productMovements.getStatus().equalsIgnoreCase("INACTIVO"))
            throw new BusinessRuleException("El movimiento ya se encuentra inactivo");
        Product product = productRepository.findById(productMovements.getProduct().getId()).orElseThrow(() -> new BusinessRuleException("No se encontró el producto buscado: " + id));
        productMovements.setProduct(product);
        //Temporary User
        productMovements.setUser(userRepository.findById(1L).orElseThrow(() -> new BusinessRuleException("No se encontró el usuario buscado: " + 1L)));
        Object dataBefore = ConvertToFormatJson(productMovements);
        if ("ENTRADA".equalsIgnoreCase(productMovements.getType().toString()) && productMovements.getQuantity() > product.getStock())
            throw new BusinessRuleException("No se puede inactivar el movimiento de ENTRADA porque el producto no tiene suficiente stock disponible para revertir la operación.");
        productMovementsRepository.inactivateProductMovement(id);
        manageStockToProduct(productMovements.getQuantity() * -1, product.getStock(), productMovements.getType().toString(), product.getId());
        productMovements.setProduct(productRepository.findById(productMovements.getProduct().getId()).orElseThrow(() -> new BusinessRuleException("No se encontró el producto buscado: " + id)));
        auditService.logInsert("ProductMovements", dataBefore, ConvertToFormatJson(productMovements));
    }

    @Override
    public List<ProductMovementsResponseDTO> findProductMovementsFilters(Specification<ProductMovements> spec) {
        return productMovementsRepository.findAll(spec).stream().map(productMovementsMapper::toResponse).toList();
    }

}
