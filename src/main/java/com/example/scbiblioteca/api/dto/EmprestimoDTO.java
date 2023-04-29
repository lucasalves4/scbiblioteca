package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.*;
import org.modelmapper.ModelMapper;

public class EmprestimoDTO {
    private Long id;
    private Leitor leitor;
    private Funcionario funcionario;
    private Exemplar exemplar;

    public static EmprestimoDTO create(Emprestimo emprestimo) {
        ModelMapper modelMapper = new ModelMapper();
        EmprestimoDTO dto = modelMapper.map(emprestimo, EmprestimoDTO.class);
        dto.leitor = emprestimo.getLeitor();
        dto.funcionario = emprestimo.getFuncionario();
        dto.exemplar = emprestimo.getExemplar();
        return dto;
    }
}
