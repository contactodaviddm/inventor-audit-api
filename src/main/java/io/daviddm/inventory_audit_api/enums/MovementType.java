package io.daviddm.inventory_audit_api.enums;

import io.daviddm.inventory_audit_api.exception.BusinessRuleException;

public enum MovementType {
    ENTRADA, SALIDA;

    public static MovementType validateEnum(String value) {
        try {
            return MovementType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessRuleException("El tipo de movimiento no es válido. Valores permitidos: ENTRADA, SALIDA");
        }
    }
}
