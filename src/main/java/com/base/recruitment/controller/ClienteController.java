 package com.base.recruitment.controller;

 import com.base.recruitment.dto.cliente.ClientDto;
 import com.base.recruitment.dto.cliente.EditarClienteRequest;
 import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
 import com.base.recruitment.service.cliente.ClienteService;
 import lombok.RequiredArgsConstructor;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.*;

 import java.util.List;

@Controller
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping("/")
    public ResponseEntity<Void> register(@RequestBody RegistrarClientRequest request) {
        this.clienteService.registrar(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ClientDto>> getClientes() {
        List<ClientDto> clientes =  this.clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable long id, @RequestBody EditarClienteRequest request) {
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
}
