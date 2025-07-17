package com.base.recruitment.service.cliente;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;

import java.util.List;

public interface ClienteService {
    void registrar(RegistrarClientRequest request);
    List<ClientDto> getAll();
}
