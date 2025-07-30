package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.response.AuditResponseDTO;
import io.daviddm.inventory_audit_api.service.AuditService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@Validated
public class AuditController {
    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<AuditResponseDTO>> getAllAuditsByDate(
            @RequestParam @NotNull(message = "No puede ser nula la fecha")
            @PastOrPresent(message = "La fecha no puede superar el dia actual") LocalDate date) {
        return ResponseEntity.ok(auditService.getAllAuditByDate(date));
    }

    @GetMapping("/by-date-between")
    public ResponseEntity<List<AuditResponseDTO>> getAllAuditsByDateBetween(
            @RequestParam @NotNull @PastOrPresent(message = "La fecha no puede superar el dia actual") LocalDate start,
            @RequestParam @NotNull @PastOrPresent(message = "La fecha no puede superar el dia actual") LocalDate end) {
        return ResponseEntity.ok(auditService.getAllAuditByDateBetween(start, end));
    }

    @GetMapping("/by-entity")
    public ResponseEntity<List<AuditResponseDTO>> getAllAuditsByEntity(
            @RequestParam(name = "entity") @NotBlank(message = "No puede estar vacía la entidad") String name) {
        return ResponseEntity.ok(auditService.getAuditsByEntity(name));
    }

    @GetMapping("/by-entity-and-date")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByEntityAndDate(
            @RequestParam(name = "entity") @NotBlank(message = "No puede estar vacía la entidad") String name,
            @RequestParam @NotNull(message = "No puede ser nula la fecha") @PastOrPresent(message = "La fecha no puede superar el dia actual") LocalDate date) {
        return ResponseEntity.ok(auditService.getAuditsByEntityAndDate(name, date));
    }

    @GetMapping("/by-entity-and-date-between")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByEntityAndDateBetween(
            @RequestParam(name = "entity") @NotBlank(message = "No puede estar vacía la entidad") String name,
            @RequestParam @NotNull(message = "No puede ser nula la fecha") @PastOrPresent(message = "La fecha no puede superar el dia actual")  LocalDate start,
            @RequestParam @NotNull(message = "No puede ser nula la fecha") @PastOrPresent(message = "La fecha no puede superar el dia actual") LocalDate end) {
        return ResponseEntity.ok(auditService.getAuditsByEntityAndDateBetween(name, start, end));
    }

    @GetMapping("/by-operation")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByOperation(
            @RequestParam(name = "operation") @NotBlank(message = "No puede estar vacía la operación") String name) {
        return ResponseEntity.ok(auditService.getAuditsByOperation(name));
    }
}
