package com.example.scbiblioteca.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Configuracao{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int prazoEntregaQuantDias;

    private float valorMulta;

    private boolean permiteRenovar;

    private int quantMaximaEmprestimo;

    private boolean permiteReserva;

}

