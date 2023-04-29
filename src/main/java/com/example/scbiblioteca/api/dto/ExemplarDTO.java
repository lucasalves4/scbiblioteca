package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.model.entity.Exemplar;
import com.example.scbiblioteca.model.entity.Reserva;
import org.modelmapper.ModelMapper;

public class ExemplarDTO {
    private Long id;
    private int numeroTombo;
    private String dataAquisicao;
    private String tipoAquisicao;
    private float valor;
    private Emprestimo emprestimo;
    private Reserva reserva;

    public static ExemplarDTO create(Exemplar exemplar) {
        ModelMapper modelMapper = new ModelMapper();
        ExemplarDTO dto = modelMapper.map(exemplar, ExemplarDTO.class);
        dto.numeroTombo = exemplar.getNumeroTombo();
        dto.dataAquisicao = exemplar.getDataAquisicao();
        dto.tipoAquisicao = exemplar.getTipoAquisicao();
        dto.valor = exemplar.getValor();
        dto.emprestimo = exemplar.getEmprestimo();
        dto.reserva = exemplar.getReserva();
        return dto;
    }
}
