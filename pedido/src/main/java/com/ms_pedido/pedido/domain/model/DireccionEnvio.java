package com.ms_pedido.pedido.domain.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEnvio {

    @NotNull
    @Size(min = 1, max = 100)
    private String calle;

    @NotNull
    @Size(min = 1, max = 50)
    private String ciudad;

    @NotNull
    @Size(min = 1, max = 20)
    private String codigoPostal;

    @NotNull
    @Size(min = 1, max = 50)
    private String pais;

}
