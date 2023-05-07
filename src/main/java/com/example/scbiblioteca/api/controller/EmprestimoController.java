package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.EmprestimoDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.*;
import com.example.scbiblioteca.service.*;
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
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
@Api("API de Empréstimos")


public class EmprestimoController {

    private final EmprestimoService service;
    private final LeitorService leitorService;
    private final FuncionarioService funcionarioService;
    private final ExemplarService exemplarService;
    private final DevolucaoService devolucaoService;

    @GetMapping()
    public ResponseEntity get() {
        List<Emprestimo> emprestimos = service.getEmprestimo();
        return ResponseEntity.ok(emprestimos.stream().map(EmprestimoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimos encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Renovação não empréstimos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(emprestimo.map(EmprestimoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Adiciona um novo empréstimo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Empréstimos criado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao criar o empréstimos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity post(@RequestBody EmprestimoDTO dto) {
        try {
            Emprestimo emprestimo = converter(dto);
            Devolucao devolucao = devolucaoService.salvar(emprestimo.getDevolucao());
            emprestimo.setDevolucao(devolucao);
            emprestimo = service.salvar(emprestimo);
            return new ResponseEntity(emprestimo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Edita um empréstimo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Empréstimos editado com sucesso"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao editar o empréstimos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EmprestimoDTO dto) {
        if (!service.getEmprestimoById(id).isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Emprestimo emprestimo = converter(dto);
            emprestimo.setId(id);
            Devolucao devolucao = devolucaoService.salvar(emprestimo.getDevolucao());
            emprestimo.setDevolucao(devolucao);
            service.salvar(emprestimo);
            return ResponseEntity.ok(emprestimo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um empréstimo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Excluído e nenhum conteúdo foi encontrado"),
            @ApiResponse(code = 400, message = "Requisição inválida"),
            @ApiResponse(code = 404, message = "Erro ao deletar o empréstimos"),
            @ApiResponse(code = 405, message = "Método não permitido"),
            @ApiResponse(code = 500, message = "Erro interno do servidor"),
            @ApiResponse(code = 501, message = "Funcionalidade não implementada"),
            @ApiResponse(code = 502, message = "Rede indisponível"),
            @ApiResponse(code = 503, message = "Serviço indisponível")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Emprestimo> emprestimo = service.getEmprestimoById(id);
        if (!emprestimo.isPresent()) {
            return new ResponseEntity("Empréstimo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(emprestimo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Emprestimo converter(EmprestimoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Emprestimo emprestimo = modelMapper.map(dto, Emprestimo.class);
        Devolucao devolucao = modelMapper.map(dto, Devolucao.class);
        emprestimo.setDevolucao(devolucao);
        if (dto.getIdLeitor() != null) {
            Optional<Leitor> leitor = leitorService.getLeitorById(dto.getIdLeitor());
            if (!leitor.isPresent()) {
                emprestimo.setLeitor(null);
            } else {
                emprestimo.setLeitor(leitor.get());
            }
        }
        if (dto.getIdFuncionario() != null) {
            Optional<Funcionario> funcionario = funcionarioService.getFuncionarioById(dto.getIdFuncionario());
            if (!funcionario.isPresent()) {
                emprestimo.setFuncionario(null);
            } else {
                emprestimo.setFuncionario(funcionario.get());
            }
        }
        if (dto.getIdExemplar() != null) {
            Optional<Exemplar> exemplar = exemplarService.getExemplarById(dto.getIdExemplar());
            if (!exemplar.isPresent()) {
                emprestimo.setExemplar(null);
            } else {
                emprestimo.setExemplar(exemplar.get());
            }
        }
        return emprestimo;
    }

}