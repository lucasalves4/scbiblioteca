package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DevolucaoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Devolucao;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.service.DevolucaoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/devolucoes")
@RequiredArgsConstructor

public class DevolucaoController {

    private final DevolucaoService service;
    private final EmprestimoService emprestimoService;

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

    @PostMapping()
    public ResponseEntity post(@RequestBody DevolucaoDTO dto) {
        try {
            Devolucao devolucao = converter(dto);
            devolucao = service.salvar(devolucao);
            return new ResponseEntity(devolucao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DevolucaoDTO dto) {
        if (!service.getDevolucaoById(id).isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Devolucao devolucao = converter(dto);
            devolucao.setId(id);
            service.salvar(devolucao);
            return ResponseEntity.ok(devolucao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Devolucao> devolucao = service.getDevolucaoById(id);
        if (!devolucao.isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(devolucao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Devolucao converter(DevolucaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Devolucao devolucao = modelMapper.map(dto, Devolucao.class);
        if (dto.getIdEmprestimo() != null) {
            Optional<Emprestimo> emprestimo = emprestimoService.getEmprestimoById(dto.getIdEmprestimo());
            if (!emprestimo.isPresent()) {
                devolucao.setEmprestimo(null);
            } else {
                devolucao.setEmprestimo(emprestimo.get());
            }
        }
        return devolucao;
    }

}