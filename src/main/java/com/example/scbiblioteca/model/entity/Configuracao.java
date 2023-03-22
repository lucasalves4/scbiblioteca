package com.example.scbiblioteca.model.entity;

import jakarta.persistence.*;
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
    private Integer id;

    private String tipoPrazo;

    private String prazoEntregaQuantDias;

    private float valorMulta;

    private boolean permiteRenovar;

    private int quantMaximaEmprestimo;

    private boolean permiteReserva;

    @OneToOne(cascade = CascadeType.ALL)
    private Documento documento;
}

