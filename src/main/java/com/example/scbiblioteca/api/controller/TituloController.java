package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DocumentoDTO;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.model.entity.Titulo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/titulos")
@RequiredArgsConstructor

public class TituloController{

    private final TituloService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Titulo> titulos = service.getTitulo();
        return ResponseEntity.ok(titulos.stream().map(TituloDTO::create).collect(Collectors.toList()));
    }

}