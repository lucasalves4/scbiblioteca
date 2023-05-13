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
public class Titulo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float codClassificacao;
    private String titulo;
    private String subtitulo;
    private  int edicao;
    private String area;
    private int totalPaginas;
    private String notaSerie;
    private String cidadePublicacao;
    private String editora;
    private Date dataPublicacao;
    private String idioma;
    @ManyToOne
    private Documento documento;
    @ManyToOne
    private Autor autor;

}


