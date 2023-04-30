package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Configuracao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoDTO {

    private Long id;

    private String prazoEntregaQuantDias;

    private float valorMulta;

    private boolean permiteRenovar;

    private int quantMaximaEmprestimo;

    private boolean permiteReserva;

    private Long idDocumento;

    public static ConfiguracaoDTO create(Configuracao configuracao) {
        ModelMapper modelMapper = new ModelMapper();
        ConfiguracaoDTO dto = modelMapper.map(configuracao, ConfiguracaoDTO.class);
        dto.prazoEntregaQuantDias = configuracao.getPrazoEntregaQuantDias();
        dto.valorMulta = configuracao.getValorMulta();
        if (configuracao.isPermiteRenovar()) dto.permiteRenovar = true;
        else dto.permiteRenovar = false;
        dto.quantMaximaEmprestimo = configuracao.getQuantMaximaEmprestimo();
        if (configuracao.isPermiteReserva()) dto.permiteReserva = true;
        else dto.permiteReserva = false;
        return dto;
    }
}