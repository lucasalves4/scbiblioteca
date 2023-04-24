package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ExemplarDTO;
import com.example.scbiblioteca.model.entity.Exemplar;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/exemplares")
@RequiredArgsConstructor

public class ExemplarController{

    private final ExemplarService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Exemplar> exemplares = service.getExemplar();
        return ResponseEntity.ok(exemplar.stream().map(ExemplarDTO::create).collect(Collectors.toList()));
    }

}