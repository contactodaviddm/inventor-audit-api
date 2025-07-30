package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.AuditOperation;
import io.daviddm.inventory_audit_api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByEntityName(String name);

    List<Audit> findByDate(LocalDate date);

    List<Audit> findByDateBetween(LocalDate start, LocalDate end);

    List<Audit> findByEntityNameAndDate(String name, LocalDate date);

    List<Audit> findByEntityNameAndDateBetween(String name, LocalDate start, LocalDate end);

    boolean existsByUser_Id(Long id);

    List<Audit> findByOperation(AuditOperation operation);
}
