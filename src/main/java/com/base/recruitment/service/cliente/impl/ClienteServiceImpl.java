package com.base.recruitment.service.cliente.impl;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
import com.base.recruitment.mapper.cliente.ClienteMappers;
import com.base.recruitment.model.ClienteEntity;
import com.base.recruitment.model.ProductoEntity;
import com.base.recruitment.repository.ClienteRepository;
import com.base.recruitment.repository.ProductoRepository;
import com.base.recruitment.service.cliente.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private static final Predicate<RegistrarClientRequest> hasProducto = request ->
            Objects.nonNull(request.productoBancario()) && !request.productoBancario().isEmpty();
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    @Override
    @Transactional
    public void registrar(RegistrarClientRequest request) {
        ClienteEntity clienteEntity = ClienteMappers.fromRegistrarClienteRequestToClienteEntity(request);
        Set<ProductoEntity> productos = Set.of();
        if (hasProducto.test(request)) {
            productos = productoRepository.findByNombreIn(request.productoBancario());
        }
        if (!productos.isEmpty()) {
            clienteEntity.setProductos(productos);
        }
        this.clienteRepository.save(clienteEntity);
    }

    @Override
    public List<ClientDto> getAll() {
        return this.clienteRepository.findAll()
                .stream()
                .map(entity -> new ClientDto(
                        entity.getDni(),
                        entity.getNombre(),
                        entity.getApellido(),
                        entity.getDireccion().getCalle(),
                        entity.getDireccion().getNumero(),
                        entity.getDireccion().getCodigoPostal(),
                        entity.getTelefono(),
                        entity.getCelular(),
                        entity.getProductos()
                                .stream()
                                .map(ProductoEntity::getNombre)
                                .toList()))
                .toList();
    }
}
