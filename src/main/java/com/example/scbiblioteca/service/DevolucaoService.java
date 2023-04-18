package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Devolucao;
import com.example.scbiblioteca.model.repository.DevolucaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DevolucaoService {
    private DevolucaoRepository repository;

    public DevolucaoService(DevolucaoRepository repository) {
        this.repository = repository;
    }

    public List<Devolucao> getDevolucao() {
        return repository.findAll();
    }

    public Optional<Devolucao> getDevolucaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Devolucao salvar(Devolucao devolucao) {
        validar(devolucao);
        return repository.save(devolucao);
    }

    @Transactional
    public void excluir(Devolucao devolucao) {
        Objects.requireNonNull(devolucao.getId());
        repository.delete(devolucao);
    }

    public void validar(Devolucao devolucao) {
        if (devolucao.getDataDevolucao().trim().equals("00/00/00")) {
            throw new RegraNegocioException("Data inválida");
        }
    }
}
