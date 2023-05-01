package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.EmprestimoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.*;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.service.ExemplarService;
import com.example.scbiblioteca.service.FuncionarioService;
import com.example.scbiblioteca.service.LeitorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor

public class EmprestimoController{

    private final EmprestimoService service;
    private final LeitorService leitorService;
    private final FuncionarioService funcionarioService;
    private final ExemplarService exemplarService;

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

    @PostMapping()
    public ResponseEntity post(@RequestBody EmprestimoDTO dto) {
        try {
            Emprestimo emprestimo = converter(dto);
            emprestimo = service.salvar(emprestimo);
            return new ResponseEntity(emprestimo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EmprestimoDTO dto) {
        if (!service.getEmprestimoById(id).isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Emprestimo emprestimo = converter(dto);
            emprestimo.setId(id);
            service.salvar(emprestimo);
            return ResponseEntity.ok(emprestimo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(emprestimo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Emprestimo converter(EmprestimoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Emprestimo emprestimo = modelMapper.map(dto, Emprestimo.class);
        if (dto.getIdLeitor() != null) {
            Optional<Leitor> leitor = leitorService.getLeitorById(dto.getIdLeitor());
            if (!leitor.isPresent()) {
                emprestimo.setLeitor(null);
            } else {
                emprestimo.setLeitor(leitor.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                emprestimo.setFuncionario(null);
            } else {
                emprestimo.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdExemplar() != null) {
            Optional<Exemplar> exemplar = exemplarService.getExemplarById(dto.getIdExemplar());
            if (!exemplar.isPresent()) {
                emprestimo.setExemplar(null);
            } else {
                emprestimo.setExemplar(exemplar.get());
            }
        }
        return emprestimo;
    }

}