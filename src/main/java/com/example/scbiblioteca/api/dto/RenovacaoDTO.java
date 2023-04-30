package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Renovacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RenovacaoDTO {

    private Long id;
    private int quantidadeDias;
    private String dataRenovacao;
    private Long idEmprestimo;

    public static RenovacaoDTO create(Renovacao renovacao) {
        ModelMapper modelMapper = new ModelMapper();
        RenovacaoDTO dto = modelMapper.map(renovacao, RenovacaoDTO.class);
        dto.dataRenovacao = renovacao.getDataRenovacao();
        dto.quantidadeDias = renovacao.getQuantidadeDias();
        return dto;
    }
}
