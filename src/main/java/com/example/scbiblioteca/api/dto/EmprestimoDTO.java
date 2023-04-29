package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.*;
import org.modelmapper.ModelMapper;

public class EmprestimoDTO {
    private Long id;
    private Long idLeitor;
    private Long idFuncionario;
    private Long idExemplar;

    public static EmprestimoDTO create(Emprestimo emprestimo) {
        ModelMapper modelMapper = new ModelMapper();
        EmprestimoDTO dto = modelMapper.map(emprestimo, EmprestimoDTO.class);
        return dto;
    }
}
