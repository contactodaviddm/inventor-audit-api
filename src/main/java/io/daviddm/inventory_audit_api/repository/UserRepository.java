package io.daviddm.inventory_audit_api.repository;

import io.daviddm.inventory_audit_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailIgnoreCase(String value);
    boolean existsByIdAndEmailIgnoreCase(Long id, String value);
    boolean existsByDocumentNumber(Long document);
    boolean existsByIdAndDocumentNumber(Long id, Long document);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByIdAndPhoneNumber(Long id, String phoneNumber);
}
