package com.canocurto.contausuario.entity;

import com.canocurto.contausuario.dto.FazerCadastro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "contausuario")
public class ContaUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeUsuario;
    @Column(unique = true)
    private String email;
    private String senha;

    public ContaUsuario(FazerCadastro dados) {
        this.nomeUsuario = dados.nomeUsuario();
        this.email = dados.email();
        this.senha = dados.senha();
    }

}
