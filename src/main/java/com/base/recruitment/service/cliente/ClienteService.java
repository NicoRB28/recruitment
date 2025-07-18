package com.base.recruitment.service.cliente;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.EditarClienteRequest;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;

import java.util.List;

public interface ClienteService {
    void registrar(RegistrarClientRequest request);
    ClientDto update(long id, EditarClienteRequest request);
    void delete(long id);
    List<ClientDto> getAll();
    List<ClientDto> getClientesWithProductoBancario(String producto);
    ClientDto getById(Long id);
}
