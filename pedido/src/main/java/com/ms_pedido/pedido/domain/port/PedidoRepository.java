package com.ms_pedido.pedido.domain.port;

import com.ms_pedido.pedido.domain.model.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface PedidoRepository {
    Mono<Pedido> save(Pedido pedido);
    Mono<Pedido> findById(String id);
    Flux<Pedido> findAll();
    Mono<Void> deleteById(String id);

}
