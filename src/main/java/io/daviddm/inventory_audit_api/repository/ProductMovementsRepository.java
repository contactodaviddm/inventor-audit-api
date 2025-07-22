package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Long> {
}
