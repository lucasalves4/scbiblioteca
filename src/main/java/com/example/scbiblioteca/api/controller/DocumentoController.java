package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DocumentoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.ConfiguracaoService;
import com.example.scbiblioteca.service.DocumentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/documentos")
@RequiredArgsConstructor
@Api("API de Documentos")
@CrossOrigin


public class DocumentoController {

    private final DocumentoService service;
    private final ConfiguracaoService configuracaoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Documento> documentos = service.getDocumento();
        return ResponseEntity.ok(documentos.stream().map(DocumentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Documento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Documento encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Documento não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Documento> documento = service.getDocumentoById(id);
        if (!documento.isPresent()) {
            return new ResponseEntity("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(documento.map(DocumentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona um novo documento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Documento criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o documento"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity post(@RequestBody DocumentoDTO dto) {
        try {
            Documento documento = converter(dto);
            Configuracao configuracao = configuracaoService.salvar(documento.getConfiguracao());
            documento.setConfiguracao(configuracao);
            documento = service.salvar(documento);
            return new ResponseEntity(documento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um documento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Documento editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o documento"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DocumentoDTO dto) {
        if (!service.getDocumentoById(id).isPresent()) {
            return new ResponseEntity("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Documento documento = converter(dto);
            documento.setId(id);
            Configuracao configuracao = configuracaoService.salvar(documento.getConfiguracao());
            documento.setConfiguracao(configuracao);
            service.salvar(documento);
            return ResponseEntity.ok(documento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um documento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o documento"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
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
        Configuracao configuracao = modelMapper.map(dto, Configuracao.class);
        documento.setConfiguracao(configuracao);
        return documento;
    }

}