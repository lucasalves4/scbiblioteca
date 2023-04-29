package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Funcionario;
import org.modelmapper.ModelMapper;

public class FuncionarioDTO {
    private Long id;

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(funcionario, FuncionarioDTO.class);
    }

}
