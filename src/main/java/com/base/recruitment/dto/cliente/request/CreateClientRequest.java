package com.base.recruitment.dto.cliente.request;

import java.util.List;

public record CreateClientRequest(
        String dni,
        String nombre,
        String calle,
        Long numero,
        Long codigoPostal,
        String telefono,
        String celular,
        List<String> productoBancario
) { }
