package com.ms_pedido.pedido.infrastructure.adapter.dto;

import com.ms_pedido.pedido.domain.model.EstadoPago;
import com.ms_pedido.pedido.domain.model.EstadoPedido;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDto {

    private String id;
    private Long userId;
    //private List<ItemPedido> items;
    //private DireccionEnvio direccion;
    private EstadoPago pagoEstado;
    private EstadoPedido pedidoEstado;
    private String mensaje;
    public record ItemPedidoDto(Long productoId, String nombre, Integer cantidad, BigDecimal precio, BigDecimal subtotal) {}
    public record DireccionEnvioDto(String calle, String ciudad, String pais, String codigoPostal) {}
    private List<ItemPedidoDto> items;
    private DireccionEnvioDto direccion;
    private BigDecimal calcularMontoTotal;

    public PedidoResponseDto(String mensaje) {
        this.mensaje = mensaje;
    }
}
