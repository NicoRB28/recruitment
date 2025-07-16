package com.base.recruitment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "producto")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToMany(mappedBy = "productos")
    private Set<ClienteEntity> clientes;
}
