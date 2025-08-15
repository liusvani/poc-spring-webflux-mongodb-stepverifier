package com.ms_pedido.pedido;

import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class PedidoBackpressureTest {

    public static void main(String[] args) {
        //Crea un cliente HTTP reactivo
        WebClient client = WebClient.create("http://localhost:8080");

        Flux<PedidoResponseDto> responseFlux = client.get()
                .uri("/pedido")
                .retrieve()
                .bodyToFlux(PedidoResponseDto.class)
                .delayElements(Duration.ofSeconds(2)) // Simula consumidor lento
                .doOnNext(dto -> System.out.println("Recibido: " + dto))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .subscribeOn(Schedulers.boundedElastic());
        responseFlux.blockLast();
    }
    @Data
    static class PedidoResponseDto {
        private String id;
        private String pagoEstado;
        private String pedidoEstado;

        // Getters y setters

        @Override
        public String toString() {
            return "PedidoResponseDto{id='" + id + "', estado de pago='" + pagoEstado + "', estado de pedido='" + pedidoEstado + "'}";
        }
    }
}

