package com.ms_pedido.pedido.application;

import com.ms_pedido.pedido.domain.model.Pedido;
import com.ms_pedido.pedido.domain.port.PedidoRepository;
import com.ms_pedido.pedido.infrastructure.adapter.mapper.PedidoMapper;
import com.ms_pedido.pedido.infrastructure.adapter.util.MensajeRespuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PedidoService {
    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public Mono<Pedido> crearPedido(Pedido pedido) {
        System.err.println("Servicio.");
        return repository.save(pedido);
    }


    public Flux<Pedido> listarPedidos() {
        return repository.findAll();
    }


    public Mono<ResponseEntity<Object>> obtenerPorId(String id) {
        return repository.findById(id)
                .map(pedido -> ResponseEntity.ok((Object) pedido))
                .switchIfEmpty(
                        Mono.just(ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new MensajeRespuesta("Pedido con ID " + id + " no encontrado")))
                );
    }

    public Mono<ResponseEntity<MensajeRespuesta>> eliminarPedido(String id) {
        return repository.findById(id)
                .flatMap(pedido -> repository.deleteById(pedido.getId())
                        .thenReturn(ResponseEntity
                                .status(HttpStatus.OK)
                                .body(new MensajeRespuesta("Pedido eliminado correctamente."))))
                .switchIfEmpty(Mono.just(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new MensajeRespuesta("Pedido no encontrado."))));
    }


}
