package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.LeitorDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Endereco;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.service.EnderecoService;
import com.example.scbiblioteca.service.LeitorService;
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
@RequestMapping("/api/v1/leitores")
@RequiredArgsConstructor
@Api("API de Leitores")
@CrossOrigin


public class LeitorController {

    private final LeitorService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Leitor> leitores = service.getLeitor();
        return ResponseEntity.ok(leitores.stream().map(LeitorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Leitores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Leitor encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Leitor não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Leitor> leitor = service.getLeitorById(id);
        if (!leitor.isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(leitor.map(LeitorDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona um novo leitor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Leitor criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o leitor"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")

    })
    public ResponseEntity post(@RequestBody LeitorDTO dto) {
        try {
            Leitor leitor = converter(dto);
            Endereco endereco = enderecoService.salvar(leitor.getEndereco());
            leitor.setEndereco(endereco);
            leitor = service.salvar(leitor);
            return new ResponseEntity(leitor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um leitor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Leitor editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o leitor"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LeitorDTO dto) {
        if (!service.getLeitorById(id).isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Leitor leitor = converter(dto);
            leitor.setId(id);
            Endereco endereco = enderecoService.salvar(leitor.getEndereco());
            leitor.setEndereco(endereco);
            service.salvar(leitor);
            return ResponseEntity.ok(leitor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um leitor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o leitor"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Leitor> leitor = service.getLeitorById(id);
        if (!leitor.isPresent()) {
            return new ResponseEntity("Leitor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(leitor.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Leitor converter(LeitorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Leitor leitor = modelMapper.map(dto, Leitor.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        leitor.setEndereco(endereco);
        return leitor;
    }

}