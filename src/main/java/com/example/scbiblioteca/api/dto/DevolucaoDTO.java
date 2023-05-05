package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Devolucao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucaoDTO {
    private Long id;
    private Date dataDevolucao;

    private Long idEmprestimo;

    public static DevolucaoDTO create(Devolucao devolucao) {
        ModelMapper modelMapper = new ModelMapper();
        DevolucaoDTO dto = modelMapper.map(devolucao, DevolucaoDTO.class);
        dto.dataDevolucao = devolucao.getDataDevolucao();
        return dto;
    }
}
