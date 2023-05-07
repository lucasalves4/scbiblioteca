package com.example.scbiblioteca.service;

import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Reserva;
import com.example.scbiblioteca.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {
    private ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> getReserva() {
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(Reserva reserva) {
        validar(reserva);
        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva) {
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }

    public void validar(Reserva reserva) {
        if (reserva.getExemplar() == null || reserva.getExemplar().equals("")) {
            throw new RegraNegocioException("Exemplar inválido");
        }
        if (reserva.getFuncionario() == null || reserva.getFuncionario().equals("")) {
            throw new RegraNegocioException("Funcionário inválido");
        }
    }
}
