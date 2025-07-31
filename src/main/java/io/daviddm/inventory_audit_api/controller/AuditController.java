package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.model.Audit;
import io.daviddm.inventory_audit_api.service.AuditService;
import io.daviddm.inventory_audit_api.specification.AuditSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getAuditById(id));
    }

    @GetMapping("/filters")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByFilters(@RequestParam(required = false) String entityName,
                                                                     @RequestParam(required = false) String operation,
                                                                     @RequestParam(required = false) Long userId,
                                                                     @RequestParam(required = false) Long userDocument,
                                                                     @RequestParam(required = false) LocalDate date,
                                                                     @RequestParam(required = false) LocalDate dateStart,
                                                                     @RequestParam(required = false) LocalDate dateEnd) {
        ProductStatus.validateEnum(operation);
        Specification<Audit> spec = AuditSpecification.findByAllFilters(entityName, operation, userId, userDocument, date, dateStart, dateEnd);
        return ResponseEntity.ok(auditService.getAuditsByFilters(spec));
    }
}
