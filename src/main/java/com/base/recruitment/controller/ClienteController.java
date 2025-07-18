 package com.base.recruitment.controller;

 import com.base.recruitment.dto.cliente.ClientDto;
 import com.base.recruitment.dto.cliente.request.EditarClienteRequest;
 import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
 import com.base.recruitment.service.cliente.ClienteService;
 import jakarta.validation.Valid;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;

 import java.util.List;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping("/")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrarClientRequest request) {
        this.clienteService.registrar(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientDto>> getClientes() {
        List<ClientDto> clientes =  this.clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable long id, @RequestBody @Valid EditarClienteRequest request) {
        ClientDto update = this.clienteService.update(id, request);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        this.clienteService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{producto}")
    public ResponseEntity<List<ClientDto>> findByProducto(@PathVariable String producto) {
        List<ClientDto> clientes = this.clienteService.getClientesWithProductoBancario(producto);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ClientDto> findById(@PathVariable Long id) {
        ClientDto client = this.clienteService.getById(id);
        return ResponseEntity.ok(client);
    }
}
