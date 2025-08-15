package com.ms_pedido.pedido.infrastructure.adapter.persistence;

import com.ms_pedido.pedido.domain.model.Pedido;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface MongoPedidoRepository  extends ReactiveMongoRepository<Pedido, String> {
}
