package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByCategory_Id(Long id);

    boolean existsByBrand_Id(Long id);

    boolean existsByWarehouse_Id(Long id);
}
