package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.MovementType;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Long>, JpaSpecificationExecutor<ProductMovements> {
    boolean existsByUser_Id(Long id);

    boolean existsByProduct_Id(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ProductMovements pm SET pm.status='INACTIVO' WHERE pm.id= :id")
    void inactivateProductMovement(@Param("id") Long id);
}
