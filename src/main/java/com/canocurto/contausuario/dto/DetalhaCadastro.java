package com.canocurto.contausuario.dto;

import com.canocurto.contausuario.entity.ContaUsuario;

public record DetalhaCadastro(

    Long id,
    String nomeUsuario,
    String email,
    String senha
) {
    
    public DetalhaCadastro(ContaUsuario dados) {
        this(dados.getId(), dados.getNomeUsuario(), dados.getEmail(), dados.getSenha());
    }
}
