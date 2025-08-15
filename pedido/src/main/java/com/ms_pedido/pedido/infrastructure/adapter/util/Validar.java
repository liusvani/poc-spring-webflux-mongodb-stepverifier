package com.ms_pedido.pedido.infrastructure.adapter.util;

import com.ms_pedido.pedido.infrastructure.adapter.handler.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Component
@RequiredArgsConstructor
public class Validar {

    private final Validator validator;

    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            String errores = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("\n"));
            throw new BusinessException(errores);
        }
    }
}
