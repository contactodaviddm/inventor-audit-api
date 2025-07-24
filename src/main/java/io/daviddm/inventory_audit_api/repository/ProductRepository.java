package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.ProductStatus;
import io.daviddm.inventory_audit_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsCategory_Id(Long id);

    boolean existsBrand_Id(Long id);

    boolean existsWarehouse_Id(Long id);

    List<Product> findAllByCategory_Id(Long id);

    List<Product> findAllByCategory_NameIgnoreCase(String categoryName);

    List<Product> findAllByBrand_Id(Long id);

    List<Product> findAllByBrand_NameIgnoreCase(String categoryName);

    List<Product> findAllByWarehouse_Id(Long id);

    List<Product> findAllByWarehouse_NameIgnoreCase(String categoryName);

    List<Product> findAllByStatus(ProductStatus productStatus);
}
