package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;
    private Date dataReserva;
    private Long idFuncionario;
    private Long idExemplar;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);
        dto.dataReserva = reserva.getDataReserva();
        return dto;
    }
}
