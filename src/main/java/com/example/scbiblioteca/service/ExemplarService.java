package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Exemplar;
import com.example.scbiblioteca.model.repository.ExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExemplarService {
    private ExemplarRepository repository;

    public ExemplarService(ExemplarRepository repository) {
        this.repository = repository;
    }

    public List<Exemplar> getExemplar() {
        return repository.findAll();
    }

    public Optional<Exemplar> getExemplarById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Exemplar salvar(Exemplar exemplar) {
        validar(exemplar);
        return repository.save(exemplar);
    }

    @Transactional
    public void excluir(Exemplar exemplar) {
        Objects.requireNonNull(exemplar.getId());
        repository.delete(exemplar);
    }

    public void validar(Exemplar exemplar) {
        if (exemplar.getNumeroTombo() <= 0) {
            throw new RegraNegocioException("Número de tombo não pode ser menor ou igual a zero");
        }
    }
}
