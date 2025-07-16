package com.base.recruitment.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table(name = "direccion")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DireccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private Long numero;
    @OneToOne(mappedBy = "direccion")
    private ClienteEntity cliente;
}
