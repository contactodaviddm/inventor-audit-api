package io.daviddm.inventory_audit_api.model;

import io.daviddm.inventory_audit_api.enums.AuditOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "entity_name",nullable = false)
    private String entityName;
    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, updatable = false)
    private AuditOperation operation;
    @Column(name = "data_before",columnDefinition = "JSON", nullable = false)
    private String dataBefore;
    @Column(name = "data_after", columnDefinition = "JSON", nullable = false)
    private String dataAfter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @CreationTimestamp
    private LocalDateTime date;
}
