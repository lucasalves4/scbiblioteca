package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.api.dto.DevolucaoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Devolucao;
import com.example.scbiblioteca.service.DevolucaoService;
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
@RequestMapping("/api/v1/devolucoes")
@RequiredArgsConstructor

public class DevolucaoController {

    private final DevolucaoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Devolucao> devolucoes = service.getDevolucao();
        return ResponseEntity.ok(devolucoes.stream().map(DevolucaoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Devolucao> devolucao = service.getDevolucaoById(id);
        if (!devolucao.isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(devolucao.map(DevolucaoDTO::create));
    }

}