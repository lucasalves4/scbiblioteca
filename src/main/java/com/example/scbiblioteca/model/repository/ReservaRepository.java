package com.example.scbiblioteca.model.repository;

import com.example.scbiblioteca.model.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}
