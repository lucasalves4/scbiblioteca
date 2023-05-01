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

    public static DocumentoDTO create(Documento documento) {
        ModelMapper modelMapper = new ModelMapper();
        DocumentoDTO dto = modelMapper.map(documento, DocumentoDTO.class);
        dto.tipoDocumento = documento.getTipoDocumento();
        return dto;
    }
}
