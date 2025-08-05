package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdAndNameIgnoreCase(Long id, String name);
}
