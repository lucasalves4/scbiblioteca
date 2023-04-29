package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Leitor;
import com.example.scbiblioteca.model.repository.LeitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LeitorService {
    private LeitorRepository repository;

    public LeitorService(LeitorRepository repository) {
        this.repository = repository;
    }

    public List<Leitor> getLeitor() {
        return repository.findAll();
    }

    public Optional<Leitor> getLeitorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Leitor salvar(Leitor leitor) {
        validar(leitor);
        return repository.save(leitor);
    }

    @Transactional
    public void excluir(Leitor leitor) {
        Objects.requireNonNull(leitor.getId());
        repository.delete(leitor);
    }

    public void validar(Leitor leitor) {
        if (leitor.getNome() == null || leitor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if(leitor.getSexo() == null || leitor.getSexo().trim().equals("")) {
            throw new RegraNegocioException("Sexo inválido");
        }
        if(leitor.getTelefone() == null || leitor.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if(leitor.getEmail() == null || leitor.getEmail().trim().equals("")) {
            throw new RegraNegocioException("E-mail inválido");
        }
    }
}
