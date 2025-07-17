package com.base.recruitment.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(name = "dni_unique", columnNames = {"dni"})})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteEntity {
    @Id
    private Long dni;
    private String nombre;
    private String apellido;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private DireccionEntity direccion;
    private String telefono;
    private String celular;
    @ManyToMany
    @JoinTable(
            name = "cliente_producto",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<ProductoEntity> productos;
}
