package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.DevolucaoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Devolucao;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.service.DevolucaoService;
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
@RequestMapping("/api/v1/devolucoes")
@RequiredArgsConstructor
@Api("API de Devoluções")


public class DevolucaoController {

    private final DevolucaoService service;
    private final EmprestimoService emprestimoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Devolucao> devolucoes = service.getDevolucao();
        return ResponseEntity.ok(devolucoes.stream().map(DevolucaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um devolução")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Devolção encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Devolção não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Devolucao> devolucao = service.getDevolucaoById(id);
        if (!devolucao.isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(devolucao.map(DevolucaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona uma nova devolução")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Devolção criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o devolção"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity post(@RequestBody DevolucaoDTO dto) {
        try {
            Devolucao devolucao = converter(dto);
            devolucao = service.salvar(devolucao);
            return new ResponseEntity(devolucao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita uma devolução")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Devolção editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o devolção"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DevolucaoDTO dto) {
        if (!service.getDevolucaoById(id).isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Devolucao devolucao = converter(dto);
            devolucao.setId(id);
            service.salvar(devolucao);
            return ResponseEntity.ok(devolucao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluí uma devolução")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o devolção"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Devolucao> devolucao = service.getDevolucaoById(id);
        if (!devolucao.isPresent()) {
            return new ResponseEntity("Devolução não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(devolucao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Devolucao converter(DevolucaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Devolucao devolucao = modelMapper.map(dto, Devolucao.class);
        return devolucao;
    }

}