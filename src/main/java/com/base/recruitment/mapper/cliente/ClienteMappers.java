package com.base.recruitment.mapper.cliente;

import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
import com.base.recruitment.model.ClienteEntity;
import com.base.recruitment.model.DireccionEntity;

public class ClienteMappers {

    public static  ClienteEntity fromRegistrarClienteRequestToClienteEntity(RegistrarClientRequest request) {
        return ClienteEntity.builder()
                .dni(request.dni())
                .nombre(request.nombre())
                .apellido(request.apellido())
                .direccion(DireccionEntity.builder()
                                .calle(request.calle())
                                .numero(request.numero())
                                .build())
                .telefono(request.telefono())
                .celular(request.celular())
                .build();
    }
}
