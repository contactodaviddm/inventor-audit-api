package io.daviddm.inventory_audit_api.enums;

import io.daviddm.inventory_audit_api.exception.BusinessRuleException;

public enum ProductStatus {
    ACTIVO, AGOTADO;

    public static ProductStatus validateEnum(String value) {
        try {
            return ProductStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessRuleException("El estado ingresado no es válido");
        }
    }
}
