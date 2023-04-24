package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.RenovacaoDTO;
import com.example.scbiblioteca.model.entity.Renovacao;
import com.example.scbiblioteca.service.RenovacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/renovacoes")
@RequiredArgsConstructor

public class RenovacaoController{

    private final RenovacaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Renovacao> renovacoes = service.getRenovacao();
        return ResponseEntity.ok(renovacoes.stream().map(RenovacaoDTO::create).collect(Collectors.toList()));
    }

}