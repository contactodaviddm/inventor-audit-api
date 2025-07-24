package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.enums.AuditOperation;
import io.daviddm.inventory_audit_api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByEntityName(String name);

    List<Audit> findByDate(LocalDateTime date);

    List<Audit> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<Audit> findByEntityNameAndDate(String name, LocalDateTime date);

    List<Audit> findByEntityNameAndDateBetween(String name, LocalDateTime start, LocalDateTime end);

    boolean existsByUser_Id(Long id);

    List<Audit> findByOperation(AuditOperation operation);
}
