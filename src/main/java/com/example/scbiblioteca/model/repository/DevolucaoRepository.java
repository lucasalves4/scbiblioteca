package com.example.scbiblioteca.model.repository;

import com.example.scbiblioteca.model.entity.Devolucao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {

}
