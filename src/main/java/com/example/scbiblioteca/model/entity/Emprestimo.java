package com.example.scbiblioteca.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataEmprestimo;

    @ManyToOne
    private Leitor leitor;

    @ManyToOne
    private Funcionario funcionario;

    @ManyToOne
    private Exemplar exemplar;

    @OneToOne(cascade = CascadeType.ALL)
    private Devolucao devolucao;
}
