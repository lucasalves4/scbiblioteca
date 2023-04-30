package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DocumentoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
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
    @PostMapping()
    public ResponseEntity post(DocumentoDTO dto) {
        try {
            Documento documento = converter(dto);
            documento = service.salvar(documento);
            return new ResponseEntity(documento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, DocumentoDTO dto) {
        if (!service.getDocumentoById(id).isPresent()) {
            return new ResponseEntity("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Documento documento = converter(dto);
            documento.setId(id);
            service.salvar(documento);
            return ResponseEntity.ok(documento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Documento> documento = service.getDocumentoById(id);
        if (!documento.isPresent()) {
            return new ResponseEntity("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(documento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Documento converter(DocumentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Documento documento = modelMapper.map(dto, Documento.class);
        return documento;
    }

}