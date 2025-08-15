package com.ms_pedido.pedido.infrastructure.adapter.handler;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}

