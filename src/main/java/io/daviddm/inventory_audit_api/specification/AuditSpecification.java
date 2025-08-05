package io.daviddm.inventory_audit_api.specification;

import io.daviddm.inventory_audit_api.model.Audit;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AuditSpecification {
    public static Specification<Audit> findByAllFilters(String entityName, String operation, Long userId, Long userDocument, LocalDate date, LocalDate dateStart, LocalDate dateEnd) {
        Specification<Audit> spec = (root, query, cb) -> cb.conjunction();
        if (entityName != null && !entityName.isBlank()) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("entityName"), entityName)));
        }
        if (operation != null && !operation.isBlank()) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("operation"), operation)));
        }
        if (userId != null && userId > 0) {
            spec = spec.and(((root, query, cb) -> cb.equal(root.get("user").get("id"), userId)));
        } else {
            if (userDocument != null && userDocument > 0) {
                spec = spec.and(((root, query, cb) -> cb.equal(root.get("user").get("documentNumber"), userDocument)));
            }
        }
        if (date != null) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.function("DATE", LocalDate.class, root.get("date")), date));
        } else {
            if (dateStart != null && dateEnd != null) {
                LocalDateTime start=dateStart.atStartOfDay();
                LocalDateTime end=dateEnd.atTime(LocalTime.MAX);
                spec = spec.and((root, query, cb) -> cb.between(root.get("date"), start, end));
            }
        }
        return spec;
    }
}
