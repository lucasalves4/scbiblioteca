package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Emprestimo;
import com.example.scbiblioteca.model.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class EmprestimoService {
    private EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<Emprestimo> getEmprestimo() {
        return repository.findAll();
    }

    public Optional<Emprestimo> getEmprestimoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Emprestimo salvar(Emprestimo emprestimo) {
        validar(emprestimo);
        return repository.save(emprestimo);
    }

    @Transactional
    public void excluir(Emprestimo emprestimo) {
        Objects.requireNonNull(emprestimo.getId());
        repository.delete(emprestimo);
    }

    public void validar(Emprestimo emprestimo) {
        if (emprestimo.getLeitor() == null || emprestimo.getLeitor().equals("")) {
            throw new RegraNegocioException("Leitor inválido");
        }
        if(emprestimo.getExemplar() == null || emprestimo.getExemplar().equals("")) {
            throw new RegraNegocioException("Exemplar inválido");
        }
        if(emprestimo.getFuncionario() == null || emprestimo.getFuncionario().equals("")) {
            throw new RegraNegocioException("Funcionário inválido");
        }
    }
}
