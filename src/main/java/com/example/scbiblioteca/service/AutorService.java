package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Autor;
import com.example.scbiblioteca.model.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AutorService {
    private AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public List<Autor> getAutor() {
        return repository.findAll();
    }

    public Optional<Autor> getAutorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Autor salvar(Autor autor) {
        validar(autor);
        return repository.save(autor);
    }

    @Transactional
    public void excluir(Autor autor) {
        Objects.requireNonNull(autor.getId());
        repository.delete(autor);
    }

    public void validar(Autor autor) {
        if (autor.getNome() == null || autor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
