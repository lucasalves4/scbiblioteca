package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ReservaDTO;
import com.example.scbiblioteca.model.entity.Reserva;
import com.example.scbiblioteca.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor

public class ReservaController{

    private final ReservaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReserva();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

}