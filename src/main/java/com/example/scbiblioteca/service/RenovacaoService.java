package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Renovacao;
import com.example.scbiblioteca.model.repository.RenovacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RenovacaoService {
    private RenovacaoRepository repository;

    public RenovacaoService(RenovacaoRepository repository) {
        this.repository = repository;
    }

    public List<Renovacao> getRenovacao() {
        return repository.findAll();
    }

    public Optional<Renovacao> getRenovacaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Renovacao salvar(Renovacao renovacao) {
        validar(renovacao);
        return repository.save(renovacao);
    }

    @Transactional
    public void excluir(Renovacao renovacao) {
        Objects.requireNonNull(renovacao.getId());
        repository.delete(renovacao);
    }

    public void validar(Renovacao renovacao) {
        if (renovacao.getQuantidadeDias() < 0) {
            throw new RegraNegocioException("Quantidade de dias não pode ser negativo");
        }
        if(renovacao.getDataRenovacao() == null) {
            throw new RegraNegocioException("Data inválida");
        }
        if(renovacao.getEmprestimo() == null || renovacao.getEmprestimo().equals("")) {
            throw new RegraNegocioException("Empréstimo inválido");
        }
    }
}
