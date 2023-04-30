package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.api.dto.TituloDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Titulo;
import com.example.scbiblioteca.service.TituloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Titulo> titulo = service.getTituloById(id);
        if (!titulo.isPresent()) {
            return new ResponseEntity("Titulo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(titulo.map(TituloDTO::create));
    }
}