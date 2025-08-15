package com.ms_pedido.pedido;



import com.ms_pedido.pedido.application.PedidoService;
import com.ms_pedido.pedido.domain.model.Pedido;
import com.ms_pedido.pedido.domain.port.PedidoRepository;
import com.ms_pedido.pedido.infrastructure.adapter.util.MensajeRespuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoRepository repository;
    private PedidoService service;

    @BeforeEach
    void setUp() {
        repository = mock(PedidoRepository.class);
        service = new PedidoService(repository);
    }

    @Test
    void testCrearPedido() {
        Pedido pedido = new Pedido();
        pedido.setUserId(57L);
        pedido.setId(UUID.randomUUID().toString());

        when(repository.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        StepVerifier.create(service.crearPedido(pedido))
                .expectNextMatches(p -> p.getId().equals(pedido.getId()))
                .verifyComplete();
    }

    @Test
    void testListarPedidos() {
        Pedido pedido1 = new Pedido();
        pedido1.setId("1");
        Pedido pedido2 = new Pedido();
        pedido2.setId("2");


        when(repository.findAll()).thenReturn(Flux.just(pedido1, pedido2));

        StepVerifier.create(service.listarPedidos())
                .expectNext(pedido1)
                .expectNext(pedido2)
                .verifyComplete();
    }

    @Test
    void testObtenerPorId_found() {
        Pedido pedido = new Pedido();
        pedido.setId("123");

        when(repository.findById("123")).thenReturn(Mono.just(pedido));

        StepVerifier.create(service.obtenerPorId("123"))
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful()
                        && ((Pedido) response.getBody()).getId().equals("123"))
                .verifyComplete();
    }

    @Test
    void testObtenerPorId_notFound() {
        when(repository.findById("123")).thenReturn(Mono.empty());

        StepVerifier.create(service.obtenerPorId("123"))
                .expectNextMatches(response -> response.getStatusCode().is4xxClientError()
                        && ((MensajeRespuesta) response.getBody()).getMensaje().contains("no encontrado"))
                .verifyComplete();
    }

    @Test
    void testEliminarPedido_found() {
        Pedido pedido = new Pedido();
        pedido.setId("abc");

        when(repository.findById("abc")).thenReturn(Mono.just(pedido));
        when(repository.deleteById("abc")).thenReturn(Mono.empty());

        StepVerifier.create(service.eliminarPedido("abc"))
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful()
                        && ((MensajeRespuesta) response.getBody()).getMensaje().contains("eliminado"))
                .verifyComplete();
    }

    @Test
    void testEliminarPedido_notFound() {
        when(repository.findById("xyz")).thenReturn(Mono.empty());

        StepVerifier.create(service.eliminarPedido("xyz"))
                .expectNextMatches(response -> response.getStatusCode().is4xxClientError()
                        && ((MensajeRespuesta) response.getBody()).getMensaje().contains("no encontrado"))
                .verifyComplete();
    }
}

