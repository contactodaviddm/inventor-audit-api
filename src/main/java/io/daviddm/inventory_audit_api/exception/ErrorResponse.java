package io.daviddm.inventory_audit_api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String message, String errorCode) {
}
