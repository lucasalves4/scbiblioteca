package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Autor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {

    private Long id;
    private String nome;

    public static AutorDTO create(Autor autor) {
        ModelMapper modelMapper = new ModelMapper();
        AutorDTO dto = modelMapper.map(autor, AutorDTO.class);
        dto.nome = autor.getNome();
        return dto;
    }
}
