package com.javanauta.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDTO> endereco;
    private List<TelefoneDTO> telefone;
}


// Aqui não precisa fazer as anotações dos relacionamentos, é só uma classe para transformar os objetos em entidades
