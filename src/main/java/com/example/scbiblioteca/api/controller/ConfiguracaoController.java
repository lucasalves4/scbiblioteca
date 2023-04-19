package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.service.ConfiguracaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/configuracoes")
@RequiredArgsConstructor

public class ConfiguracaoController{

    private final ConfiguracaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Configuracao> configuracoes = service.getConfiguracao();
        return ResponseEntity.ok(configuracoes.stream().map(ConfiguracaoDTO::create).collect(Collectors.toList()));
    }

}
