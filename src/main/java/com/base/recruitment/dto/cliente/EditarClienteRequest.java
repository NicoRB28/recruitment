package com.base.recruitment.dto.cliente;

import java.util.List;

public record EditarClienteRequest(
        long dni,
        String nombre,
        String apellido,
        String calle,
        long numero,
        long codigoPostal,
        String telefono,
        String celular,
        List<String> productoBancario
) {

}
