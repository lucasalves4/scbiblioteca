package com.example.scbiblioteca.service;

import com.example.scbiblioteca.model.entity.Configuracao;
import com.example.scbiblioteca.model.repository.ConfiguracaoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConfiguracaoService {
    private ConfiguracaoRepository repository;

    public ConfiguracaoService(ConfiguracaoRepository repository) {
        this.repository = repository;
    }

    public List<Configuracao> getConfiguracao() {
        return repository.findAll();
    }

    public Optional<Configuracao> getConfiguracaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Configuracao salvar(Configuracao configuracao) {
        //validar(configuracao);
        return repository.save(configuracao);
    }

    @Transactional
    public void excluir(Configuracao configuracao) {
        Objects.requireNonNull(configuracao.getId());
        repository.delete(configuracao);
    }

//    public void validar(Configuracao configuracao) {
//        if (configuracao.getDocumento() == null || aluno.getMatricula() == 0) {
//            throw new RegraNegocioException("Matrícula inválida");
//        }
//        if (aluno.getNome() == null || aluno.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (aluno.getCurso() == null || aluno.getCurso().getId() == null || aluno.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
//    }
}
