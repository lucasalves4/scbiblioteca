package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.ConfiguracaoService;
import com.example.scbiblioteca.service.DocumentoService;
import com.example.scbiblioteca.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/configuracoes")
@RequiredArgsConstructor

public class ConfiguracaoController{

    private final ConfiguracaoService service;
    private final DocumentoService documentoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Configuracao> configuracoes = service.getConfiguracao();
        return ResponseEntity.ok(configuracoes.stream().map(ConfiguracaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Configuracao> configuracao = service.getConfiguracaoById(id);
        if (!configuracao.isPresent()) {
            return new ResponseEntity("Configuração não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(configuracao.map(ConfiguracaoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(ConfiguracaoDTO dto) {
        try {
            Configuracao configuracao = converter(dto);
            configuracao = service.salvar(configuracao);
            return new ResponseEntity(configuracao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, ConfiguracaoDTO dto) {
        if (!service.getConfiguracaoById(id).isPresent()) {
            return new ResponseEntity("Configuracao não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Configuracao configuracao = converter(dto);
            configuracao.setId(id);
            service.salvar(configuracao);
            return ResponseEntity.ok(configuracao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Configuracao> configuracao = service.getConfiguracaoById(id);
        if (!configuracao.isPresent()) {
            return new ResponseEntity("Configuração não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(configuracao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Configuracao converter(ConfiguracaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Configuracao configuracao = modelMapper.map(dto, Configuracao.class);
        if (dto.getIdDocumento() != null) {
            Optional<Documento> documento = documentoService.getDocumentoById(dto.getIdDocumento());
            if (!documento.isPresent()) {
                configuracao.setDocumento(null);
            } else {
                configuracao.setDocumento(documento.get());
            }
        }
        return configuracao;
    }

}
