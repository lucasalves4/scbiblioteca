package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.AutorDTO;
import com.example.scbiblioteca.exception.RegraNegocioException;
import com.example.scbiblioteca.model.entity.Autor;
import com.example.scbiblioteca.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor

public class AutorController {

    private final AutorService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Autor> autores = service.getAutor();
        return ResponseEntity.ok(autores.stream().map(AutorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Autor> autor = service.getAutorById(id);
        if (!autor.isPresent()) {
            return new ResponseEntity("Autor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(autor.map(AutorDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(AutorDTO dto) {
        try {
            Autor autor = converter(dto);
            autor = service.salvar(autor);
            return new ResponseEntity(autor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Autor converter(AutorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Autor autor = modelMapper.map(dto, Autor.class);
        return autor;
    }

//    @PostMapping()
//    public ResponseEntity post(AutorDTO dto) {
//        try {
//            Autor autor = converter(dto);
//            autor = autorService.salvar(autor.getNome());
//            autor.setNome(endereco);
//            autor = service.salvar(autor);
//            return new ResponseEntity(autor, HttpStatus.CREATED);
//        } catch (RegraNegocioException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//
//    public Autor converter(AutorDTO dto) {
//        ModelMapper modelMapper = new ModelMapper();
//        Autor autor = modelMapper.map(dto, Autor.class);
//        autor.setNome(autor);
//        if (dto.getNome()!=null){
//            Optional<Autor> autor = autorService.getAutorById(dto.getId());
//            if(!id.isPresent)){
//            autor.setId(null);
//            }
//        }
// //       if (dto.getNome() != null) {
// //           Optional<Curso> curso = autorService(dto.getIdCurso());
// //           if (!autor.isPresent()) {
// //               autor.setNome(null);
////            } else {
// //               autor.setNome(curso.get());
// //           }
//  //      }
//        return autor;
//    }
}
