package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.api.dto.LeitorDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.service.LeitorService;
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
@RequestMapping("/api/v1/leitores")
@RequiredArgsConstructor

public class LeitorController{

    private final LeitorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Leitor> leitores = service.getLeitor();
        return ResponseEntity.ok(leitores.stream().map(LeitorDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Leitor> leitor = service.getLeitorById(id);
        if (!leitor.isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(leitor.map(LeitorDTO::create));
    }

}