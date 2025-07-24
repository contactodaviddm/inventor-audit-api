package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
