package io.daviddm.inventory_audit_api.enums;

import io.daviddm.inventory_audit_api.exception.BusinessRuleException;

public enum AuditOperation {
    INSERT, UPDATE, DELETE;

    public static AuditOperation validateEnum(String value) {
        try {
            return AuditOperation.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("El estado ingresado no es válido.");
        }
    }
}
