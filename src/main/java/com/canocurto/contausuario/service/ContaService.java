package com.canocurto.contausuario.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.canocurto.contausuario.dto.DetalhaCadastro;
import com.canocurto.contausuario.dto.FazerCadastro;
import com.canocurto.contausuario.dto.FazerLogin;
import com.canocurto.contausuario.entity.ContaUsuario;
import com.canocurto.contausuario.repository.ContaUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContaService {

    private ContaUsuarioRepository contaRepository;

    public ContaService(ContaUsuarioRepository dados) {
        this.contaRepository = dados;
    }



    
    public ContaUsuario cadastrar(FazerCadastro dados) {
        if(contaRepository.existsByEmail(dados.email())) {          // VEREFICA SE JÁ EXISTE UMA UMA CONTA COM ESTE EMAIL
            
            throw new IllegalArgumentException("Já existe uma conta com este email");
        }
        
        var contaUsuario = new ContaUsuario(dados);
        contaRepository.save(contaUsuario);
        return contaUsuario;
    }




    public ResponseEntity<?> login(FazerLogin dados) {
        var conta = contaRepository.findByEmail(dados.email());

        if(conta.isEmpty()) {                       //  VERIFICA SE O EMAIL ESTÁ CERTO
            return ResponseEntity.status(401).body("Verefique seu email ou senha");
        }

        if(!conta.get().getSenha().equals(dados.senha())) {   // VEREFICA SE A SENHA DA CONTA ESTÁ CERTA
            
            return ResponseEntity.status(401).body("Verefique sua email ou senha");
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<DetalhaCadastro>> listar() {
         var lista = contaRepository.findAll().stream().map(DetalhaCadastro:: new).toList();

        return ResponseEntity.ok(lista);
       
    }
    
}
