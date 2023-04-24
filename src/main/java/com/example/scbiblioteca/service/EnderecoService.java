package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Endereco;
import com.example.scbiblioteca.model.repository.EnderecoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EnderecoService {
    private EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public List<Endereco> getEndereco() {
        return repository.findAll();
    }

    public Optional<Endereco> getEnderecoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Endereco salvar(Endereco endereco) {
        validar(endereco);
        return repository.save(endereco);
    }

    @Transactional
    public void excluir(Endereco endereco) {
        Objects.requireNonNull(endereco.getId());
        repository.delete(endereco);
    }

    public void validar(Endereco endereco) {
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().equals("")) {
            throw new RegraNegocioException("Logradouro inválido");
        }
        if(endereco.getNumero() == null || endereco.getNumero() <= 0) {
            throw new RegraNegocioException("Número inválido");
        }
        if(endereco.getComplemento() == null) {
            throw new RegraNegocioException("Complemento inválido");
        }
        if(endereco.getBairro() == null || endereco.getBairro().trim().equals("")) {
            throw new RegraNegocioException("Bairro inválido");
        }
        if(endereco.getCidade() == null || endereco.getCidade().trim().equals("")) {
            throw new RegraNegocioException("Cidade inválida");
        }
        if(endereco.getUf() == null || endereco.getUf().trim().equals("")) {
            throw new RegraNegocioException("UF inválido");
        }
        if(endereco.getCep() == null || endereco.getCep().trim().equals("")) {
            throw new RegraNegocioException("CEP inválido");
        }
    }
}
