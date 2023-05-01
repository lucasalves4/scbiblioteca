package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ReservaDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Exemplar;
import com.example.scbiblioteca.model.entity.Funcionario;
import com.example.scbiblioteca.model.entity.Reserva;
import com.example.scbiblioteca.service.ExemplarService;
import com.example.scbiblioteca.service.FuncionarioService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor

public class ReservaController{

    private final ReservaService service;
    private final FuncionarioService funcionarioService;
    private final ExemplarService exemplarService;

    @GetMapping()
    public ResponseEntity get() {
        List<Reserva> reservas = service.getReserva();
        return ResponseEntity.ok(reservas.stream().map(ReservaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reserva.map(ReservaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody ReservaDTO dto) {
        try {
            Reserva reserva = converter(dto);
            reserva = service.salvar(reserva);
            return new ResponseEntity(reserva, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ReservaDTO dto) {
        if (!service.getReservaById(id).isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Reserva reserva = converter(dto);
            reserva.setId(id);
            service.salvar(reserva);
            return ResponseEntity.ok(reserva);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Reserva> reserva = service.getReservaById(id);
        if (!reserva.isPresent()) {
            return new ResponseEntity("Reserva não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(reserva.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Reserva converter(ReservaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Reserva reserva = modelMapper.map(dto, Reserva.class);
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                reserva.setFuncionario(null);
            } else {
                reserva.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdExemplar() != null) {
            Optional<Exemplar> exemplar = exemplarService.getExemplarById(dto.getIdExemplar());
            if (!exemplar.isPresent()) {
                reserva.setExemplar(null);
            } else {
                reserva.setExemplar(exemplar.get());
            }
        }
        return reserva;
    }
}