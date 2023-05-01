package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.LeitorDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Endereco;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.service.EnderecoService;
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
@RequestMapping("/api/v1/leitores")
@RequiredArgsConstructor

public class LeitorController{

    private final LeitorService service;
    private final EnderecoService enderecoService;

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

    @PostMapping()
    public ResponseEntity post(@RequestBody LeitorDTO dto) {
        try {
            Leitor leitor = converter(dto);
            Endereco endereco = enderecoService.salvar(leitor.getEndereco());
            leitor.setEndereco(endereco);
            leitor = service.salvar(leitor);
            return new ResponseEntity(leitor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LeitorDTO dto) {
        if (!service.getLeitorById(id).isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Leitor leitor = converter(dto);
            leitor.setId(id);
            Endereco endereco = enderecoService.salvar(leitor.getEndereco());
            leitor.setEndereco(endereco);
            service.salvar(leitor);
            return ResponseEntity.ok(leitor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Leitor> leitor = service.getLeitorById(id);
        if (!leitor.isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(leitor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Leitor converter(LeitorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Leitor leitor = modelMapper.map(dto, Leitor.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        leitor.setEndereco(endereco);
        return leitor;
    }

}