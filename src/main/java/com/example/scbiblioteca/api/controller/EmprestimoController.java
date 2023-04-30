package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.api.dto.EmprestimoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.service.EmprestimoService;
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
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor

public class EmprestimoController{

    private final EmprestimoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Emprestimo> emprestimos = service.getEmprestimo();
        return ResponseEntity.ok(emprestimos.stream().map(EmprestimoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(emprestimo.map(EmprestimoDTO::create));
    }

}