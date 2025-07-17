package com.base.recruitment.dto.cliente.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RegistrarClientRequest(
        Long dni,
        String nombre,
        String apellido,
        String calle,
        Long numero,
        Long codigoPostal,
        String telefono,
        String celular,
        List<String> productoBancario
) { }
