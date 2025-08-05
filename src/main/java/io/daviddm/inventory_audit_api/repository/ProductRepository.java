package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByCategory_Id(Long id);

    boolean existsByBrand_Id(Long id);

    boolean existsByWarehouse_Id(Long id);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByIdAndCodeIgnoreCase(Long id, String code);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product p SET p.stock=p.stock+ :stock WHERE p.id= :id")
    void updateStockById(@Param("stock") Integer stock, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Product p
            SET p.status = 
                CASE 
                    WHEN p.stock = 0 THEN 'AGOTADO' 
                    ELSE 'ACTIVO' 
                END
            WHERE p.id = :id
            """)
    void updateStatusById(@Param("id") Long id);
}
