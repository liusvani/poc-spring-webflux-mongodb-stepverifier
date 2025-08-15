package com.ms_pedido.pedido.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ms_pedido.pedido.infrastructure.adapter.handler.ValidationException;

public enum EstadoPedido {
    PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO, CANCELADO;

    @JsonCreator
    public static EstadoPedido from(String value) {
        for (EstadoPedido estado : values()) {
            if (estado.name().equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new ValidationException("Estado del pedido es inválido. Las opciones son PENDIENTE, PROCESANDO, ENVIADO, ENTREGADO y CANCELADO");
    }
}
