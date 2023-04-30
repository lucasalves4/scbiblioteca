package com.example.scbiblioteca.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Leitor leitor;

    @ManyToOne(cascade = CascadeType.ALL)
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.ALL)
    private Exemplar exemplar;
}
