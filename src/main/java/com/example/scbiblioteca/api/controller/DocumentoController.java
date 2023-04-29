package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DocumentoDTO;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/documentos")
@RequiredArgsConstructor

public class DocumentoController{

    private final DocumentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Documento> documentos = service.getDocumento();
        return ResponseEntity.ok(documentos.stream().map(DocumentoDTO::create).collect(Collectors.toList()));
    }

}