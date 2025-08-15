# Microservicio Pedido

## Descripción General

El microservicio Pedido se encarga de gestionar pedidos, provee un API encargado de las operaciones CRUD sobre la entidad. Está desarrollado con Spring Boot y WebFlux, ofreciendo una API RESTful completamente reactiva y no bloqueante. La persistencia se implementa utilizando MongoDB como base de datos NoSQL para garantizar escalabilidad y flexibilidad. Su arquitectura basada en flujos reactivos permite una gestión eficiente de recursos, incluso en alta demanda, mediante operadores como onBackpressureBuffer y delayElements. Las validaciones se realizan con jakarta.validation y un Validator explícito para asegurar la integridad de los datos. Se incorporan pruebas automatizadas con JUnit 5 y StepVerifier, además de una simulación de cliente lento para evaluar la resiliencia ante escenarios de sobrecarga.
---
## Funcionalidades

1. **Gestión de Pedidos**
    - Definen el userID, pagoEstado y pedidoEstado.
    - Definen el productoId, nombre, cantidad y precio. 
    - Definen calle, ciudad, codigoPostal y pais.
  
## API Endpoints

### Campaigns API
- **Base Path:** `/pedido`

| Method  | Endpoint        | Description                |
|---------|-----------------|----------------------------|
| POST    | `/pedido`       | Crear un pedido.           |
| GET     | `/pedido/{id}`  | Obtener un pedido por ID.  |
| GET     | `/pedido`       | Listar pedidos.            |
| DELETE  | `/pedido/{id}`  | Eliminar pedido por ID.    |

## How to Run

1. Clonar el repository:
   ```bash
   git clone https://github.com/liusvani/poc-spring-webflux-mongodb-stepverifier.git
   ```

2. Navegar hasta el directorio del proyecto:
   ```bash
   cd poc-spring-webflux-mongodb-stepverifier
   ```

3. Construye el proyecto:
   ```bash
   ./mvnw clean install
   ```

4. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

5. El servicio estará disponible en la dirección `http://localhost:8080`.

---

## Ejemplo Payloads

### Pedido Payloads
#### Crear Pedido
```json
{        
    "userId": 1,    
    "pagoEstado": "PAGADO",
    "pedidoEstado": "ENVIADO",
    "items": [
        {
            "productoId": 101,
            "nombre": "Champu",
            "cantidad": "4",
            "precio": 2.5
        },
        {
            "productoId": 202,
            "nombre": "Jabón de tocador",
            "cantidad": 4,
            "precio": 5
        }
    ],
    "direccion": {
        "calle": "Calle 23",
        "ciudad": "La Habana",
        "pais": "Cuba",
        "codigoPostal": "10400"
    }
}
```



