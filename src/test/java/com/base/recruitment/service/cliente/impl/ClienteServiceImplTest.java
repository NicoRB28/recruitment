package com.base.recruitment.service.cliente.impl;

import com.base.recruitment.dto.cliente.ClientDto;
import com.base.recruitment.dto.cliente.EditarClienteRequest;
import com.base.recruitment.dto.cliente.request.RegistrarClientRequest;
import com.base.recruitment.exception.client.ClientServiceException;
import com.base.recruitment.model.ClienteEntity;
import com.base.recruitment.model.DireccionEntity;
import com.base.recruitment.model.ProductoEntity;
import com.base.recruitment.repository.ClienteRepository;
import com.base.recruitment.repository.DireccionRepository;
import com.base.recruitment.repository.ProductoRepository;
import com.base.recruitment.service.cliente.ClienteService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    ClienteRepository clienteRepository;
    @Mock
    ProductoRepository productoRepository;

    ClienteService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new ClienteServiceImpl(clienteRepository, productoRepository);
    }

    @Test
    void registrarWithoutProductSuccess() {
        RegistrarClientRequest request = registrarClienteRequest();
        ClienteEntity cliente = getCliente();

        when(clienteRepository.exists(any(Example.class))).thenReturn(false);
        when(clienteRepository.save(any())).thenReturn(cliente);

        underTest.registrar(request);

        verify(productoRepository, never()).findByNombreIn(any());
        verify(clienteRepository, atMostOnce()).save(cliente);
    }

    @Test
    void registrarWithProductSuccess() {
        RegistrarClientRequest request = registrarClienteRequestWithProducto("CHEQ");
        ClienteEntity cliente = getClienteWithProducto();
        Set<ProductoEntity> producto = Set.of(ProductoEntity.builder().nombre("CHEQ").build());
        when(clienteRepository.exists(any(Example.class))).thenReturn(false);
        when(productoRepository.findByNombreIn(List.of("CHEQ")))
                .thenReturn(producto);

        when(clienteRepository.save(any())).thenReturn(cliente);

        underTest.registrar(request);

        verify(productoRepository, atMostOnce()).findByNombreIn(request.productoBancario());
        verify(clienteRepository, atMostOnce()).save(cliente);
    }

    @Test
    void registrarWithProductFailProductoNoExiste() {
        RegistrarClientRequest request = registrarClienteRequestWithProducto("PSADA");

        when(clienteRepository.exists(any(Example.class))).thenReturn(false);
        when(productoRepository.findByNombreIn(List.of("PSADA"))).thenReturn(Set.of());

        ClientServiceException actual = assertThrows(ClientServiceException.class, () -> underTest.registrar(request));

        verify(productoRepository, atMostOnce()).findByNombreIn(request.productoBancario());
        verify(clienteRepository, never()).save(any());
        assertThat(actual)
                .isInstanceOf(ClientServiceException.class)
                .hasMessage("Error en los productos ingresados.");
    }


    @Test
    void registrarWithoutProductFailWithClienteYaRegistrado() {
        RegistrarClientRequest request = registrarClienteRequest();
        ClienteEntity cliente = getCliente();

        when(clienteRepository.exists(any(Example.class))).thenReturn(true);
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClientServiceException actual = assertThrows(ClientServiceException.class,
                () -> underTest.registrar(request));

        verify(productoRepository, never()).findByNombreIn(any());
        verify(clienteRepository, never()).save(cliente);
        assertThat(actual)
                .isInstanceOf(ClientServiceException.class)
                .hasMessage("Cliente ya registrado.");
    }

    @Test
    void updateSuccess() {
        long id = 1L;
        EditarClienteRequest request = updateRequest();
        ClienteEntity dbEntity = getCliente();
        ClienteEntity expected = getExpectedAfterEdit();
        ProductoEntity dbProduct = new ProductoEntity(id, "CHEQ", Set.of());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(dbEntity));
        when(productoRepository.findByNombreIn(List.of("CHEQ"))).thenReturn(Set.of(dbProduct));
        when(clienteRepository.save(dbEntity)).thenReturn(expected);

        ClientDto actual = underTest.update(1L, request);

        assertThat(actual)
                .extracting(
                        ClientDto::dni,
                        ClientDto::nombre,
                        ClientDto::apellido,
                        ClientDto::calle,
                        ClientDto::numero,
                        ClientDto::codigoPostal,
                        ClientDto::telefono,
                        ClientDto::celular,
                        ClientDto::productoBancario)
                .containsAll(
                        List.of(
                                31123444L,
                                "Pablo Editado",
                                "Apellido Editado",
                                "Calle Editada",
                                321L,
                                222L,
                                "221 4433483",
                                "221 4643212",
                                List.of("CHEQ")
                        ));
    }

    @Test
    void deleteSuccess() {
        ClienteEntity cliente = getCliente();
        when(this.clienteRepository.getReferenceById(1L))
                .thenReturn(cliente);

        underTest.delete(1L);

        verify(clienteRepository, atMostOnce()).delete(cliente);
    }

    @Test
    void deleteFailNotFound() {
        when(this.clienteRepository.getReferenceById(1L))
                .thenThrow(EntityNotFoundException.class);

        ClientServiceException actual = assertThrows(ClientServiceException.class,
                () -> underTest.delete(1L));

        verify(clienteRepository, never()).delete(any());
        assertThat(actual)
                .isInstanceOf(ClientServiceException.class)
                .hasMessage("El cliente no se encuentra registrado");
    }

    @Test
    void getAll() {
        List<ClienteEntity> db = List.of(
                getCliente(), getClienteWithProducto(), getExpectedAfterEdit());

        when(clienteRepository.findAll())
                .thenReturn(db);

        List<ClientDto> actual = underTest.getAll();

        assertThat(actual)
                .isNotEmpty()
                .hasSize(3);
    }

    @Test
    void getClientesWithProductoBancario() {
        Set<ClienteEntity> db = Set.of(
                getClienteWithProducto()
        );

        when(clienteRepository.findAllWithProduct("CHEQ"))
                .thenReturn(db);

        List<ClientDto> actual = underTest.getClientesWithProductoBancario("CHEQ");

        assertThat(actual)
                .isNotEmpty()
                .hasSize(1);
    }

    private ClienteEntity getCliente() {
        return ClienteEntity.builder()
                .id(1L)
                .dni(30000123L)
                .nombre("Ricardo")
                .apellido("Perez")
                .celular("11 897123")
                .telefono(null)
                .direccion(DireccionEntity.builder()
                        .calle("Las Heras")
                        .numero(123L)
                        .codigoPostal(2384L)
                        .build())
                .productos(Set.of())
                .build();
    }

    private ClienteEntity getClienteWithProducto() {
        return ClienteEntity.builder()
                .id(1L)
                .dni(30000123L)
                .nombre("Ricardo")
                .apellido("Perez")
                .celular("11 897123")
                .telefono(null)
                .direccion(DireccionEntity.builder()
                        .calle("Las Heras")
                        .numero(123L)
                        .codigoPostal(2384L)
                        .build())
                .productos(Stream.of("CHEQ")
                        .map(str -> ProductoEntity.builder()
                        .nombre(str)
                        .build())
                        .collect(Collectors.toSet()))
                .build();
    }

    private RegistrarClientRequest registrarClienteRequest() {
        return RegistrarClientRequest.builder()
                .dni(30000123L)
                .nombre("Ricardo")
                .apellido("Perez")
                .celular("11 897123")
                .telefono(null)
                .calle("Las Heras")
                .numero(123L)
                .codigoPostal(2384L)
                .build();
    }


    private RegistrarClientRequest registrarClienteRequestWithProducto(String producto) {
        return RegistrarClientRequest.builder()
                .dni(30000123L)
                .nombre("Ricardo")
                .apellido("Perez")
                .celular("11 897123")
                .telefono(null)
                .calle("Las Heras")
                .numero(123L)
                .codigoPostal(2384L)
                .productoBancario(List.of(producto))
                .build();
    }

    private EditarClienteRequest updateRequest() {
        return new EditarClienteRequest(
                31123444,
                "Pablo Editado",
                "Apellido Editado",
                "Calle Editada",
                321L,
                222L,
                "221 4433483",
                "221 4643212",
                List.of("CHEQ")
        );
    }

    private ClienteEntity getExpectedAfterEdit() {
        return new ClienteEntity(
                1L,
                31123444L,
                "Pablo Editado",
                "Apellido Editado",
                DireccionEntity.builder()
                        .calle("Calle Editada")
                        .numero(321L)
                        .codigoPostal(222L)
                        .build(),
                "221 4433483",
                "221 4643212",
                Set.of(ProductoEntity.builder()
                        .nombre("CHEQ")
                        .build())
        );
    }

}