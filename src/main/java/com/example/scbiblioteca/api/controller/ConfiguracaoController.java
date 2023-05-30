package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.ConfiguracaoDTO;
import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.service.ConfiguracaoService;
import com.example.scbiblioteca.service.DocumentoService;
import com.example.scbiblioteca.exception.RegraNegocioException;
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
@RequestMapping("/api/v1/configuracoes")
@RequiredArgsConstructor
@Api("API de Confurações")
@CrossOrigin

public class ConfiguracaoController {

    private final ConfiguracaoService service;
    private final DocumentoService documentoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Configuracao> configuracoes = service.getConfiguracao();
        return ResponseEntity.ok(configuracoes.stream().map(ConfiguracaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma configuração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Configuração encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Configuração não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Configuracao> configuracao = service.getConfiguracaoById(id);
        if (!configuracao.isPresent()) {
            return new ResponseEntity("Configuração não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(configuracao.map(ConfiguracaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona uma nova configuração")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Configuração criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o configuração"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity post(@RequestBody ConfiguracaoDTO dto) {
        try {
            Configuracao configuracao = converter(dto);
            configuracao = service.salvar(configuracao);
            return new ResponseEntity(configuracao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um novo configuração")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Configuração editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o configuração"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ConfiguracaoDTO dto) {
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
    @ApiOperation("Excluir uma configuração")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o configuração"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
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
        return configuracao;
    }

}
