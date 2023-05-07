package com.example.scbiblioteca.api.dto;

import com.example.scbiblioteca.model.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO {
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

    public static FuncionarioDTO create(Funcionario funcionario) {
        ModelMapper modelMapper = new ModelMapper();
        FuncionarioDTO dto = modelMapper.map(funcionario, FuncionarioDTO.class);
        dto.nome = funcionario.getNome();
        dto.sexo = funcionario.getSexo();
        dto.telefone = funcionario.getTelefone();
        dto.email = funcionario.getEmail();
        dto.logradouro = funcionario.getEndereco().getLogradouro();
        dto.numero = funcionario.getEndereco().getNumero();
        dto.complemento = funcionario.getEndereco().getComplemento();
        dto.bairro = funcionario.getEndereco().getBairro();
        dto.cidade = funcionario.getEndereco().getCidade();
        dto.uf = funcionario.getEndereco().getUf();
        dto.cep = funcionario.getEndereco().getCep();
        return dto;
    }

}
