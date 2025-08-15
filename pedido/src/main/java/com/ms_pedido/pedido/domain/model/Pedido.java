package com.ms_pedido.pedido.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    private String id;

    @NotNull
    @Indexed //indexar campos para hacer busquedas
    private Long userId;

    @NotNull(message = "La lista de ítems no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un ítem en el pedido")
    private List<ItemPedido> items;

    @NotNull
    @Positive
    private BigDecimal montoTotal;

    @Field(targetType = FieldType.STRING) //serialicen como string
    @NotNull(message = "La dirección de envío no puede ser nula")
    private DireccionEnvio direccion;

    @Field(targetType = FieldType.STRING) //serialicen como string
    private EstadoPago pagoEstado;

    @Field(targetType = FieldType.STRING) //serialicen como string
    private EstadoPedido pedidoEstado;

    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updatedAt;

    @Transient
    public BigDecimal calcularMontoTotal() {
        return items != null
                ? items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
    }

}
