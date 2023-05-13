package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ExemplarDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Exemplar;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.model.entity.Titulo;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.service.ExemplarService;
import com.example.scbiblioteca.service.ReservaService;
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
@RequestMapping("/api/v1/exemplares")
@RequiredArgsConstructor
@Api("API de Exemplares")


public class ExemplarController {

    private final ExemplarService service;
    private final TituloService tituloService;

    @GetMapping()
    public ResponseEntity get() {
        List<Exemplar> exemplares = service.getExemplar();
        return ResponseEntity.ok(exemplares.stream().map(ExemplarDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um exemplar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exemplar encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Exemplar não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Exemplar> exemplar = service.getExemplarById(id);
        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exemplar.map(ExemplarDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona um novo exemplar")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Exemplar criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o exemplar"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity post(@RequestBody ExemplarDTO dto) {
        try {
            Exemplar exemplar = converter(dto);
            exemplar = service.salvar(exemplar);
            return new ResponseEntity(exemplar, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um exemplar")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Exemplar editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o exemplar"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ExemplarDTO dto) {
        if (!service.getExemplarById(id).isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Exemplar exemplar = converter(dto);
            exemplar.setId(id);
            service.salvar(exemplar);
            return ResponseEntity.ok(exemplar);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um exemplar")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o exemplar"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Exemplar> exemplar = service.getExemplarById(id);
        if (!exemplar.isPresent()) {
            return new ResponseEntity("Exemplar não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(exemplar.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Exemplar converter(ExemplarDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Exemplar exemplar = modelMapper.map(dto, Exemplar.class);
        if (dto.getIdTitulo() != null) {
            Optional<Titulo> titulo = tituloService.getTituloById(dto.getIdTitulo());
            if (!titulo.isPresent()) {
                exemplar.setTitulo(null);
            } else {
                exemplar.setTitulo(titulo.get());
            }
        }
        return exemplar;
    }
}