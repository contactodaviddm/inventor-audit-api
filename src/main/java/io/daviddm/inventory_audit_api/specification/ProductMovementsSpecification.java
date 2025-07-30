package io.daviddm.inventory_audit_api.specification;

import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductMovementsSpecification {
    public static Specification<ProductMovements> findByFilters(Long productId,
                                                                String productCode,
                                                                String productName,
                                                                Long userId,
                                                                Integer quantity,
                                                                Integer quantityMax,
                                                                Integer quantityMin,
                                                                LocalDate date,
                                                                LocalDate dateMax,
                                                                LocalDate dateMin,
                                                                String type) {
        Specification<ProductMovements> spec = (root, query, cb) -> cb.conjunction();
        if (productId != null) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("product").get("id"), productId)));
        }
        if (productName != null && !productName.trim().isEmpty()) {
            spec = spec.and(((root, query, cb) ->
                    cb.like(root.get("product").get("name"), "%" + productName + "%")));
        }
        if (productCode != null && !productCode.trim().isEmpty()) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("product").get("code"), productCode)));
        }
        if (userId != null) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId)));
        }
        if (quantity != null) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("quantity"), quantity)));
        } else {
            if (quantityMin != null) {
                spec = spec.and(((root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get("quantity"), quantityMin)));
            }
            if (quantityMax != null) {
                spec = spec.and(((root, query, cb) ->
                        cb.lessThanOrEqualTo(root.get("quantity"), quantityMax)));
            }
        }
        if (date != null) {
            spec = spec.and(((root, query, cb) ->
                    cb.equal(root.get("date"), date)));
        } else {
            if (dateMin != null) {
                spec = spec.and(((root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get("date"), dateMin)));
            }
            if (dateMax != null) {
                spec = spec.and(((root, query, cb) ->
                        cb.lessThanOrEqualTo(root.get("date"), dateMax)));
            }
        }
        if (type != null && !type.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("type"), type));
        }
        return spec;
    }
}
