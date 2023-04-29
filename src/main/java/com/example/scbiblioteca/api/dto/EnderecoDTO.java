package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Autor;
import com.example.scbiblioteca.model.entity.Endereco;
import org.modelmapper.ModelMapper;

public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    public static EnderecoDTO create(Endereco endereco) {
        ModelMapper modelMapper = new ModelMapper();
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        dto.logradouro = endereco.getLogradouro();
        dto.numero = endereco.getNumero();
        dto.complemento = endereco.getComplemento();
        dto.bairro = endereco.getBairro();
        dto.cidade = endereco.getCidade();
        dto.uf = endereco.getUf();
        dto.cep = endereco.getCep();
        return dto;
    }
}
