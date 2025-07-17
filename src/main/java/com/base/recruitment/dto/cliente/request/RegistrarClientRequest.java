package com.base.recruitment.dto.cliente.request;

import java.util.List;

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
