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
public class Titulo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String dataPublicacao;
    private String idioma;
    @OneToOne(cascade = CascadeType.ALL)
    private Documento documento;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

}


