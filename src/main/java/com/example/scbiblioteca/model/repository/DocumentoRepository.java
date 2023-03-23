package com.example.scbiblioteca.model.repository;

import com.example.scbiblioteca.model.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

}
