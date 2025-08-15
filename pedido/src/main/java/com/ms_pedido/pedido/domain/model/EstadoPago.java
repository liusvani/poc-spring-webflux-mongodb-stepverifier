package com.ms_pedido.pedido.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ms_pedido.pedido.infrastructure.adapter.handler.ValidationException;

public enum EstadoPago {
    PENDIENTE, PAGADO, FALLIDO;

    @JsonCreator
    public static EstadoPago from(String value) {
        for (EstadoPago estado : values()) {
            if (estado.name().equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new ValidationException("Estado del pago es inválido. Las opciones son PENDIENTE, PAGADO y FALLIDO");
    }
}
