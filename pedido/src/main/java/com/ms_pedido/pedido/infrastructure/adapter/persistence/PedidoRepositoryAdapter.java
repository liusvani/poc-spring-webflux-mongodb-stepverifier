package com.ms_pedido.pedido.infrastructure.adapter.persistence;


import com.ms_pedido.pedido.domain.model.Pedido;
import com.ms_pedido.pedido.domain.port.PedidoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PedidoRepositoryAdapter implements PedidoRepository {
    private final MongoPedidoRepository mongoRepository;

    public PedidoRepositoryAdapter(MongoPedidoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Flux<Pedido> findAll() {
        return mongoRepository.findAll();
    }

    @Override
    public Mono<Pedido> findById(String id) {
        return mongoRepository.findById(id); // ✔️ reactivo
    }

    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return mongoRepository.save(pedido);
    }

    @Override
    public Mono<Void> deleteById(String id) {

        return mongoRepository.deleteById(id);
    }


}

