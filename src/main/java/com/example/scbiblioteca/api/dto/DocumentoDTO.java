package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Documento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {
    private Long id;

    private String tipoDocumento;

    private int prazoEntregaQuantDias;

    private float valorMulta;

    private boolean permiteRenovar;

    private int quantMaximaEmprestimo;

    private boolean permiteReserva;

    public static DocumentoDTO create(Documento documento) {
        ModelMapper modelMapper = new ModelMapper();
        DocumentoDTO dto = modelMapper.map(documento, DocumentoDTO.class);
        dto.tipoDocumento = documento.getTipoDocumento();
        dto.prazoEntregaQuantDias = documento.getConfiguracao().getPrazoEntregaQuantDias();
        dto.valorMulta = documento.getConfiguracao().getValorMulta();
        if (documento.getConfiguracao().isPermiteRenovar()) dto.permiteRenovar = true;
        else dto.permiteRenovar = false;
        if (documento.getConfiguracao().isPermiteReserva()) dto.permiteReserva = true;
        else dto.permiteReserva = false;
        dto.quantMaximaEmprestimo = documento.getConfiguracao().getQuantMaximaEmprestimo();
        return dto;
    }
}
