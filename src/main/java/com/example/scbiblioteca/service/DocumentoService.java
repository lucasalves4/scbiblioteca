package com.example.scbiblioteca.service;


import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Documento;
import com.example.scbiblioteca.model.repository.DocumentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentoService {
    private DocumentoRepository repository;

    public DocumentoService(DocumentoRepository repository) {
        this.repository = repository;
    }

    public List<Documento> getDocumento() {
        return repository.findAll();
    }

    public Optional<Documento> getDocumentoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Documento salvar(Documento documento) {
        validar(documento);
        return repository.save(documento);
    }

    @Transactional
    public void excluir(Documento documento) {
        Objects.requireNonNull(documento.getId());
        repository.delete(documento);
    }

    public void validar(Documento documento) {
//        if (documento.getTipoDocumento() == null || documento.getTipoDocumento().trim().equals("")) {
//            throw new RegraNegocioException("Tipo de Documento inválido");
//        }
//        if (documento.getConfiguracao() == null || documento.getConfiguracao().equals("")) {
//            throw new RegraNegocioException("Configuração inválida");
//        }
    }
}
