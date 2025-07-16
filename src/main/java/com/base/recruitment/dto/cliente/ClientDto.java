package com.base.recruitment.dto.cliente;

import java.util.List;

public record ClientDto(
        Long dni,
        String nombre,
        String apellido,
        String calle,
        Long numero,
        Long codigoPostal,
        String telefono,
        String celular,
        List<String> productoBancario
) {
}
