package com.ms_pedido.pedido;


import com.ms_pedido.pedido.infrastructure.adapter.dto.PedidoResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ClienteSimuladorTest {
    List<PedidoResponseDto> descartados = new ArrayList<>();
    @Test
    public void simularClienteLento() throws InterruptedException {
        Flux<PedidoResponseDto> flujo = WebClient.create("http://localhost:8080")
                .get()
                .uri("/pedido")
                .retrieve()
                .bodyToFlux(PedidoResponseDto.class)
                .onBackpressureBuffer(
                        2,
                        pedido -> {
                            System.out.println("Buffer lleno. Pedido descartado: " + pedido.getId());
                            descartados.add(pedido); // Guardar el pedido descartado
                        },
                        BufferOverflowStrategy.DROP_LATEST
                )
                .delayElements(Duration.ofMillis(5000))
                .doOnNext(pedido -> System.out.println("Recibido: " + pedido.getId()));

        flujo.count()
                .doOnNext(total -> {
                    System.out.println("Cantidad total de pedidos recibidos: " + total);
                    descartados.forEach(p -> System.out.println("Descartado: " + p.getId()));
                    System.out.println("Cantidad total de pedidos descartados:"+descartados.size()+"");
                })
                .subscribe();

        Thread.sleep(40000);
    }

}

