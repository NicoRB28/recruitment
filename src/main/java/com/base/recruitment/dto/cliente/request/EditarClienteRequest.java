package com.base.recruitment.dto.cliente.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditarClienteRequest(
        @NotNull(message = "El campo dni es requerido")
        Long dni,
        @NotNull(message = "El campo nombre es requerido.")
        String nombre,
        @NotNull(message = "El campo apellido es requerido.")
        String apellido,
        String calle,
        Long numero,
        Long codigoPostal,
        String telefono,
        String celular,
        List<String> productoBancario
) {

}
