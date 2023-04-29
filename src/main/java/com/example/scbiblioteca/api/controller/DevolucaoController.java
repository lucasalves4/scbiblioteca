package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DevolucaoDTO;
import com.example.scbiblioteca.model.entity.Devolucao;
import com.example.scbiblioteca.service.DevolucaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/devolucoes")
@RequiredArgsConstructor

public class DevolucaoController {

    private final DevolucaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Devolucao> devolucoes = service.getDevolucao();
        return ResponseEntity.ok(devolucoes.stream().map(DevolucaoDTO::create).collect(Collectors.toList()));
    }

}