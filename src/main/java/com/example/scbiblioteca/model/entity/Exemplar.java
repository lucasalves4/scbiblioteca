package com.example.scbiblioteca.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int numeroTombo;
    private Date dataAquisicao;
    private String tipoAquisicao;
    private float valor;
    @ManyToOne
    private Titulo titulo;
}


