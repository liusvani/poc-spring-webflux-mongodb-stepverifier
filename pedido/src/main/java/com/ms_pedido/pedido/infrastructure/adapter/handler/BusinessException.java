package com.ms_pedido.pedido.infrastructure.adapter.handler;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}