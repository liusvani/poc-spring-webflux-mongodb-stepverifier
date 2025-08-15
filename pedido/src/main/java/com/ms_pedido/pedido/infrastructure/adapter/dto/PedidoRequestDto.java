package com.ms_pedido.pedido.infrastructure.adapter.dto;

import com.ms_pedido.pedido.domain.model.DireccionEnvio;
import com.ms_pedido.pedido.domain.model.EstadoPago;
import com.ms_pedido.pedido.domain.model.EstadoPedido;
import com.ms_pedido.pedido.domain.model.ItemPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class PedidoRequestDto {
    @NotNull(message = "ID es obligatorio.")
    private Long userId;

    @NotNull(message = "La lista de ítems no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un ítem en el pedido")
    @Valid
    private List<ItemPedido> items;

    @NotNull(message = "El monto total es obligatorio")
    @Positive
    private BigDecimal montoTotal;


    @NotNull(message = "La dirección de envío es obligatorio.")
    private DireccionEnvio direccion;

    @NotNull(message = "El estado del pago no puede ser nulo")
    @Valid
    private EstadoPago pagoEstado;

    @NotNull(message = "El estado del pedido no puede ser nulo")
    @Valid
    private EstadoPedido pedidoEstado;
}
