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
public class Exemplar{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int numeroTombo;
    private String dataAquisicao;
    private String tipoAquisicao;
    private float valor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Emprestimo emprestimo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Reserva reserva;
}


