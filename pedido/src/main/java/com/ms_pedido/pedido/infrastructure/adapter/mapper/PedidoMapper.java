package com.ms_pedido.pedido.infrastructure.adapter.mapper;

import com.ms_pedido.pedido.domain.model.DireccionEnvio;
import com.ms_pedido.pedido.domain.model.ItemPedido;
import com.ms_pedido.pedido.domain.model.Pedido;
import com.ms_pedido.pedido.infrastructure.adapter.dto.PedidoRequestDto;
import com.ms_pedido.pedido.infrastructure.adapter.dto.PedidoResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    public static Pedido toEntity(PedidoRequestDto dto) {
        Pedido pedido = new Pedido();

        pedido.setUserId(dto.getUserId());
        pedido.setItems(dto.getItems());
        pedido.setDireccion(dto.getDireccion());
        pedido.setPagoEstado(dto.getPagoEstado());
        pedido.setPedidoEstado(dto.getPedidoEstado());
        return pedido;
    }

    public static PedidoResponseDto toDto(Pedido pedido) {
        PedidoResponseDto responseDto = new PedidoResponseDto();

        responseDto.setId(pedido.getId());
        responseDto.setUserId(pedido.getUserId());
        responseDto.setPagoEstado(pedido.getPagoEstado());
        responseDto.setPedidoEstado(pedido.getPedidoEstado());
        responseDto.setCalcularMontoTotal(pedido.calcularMontoTotal());

        // Mapear items
        List<PedidoResponseDto.ItemPedidoDto> itemsDto = pedido.getItems().stream()
                .map(item -> new PedidoResponseDto.ItemPedidoDto(
                        item.getProductoId(),
                        item.getNombre(),
                        item.getCantidad(),
                        item.getPrecio(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        responseDto.setItems(itemsDto);

        // Mapear dirección
        DireccionEnvio direccion = pedido.getDireccion();
        PedidoResponseDto.DireccionEnvioDto direccionDto = new PedidoResponseDto.DireccionEnvioDto(
                direccion.getCalle(),
                direccion.getCiudad(),
                direccion.getPais(),
                direccion.getCodigoPostal()
        );

        responseDto.setDireccion(direccionDto);

        return responseDto;
    }
}

