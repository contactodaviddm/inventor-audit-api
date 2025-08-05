package io.daviddm.inventory_audit_api.model;

import io.daviddm.inventory_audit_api.enums.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMovements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Integer quantity;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private MovementType type;
    private String status;
}
