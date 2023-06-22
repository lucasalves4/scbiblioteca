package com.example.scbiblioteca.api.controller;

import com.example.scbiblioteca.api.dto.CredenciaisDTO;
import com.example.scbiblioteca.api.dto.TokenDTO;

import com.example.scbiblioteca.api.dto.UsuarioDTO;
import com.example.scbiblioteca.exception.SenhaInvalidaException;
import com.example.scbiblioteca.model.entity.Usuario;
import com.example.scbiblioteca.security.JwtService;
import com.example.scbiblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity get() {
        List<Usuario> usuarios = usuarioService.getUsuario();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        System.out.println(usuario.isAdmin());
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);

            String rule = usuarioAutenticado
                    .getAuthorities()
                    .toArray()[0]
                    .toString()
                    .equals("ROLE_ADMIN") ? "manager" : "employee";

            return new TokenDTO(usuario.getLogin(), token, rule);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
