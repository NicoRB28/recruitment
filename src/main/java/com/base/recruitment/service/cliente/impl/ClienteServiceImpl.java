package com.base.recruitment.service.cliente.impl;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.EditarClienteRequest;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
import com.base.recruitment.exception.client.ClientServiceException;
import com.base.recruitment.mapper.cliente.ClienteMappers;
import com.base.recruitment.model.ClienteEntity;
import com.base.recruitment.model.DireccionEntity;
import com.base.recruitment.model.ProductoEntity;
import com.base.recruitment.repository.ClienteRepository;
import com.base.recruitment.repository.DireccionRepository;
import com.base.recruitment.repository.ProductoRepository;
import com.base.recruitment.service.cliente.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        checkIfExists(() -> ClienteEntity.builder().dni(request.dni()).build());
        ClienteEntity clienteEntity = ClienteMappers.fromRegistrarClienteRequestToClienteEntity(request);
        handleProducto(request, clienteEntity);
        this.clienteRepository.save(clienteEntity);
    }

    private void handleProducto(RegistrarClientRequest request, ClienteEntity clienteEntity) {
        Set<ProductoEntity> productos = Set.of();
        if (hasProducto.test(request)) {
            productos = productoRepository.findByNombreIn(request.productoBancario());
            if (productos.isEmpty()) {
                throw new ClientServiceException("Error en los productos ingresados.", HttpStatus.BAD_REQUEST.value());
            }
        }
        clienteEntity.setProductos(productos);
    }

    @Override
    @Transactional
    public ClientDto update(long id, EditarClienteRequest request) {
        ClienteEntity clienteEntity = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClientServiceException("Cliente no registrado", HttpStatus.NOT_FOUND.value()));
        updateValues(request, clienteEntity);
        ClienteEntity result = this.clienteRepository.save(clienteEntity);
        return ClienteMappers.fromEntityToClientDto(result);
    }

    private void updateValues(EditarClienteRequest request, ClienteEntity clienteEntity) {
        clienteEntity.setDni(request.dni());
        clienteEntity.setNombre(request.nombre());
        clienteEntity.setApellido(request.apellido());
        DireccionEntity direccion = clienteEntity.getDireccion();
        direccion.setCalle(request.calle());
        direccion.setNumero(request.numero());
        direccion.setCodigoPostal(request.codigoPostal());
        clienteEntity.setDireccion(direccion);
        clienteEntity.setTelefono(request.telefono());
        clienteEntity.setCelular(request.celular());
        Set<ProductoEntity> productos = this.productoRepository.findByNombreIn(request.productoBancario());
        clienteEntity.setProductos(productos);
    }

    @Override
    @Transactional
    public void delete(long id) {
        try {
            ClienteEntity clientReference = this.clienteRepository.getReferenceById(id);
            this.clienteRepository.delete(clientReference);
        } catch (EntityNotFoundException e) {
            log.error("El cliente no se encuentra registrado");
            throw new ClientServiceException("El cliente no se encuentra registrado", HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    public List<ClientDto> getAll() {
        return this.clienteRepository.findAll()
                .stream()
                .map(ClienteMappers::fromEntityToClientDto)
                .toList();
    }

    @Override
    public List<ClientDto> getClientesWithProductoBancario(String producto) {
        return this.clienteRepository.findAllWithProduct(producto)
                .stream()
                .map(ClienteMappers::fromEntityToClientDto)
                .toList();
    }

    @Override
    public ClientDto getById(Long id) {
        ClienteEntity cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ClientServiceException("Cliente no registrado", HttpStatus.NOT_FOUND.value()));
        return ClienteMappers.fromEntityToClientDto(cliente);
    }

    private void checkIfExists(Supplier<ClienteEntity> clientSupplier) {
        Example<ClienteEntity> specification = Example.of(clientSupplier.get());
        boolean exists = clienteRepository.exists(specification);
        if(exists) {
            throw new ClientServiceException("Cliente ya registrado.", HttpStatus.CONFLICT.value());
        }
    }
}
