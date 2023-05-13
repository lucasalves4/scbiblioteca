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
public class Reserva{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataReserva;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Leitor leitor;
    @ManyToOne
    private Exemplar exemplar;

}



