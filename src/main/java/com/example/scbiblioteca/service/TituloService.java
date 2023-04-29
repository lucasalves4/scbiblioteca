package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Titulo;
import com.example.scbiblioteca.model.repository.TituloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TituloService {
    private TituloRepository repository;

    public TituloService(TituloRepository repository) {
        this.repository = repository;
    }

    public List<Titulo> getTitulo() {
        return repository.findAll();
    }

    public Optional<Titulo> getTituloById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Titulo salvar(Titulo titulo) {
        validar(titulo);
        return repository.save(titulo);
    }

    @Transactional
    public void excluir(Titulo titulo) {
        Objects.requireNonNull(titulo.getId());
        repository.delete(titulo);
    }

    public void validar(Titulo titulo) {
        if (titulo.getTitulo() == null || titulo.getTitulo().trim().equals("")) {
            throw new RegraNegocioException("Titulo inválido");
        }
        if(titulo.getSubtitulo() == null || titulo.getSubtitulo().trim().equals("")) {
            throw new RegraNegocioException("Subtitulo inválido");
        }
        if(titulo.getArea() == null || titulo.getArea().trim().equals("")) {
            throw new RegraNegocioException("Área inválida");
        }
        if(titulo.getNotaSerie() == null || titulo.getNotaSerie().trim().equals("")) {
            throw new RegraNegocioException("Nota da série inválida");
        }
        if(titulo.getCidadePublicacao() == null || titulo.getCidadePublicacao().trim().equals("")) {
            throw new RegraNegocioException("Cidade de publicação inválida");
        }
        if(titulo.getEditora() == null || titulo.getEditora().trim().equals("")) {
            throw new RegraNegocioException("Editora inválida");
        }
        if(titulo.getDataPublicacao() == null || titulo.getDataPublicacao().trim().equals("")) {
            throw new RegraNegocioException("Data de publicação inválida");
        }
        if(titulo.getIdioma() == null || titulo.getIdioma().trim().equals("")) {
            throw new RegraNegocioException("Idioma inválido");
        }
        if(titulo.getCodClassificacao() <= 0) {
            throw new RegraNegocioException("Código de classificação inválido");
        }
        if(titulo.getEdicao() <= 0) {
            throw new RegraNegocioException("Edição inválida");
        }
        if(titulo.getTotalPaginas() <= 0) {
            throw new RegraNegocioException("Total de páginas inválido");
        }
    }
}
