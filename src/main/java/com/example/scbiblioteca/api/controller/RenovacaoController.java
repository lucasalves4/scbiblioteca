package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.RenovacaoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.model.entity.Renovacao;
import com.example.scbiblioteca.service.RenovacaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/renovacoes")
@RequiredArgsConstructor

public class RenovacaoController{

    private final RenovacaoService service;
    private final EmprestimoService emprestimoService;


    @GetMapping()
    public ResponseEntity get() {
        List<Renovacao> renovacoes = service.getRenovacao();
        return ResponseEntity.ok(renovacoes.stream().map(RenovacaoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Renovacao> renovacao = service.getRenovacaoById(id);
        if (!renovacao.isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(renovacao.map(RenovacaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(RenovacaoDTO dto) {
        try {
            Renovacao renovacao = converter(dto);
            renovacao = service.salvar(renovacao);
            return new ResponseEntity(renovacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, RenovacaoDTO dto) {
        if (!service.getRenovacaoById(id).isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Renovacao renovacao = converter(dto);
            renovacao.setId(id);
            service.salvar(renovacao);
            return ResponseEntity.ok(renovacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Renovacao> renovacao = service.getRenovacaoById(id);
        if (!renovacao.isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(renovacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Renovacao converter(RenovacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Renovacao renovacao = modelMapper.map(dto, Renovacao.class);
        if (dto.getIdEmprestimo() != null) {
            Optional<Emprestimo> emprestimo = emprestimoService.getEmprestimoById(dto.getIdEmprestimo());
            if (!emprestimo.isPresent()) {
                renovacao.setEmprestimo(null);
            } else {
                renovacao.setEmprestimo(emprestimo.get());
            }
        }
        return renovacao;
    }

}