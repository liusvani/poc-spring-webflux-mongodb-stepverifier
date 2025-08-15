package com.ms_pedido.pedido.infrastructure.adapter.web;

import com.ms_pedido.pedido.application.PedidoService;
import com.ms_pedido.pedido.domain.model.Pedido;
import com.ms_pedido.pedido.domain.port.PedidoRepository;
import com.ms_pedido.pedido.infrastructure.adapter.dto.PedidoRequestDto;
import com.ms_pedido.pedido.infrastructure.adapter.dto.PedidoResponseDto;
import com.ms_pedido.pedido.infrastructure.adapter.handler.BusinessException;
import com.ms_pedido.pedido.infrastructure.adapter.handler.ValidationException;
import com.ms_pedido.pedido.infrastructure.adapter.mapper.PedidoMapper;
import com.ms_pedido.pedido.infrastructure.adapter.util.MensajeRespuesta;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    private final Validator validator;


    @PostMapping
    public Mono<ResponseEntity<MensajeRespuesta>> crearPedido(@RequestBody Mono<PedidoRequestDto> dtoMono) {
        return dtoMono
                .flatMap(dto -> {
                    Set<ConstraintViolation<PedidoRequestDto>> violaciones = validator.validate(dto);
                    if (!violaciones.isEmpty()) {
                        String errores = violaciones.stream()
                                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                                .collect(Collectors.joining(", "));
                        //return Mono.error(new ValidationException("Campo X es obligatorio"));
                        return Mono.error(new ValidationException(errores));
                    }
                    return Mono.just(dto);
                })
                .map(PedidoMapper::toEntity)
                .delayElement(Duration.ofSeconds(1))
                .flatMap(pedido -> pedidoService.crearPedido(pedido)
                        .thenReturn(ResponseEntity.ok(new MensajeRespuesta("Pedido creado exitosamente")))
                );
    }


    @PostMapping("/test")
    public ResponseEntity<?> test() {
        throw new ValidationException("Campo X es obligatorio");
    }


    // Listar todos los pedidos
    @GetMapping()
    public Flux<PedidoResponseDto> listarPedidosConBuffer() {
        return pedidoService.listarPedidos()
                //.limitRate(10)// controla cuántos elementos se solicitan a la vez
                .onBackpressureBuffer(
                        20, // tamaño del buffer elementos a guardar mientras backpressure
                        pedido -> System.out.println("Buffer lleno. Pedido descartado: " + pedido.getId())
                       // BufferOverflowStrategy.DROP_LATEST //DROP_LATEST deja retornar los elementos botados pero DROP_OLDEST no deja retornanrlos
                )
                .delayElements(Duration.ofMillis(500)) //tiempo en segundos demora en devolver cada elemento
                .map(PedidoMapper::toDto)
                .switchIfEmpty(Flux.error(new BusinessException("No hay pedidos registrados.")));
    }


    // Buscar por ID
    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> obtenerPedidoPorId(@PathVariable String id) {
        return pedidoService.obtenerPorId(id)
                .map(response -> {
                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof Pedido pedido) {
                        PedidoResponseDto dto = PedidoMapper.toDto(pedido);
                        return ResponseEntity.ok(dto);
                    }
                    return response;
                });
    }

    // Eliminar por ID
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<MensajeRespuesta>> eliminarPedido(@PathVariable String id) {
        return pedidoService.eliminarPedido(id);
    }
}

