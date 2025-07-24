package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.MovementType;
import io.daviddm.inventory_audit_api.model.ProductMovements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMovementsRepository extends JpaRepository<ProductMovements, Long> {
    boolean existsByUser_Id(Long id);

    boolean existsByProduct_Id(Long id);

    List<ProductMovements> findAllByProduct_Id(Long id);

    List<ProductMovements> findAllByProduct_Name(String name);

    List<ProductMovements> findAllByUser_Id(Long id);

    List<ProductMovements> findAllByType(MovementType type);

}
