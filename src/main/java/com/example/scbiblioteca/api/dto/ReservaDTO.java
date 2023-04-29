package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Reserva;
import org.modelmapper.ModelMapper;

public class ReservaDTO {
    private Long id;

    public static ReservaDTO create(Reserva reserva) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(reserva, ReservaDTO.class);
    }
}
