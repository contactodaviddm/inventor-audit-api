package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.MovementType;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Long>, JpaSpecificationExecutor<ProductMovements> {
    boolean existsByUser_Id(Long id);

    boolean existsByProduct_Id(Long id);
}
