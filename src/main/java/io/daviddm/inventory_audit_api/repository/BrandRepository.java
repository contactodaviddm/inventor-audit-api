package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
