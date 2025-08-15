package com.ms_pedido.pedido.infrastructure.adapter.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder mensaje = new StringBuilder("");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            mensaje.append("- ").append(error.getDefaultMessage()).append("\n");
        });

        return ResponseEntity
                .badRequest()
                .header("Content-Type", "text/plain; charset=UTF-8")
                .body(mensaje.toString());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        //"error", "Error",
                        //"mensaje", ex.getMessage().lines().toList()
                        "mensaje", ex.getMessage().lines().toList()
                ));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("errores", ex.getMessage().lines().toList()));
    }

}
