package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ExemplarDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.model.entity.Exemplar;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.service.ExemplarService;
import com.example.scbiblioteca.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/exemplares")
@RequiredArgsConstructor

public class ExemplarController{

    private final ExemplarService service;
    private final EmprestimoService emprestimoService;
    private final ReservaService reservaService;

    @GetMapping()
    public ResponseEntity get() {
        List<Exemplar> exemplares = service.getExemplar();
        return ResponseEntity.ok(exemplares.stream().map(ExemplarDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Exemplar> exemplar = service.getExemplarById(id);
        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exemplar.map(ExemplarDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ExemplarDTO dto) {
        try {
            Exemplar exemplar = converter(dto);
            exemplar = service.salvar(exemplar);
            return new ResponseEntity(exemplar, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ExemplarDTO dto) {
        if (!service.getExemplarById(id).isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Exemplar exemplar = converter(dto);
            exemplar.setId(id);
            service.salvar(exemplar);
            return ResponseEntity.ok(exemplar);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Exemplar> exemplar = service.getExemplarById(id);
        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(exemplar.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Exemplar converter(ExemplarDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Exemplar exemplar = modelMapper.map(dto, Exemplar.class);
        return exemplar;
    }
}