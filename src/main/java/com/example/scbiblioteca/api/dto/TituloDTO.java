package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Titulo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TituloDTO {
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
    private Long idDocumento;
    private Long idAutor;

    public static TituloDTO create(Titulo titulo) {
        ModelMapper modelMapper = new ModelMapper();
        TituloDTO dto = modelMapper.map(titulo, TituloDTO.class);
        dto.codClassificacao = titulo.getCodClassificacao();
        dto.titulo = titulo.getTitulo();
        dto.subtitulo = titulo.getSubtitulo();
        dto.edicao = titulo.getEdicao();
        dto.area = titulo.getArea();
        dto.totalPaginas = titulo.getTotalPaginas();
        dto.notaSerie = titulo.getNotaSerie();
        dto.cidadePublicacao = titulo.getCidadePublicacao();
        dto.editora = titulo.getEditora();
        dto.dataPublicacao = titulo.getDataPublicacao();
        dto.idioma = titulo.getIdioma();
        return dto;
    }
}
