package io.daviddm.inventory_audit_api.specification;

import io.daviddm.inventory_audit_api.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> findByFilters(Long categoryId, String categoryName,
                                                       Long brandId, String brandName,
                                                       Long warehouseId, String warehouseName,
                                                       String status, String productCode, String productName) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        if ((productCode != null && !productCode.isBlank())) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("code")), productCode.toLowerCase()));
        }

        if (productName != null && !productName.isBlank()) {
            spec = spec.and(((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + productName.toLowerCase() + "%")));
        }

        if (categoryId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("category").get("id"), categoryId));
        }
        if (categoryName != null && !categoryName.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("category").get("name")), "%" + categoryName.toLowerCase() + "%"));
        }
        if (brandId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("brand").get("id"), brandId));
        }
        if (brandName != null && !brandName.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("brand").get("name")), "%" + brandName.toLowerCase() + "%"));
        }
        if (warehouseId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("warehouse").get("id"), warehouseId));
        }
        if (warehouseName != null && !warehouseName.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("warehouse").get("name")), "%" + warehouseName.toLowerCase() + "%"));
        }
        if (status != null && !status.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
        }
        return spec;
    }

}
