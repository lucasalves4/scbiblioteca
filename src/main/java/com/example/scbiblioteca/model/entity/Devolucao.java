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
public class Devolucao{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String dataDevolucao;

    @ManyToOne(cascade = CascadeType.ALL)
    private Emprestimo emprestimo;
}


