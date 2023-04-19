package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.LeitorDTO;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.service.LeitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

}