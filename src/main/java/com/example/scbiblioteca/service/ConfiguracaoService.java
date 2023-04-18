package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
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

    public void validar(Configuracao configuracao) {
        if (configuracao.getTipoPrazo() == null || configuracao.getTipoPrazo().trim().equals("")) {
            throw new RegraNegocioException("Tipo de prazo inválido");
        }
        if (configuracao.getPrazoEntregaQuantDias() == null || configuracao.getPrazoEntregaQuantDias().trim().equals("")) {
            throw new RegraNegocioException("Prazo de entrega inserido inválido");
        }
        if (configuracao.getValorMulta() < 0) {
            throw new RegraNegocioException("Valor de multa não pode ser negativo");
        }
        if (configuracao.getQuantMaximaEmprestimo() < 0) {
            throw new RegraNegocioException("Quantidade máxima de exemplares não pode ser negativa");
        }
    }
}
