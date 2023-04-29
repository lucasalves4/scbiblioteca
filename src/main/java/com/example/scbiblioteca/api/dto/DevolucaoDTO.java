package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Devolucao;
import org.modelmapper.ModelMapper;

public class DevolucaoDTO {
    private Long id;
    private String dataDevolucao;

    public static DevolucaoDTO create(Devolucao devolucao) {
        ModelMapper modelMapper = new ModelMapper();
        DevolucaoDTO dto = modelMapper.map(devolucao, DevolucaoDTO.class);
        dto.dataDevolucao = devolucao.getDataDevolucao();
        return dto;
    }
}
