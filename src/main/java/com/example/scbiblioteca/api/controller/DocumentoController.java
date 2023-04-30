package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.api.dto.DocumentoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Documento> documento = service.getDocumentoById(id);
        if (!documento.isPresent()) {
            return new ResponseEntity("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(documento.map(DocumentoDTO::create));
    }

}