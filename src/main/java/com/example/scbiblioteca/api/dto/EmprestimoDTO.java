package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {
    private Long id;
    private Date dataEmprestimo;
    private Long idLeitor;
    private Long idFuncionario;
    private Long idExemplar;

    public static EmprestimoDTO create(Emprestimo emprestimo) {
        ModelMapper modelMapper = new ModelMapper();
        EmprestimoDTO dto = modelMapper.map(emprestimo, EmprestimoDTO.class);
        return dto;
    }
}
