package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.EnderecoDTO;
import com.example.scbiblioteca.model.entity.Endereco;
import com.example.scbiblioteca.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/enderecos")
@RequiredArgsConstructor

public class EnderecoController{

    private final EnderecoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Endereco> enderecos = service.getEndereco();
        return ResponseEntity.ok(enderecos.stream().map(EnderecoDTO::create).collect(Collectors.toList()));
    }

}