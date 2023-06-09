package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.TituloDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Autor;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.model.entity.Titulo;
import com.example.scbiblioteca.service.AutorService;
import com.example.scbiblioteca.service.DocumentoService;
import com.example.scbiblioteca.service.TituloService;
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
@RequestMapping("/api/v1/titulos")
@RequiredArgsConstructor
@Api("API de Títulos")
@CrossOrigin


public class TituloController {

    private final TituloService service;
    private final DocumentoService documentoService;
    private final AutorService autorService;

    @GetMapping()
    public ResponseEntity get() {
        List<Titulo> titulos = service.getTitulo();
        return ResponseEntity.ok(titulos.stream().map(TituloDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um título")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Título encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Título não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Titulo> titulo = service.getTituloById(id);
        if (!titulo.isPresent()) {
            return new ResponseEntity("Titulo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(titulo.map(TituloDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona uma nova título")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Título criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o título"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")

    })
    public ResponseEntity post(@RequestBody TituloDTO dto) {
        try {
            Titulo titulo = converter(dto);
            titulo = service.salvar(titulo);
            return new ResponseEntity(titulo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um novo título")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Título editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o título"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TituloDTO dto) {
        if (!service.getTituloById(id).isPresent()) {
            return new ResponseEntity("Titulo não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Titulo titulo = converter(dto);
            titulo.setId(id);
            service.salvar(titulo);
            return ResponseEntity.ok(titulo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um título")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o título"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Titulo> titulo = service.getTituloById(id);
        if (!titulo.isPresent()) {
            return new ResponseEntity("Titulo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(titulo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Titulo converter(TituloDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Titulo titulo = modelMapper.map(dto, Titulo.class);
        if (dto.getIdDocumento() != null) {
            Optional<Documento> documento = documentoService.getDocumentoById(dto.getIdDocumento());
            if (!documento.isPresent()) {
                titulo.setDocumento(null);
            } else {
                titulo.setDocumento(documento.get());
            }
        }
        if (dto.getIdAutor() != null) {
            Optional<Autor> autor = autorService.getAutorById(dto.getIdAutor());
            if (!autor.isPresent()) {
                titulo.setAutor(null);
            } else {
                titulo.setAutor(autor.get());
            }
        }
        return titulo;
    }
}