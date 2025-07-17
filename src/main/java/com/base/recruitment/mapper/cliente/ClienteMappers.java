package com.base.recruitment.mapper.cliente;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
import com.base.recruitment.model.ClienteEntity;
import com.base.recruitment.model.DireccionEntity;
import com.base.recruitment.model.ProductoEntity;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ClienteMappers {

    private static final Predicate<Object> hasValue = Objects::nonNull;

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

    public static ClientDto fromEntityToClientDto(ClienteEntity entity) {
        return new ClientDto(
                entity.getId(),
                entity.getDni(),
                entity.getNombre(),
                entity.getApellido(),
                extractOrDefault(entity::getDireccion, () -> entity.getDireccion().getCalle(), null),
                extractOrDefault(entity::getDireccion, () -> entity.getDireccion().getNumero(), null),
                extractOrDefault(entity::getDireccion, () -> entity.getDireccion().getCodigoPostal(), null),
                entity.getTelefono(),
                entity.getCelular(),
                entity.getProductos()
                        .stream()
                        .map(ProductoEntity::getNombre)
                        .toList());
    }

    static  <T, R>  R extractOrDefault(Supplier<T> firstExtractor, Supplier<R> secondExtractor, R defaultValue) {
        return hasValue.test(firstExtractor.get()) ? secondExtractor.get() : defaultValue;
    }
}
