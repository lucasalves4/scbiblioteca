package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Leitor;
import org.modelmapper.ModelMapper;

public class LeitorDTO {

    private Long id;
    private String nome;
    private String sexo;
    private String telefone;
    private String email;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static LeitorDTO create(Leitor leitor) {
        ModelMapper modelMapper = new ModelMapper();
        LeitorDTO dto = modelMapper.map(leitor, LeitorDTO.class);
        dto.nome = leitor.getNome();
        dto.sexo = leitor.getSexo();
        dto.telefone = leitor.getTelefone();
        dto.email = leitor.getEmail();
        dto.logradouro = leitor.getEndereco().getLogradouro();
        dto.numero = leitor.getEndereco().getNumero();
        dto.complemento = leitor.getEndereco().getComplemento();
        dto.bairro = leitor.getEndereco().getBairro();
        dto.cidade = leitor.getEndereco().getCidade();
        dto.uf = leitor.getEndereco().getUf();
        dto.cep = leitor.getEndereco().getCep();
        return dto;
    }
}
