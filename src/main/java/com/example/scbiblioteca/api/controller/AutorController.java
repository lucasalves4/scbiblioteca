package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.AutorDTO;
import com.example.scbiblioteca.model.entity.Autor;
import com.example.scbiblioteca.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor

public class AutorController {

    private final AutorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Autor> autores = service.getAutor();
        return ResponseEntity.ok(autores.stream().map(AutorDTO::create).collect(Collectors.toList()));
    }

}
