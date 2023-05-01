package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private Long idFuncionario;
    private Long idExemplar;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(reserva, ReservaDTO.class);
    }
}
