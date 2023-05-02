package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.RenovacaoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.service.EmprestimoService;
import com.example.scbiblioteca.model.entity.Renovacao;
import com.example.scbiblioteca.service.RenovacaoService;
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
@RequestMapping("/api/v1/renovacoes")
@RequiredArgsConstructor
@Api("API de Renovações")


public class RenovacaoController{

    private final RenovacaoService service;
    private final EmprestimoService emprestimoService;


    @GetMapping()
    @ApiOperation("Obter detalhes de um Leitores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Renovação encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Renovação não encontrado"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get() {
        List<Renovacao> renovacoes = service.getRenovacao();
        return ResponseEntity.ok(renovacoes.stream().map(RenovacaoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Renovacao> renovacao = service.getRenovacaoById(id);
        if (!renovacao.isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(renovacao.map(RenovacaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona um novo leitor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Renovação criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o renovação"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")

    })
    public ResponseEntity post(@RequestBody RenovacaoDTO dto) {
        try {
            Renovacao renovacao = converter(dto);
            renovacao = service.salvar(renovacao);
            return new ResponseEntity(renovacao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um novo leitor")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Renovação editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o renovação"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody RenovacaoDTO dto) {
        if (!service.getRenovacaoById(id).isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Renovacao renovacao = converter(dto);
            renovacao.setId(id);
            service.salvar(renovacao);
            return ResponseEntity.ok(renovacao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um leitor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o renovação"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Renovacao> renovacao = service.getRenovacaoById(id);
        if (!renovacao.isPresent()) {
            return new ResponseEntity("Renovação não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(renovacao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Renovacao converter(RenovacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Renovacao renovacao = modelMapper.map(dto, Renovacao.class);
        if (dto.getIdEmprestimo() != null) {
            Optional<Emprestimo> emprestimo = emprestimoService.getEmprestimoById(dto.getIdEmprestimo());
            if (!emprestimo.isPresent()) {
                renovacao.setEmprestimo(null);
            } else {
                renovacao.setEmprestimo(emprestimo.get());
            }
        }
        return renovacao;
    }

}