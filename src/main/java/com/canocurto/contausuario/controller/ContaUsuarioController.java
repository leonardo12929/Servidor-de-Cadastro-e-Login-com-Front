package com.canocurto.contausuario.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.canocurto.contausuario.dto.DetalhaCadastro;
import com.canocurto.contausuario.dto.FazerCadastro;
import com.canocurto.contausuario.dto.FazerLogin;
import com.canocurto.contausuario.service.ContaService;

@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST}
)
@RestController
@RequestMapping("/contausuario")
public class ContaUsuarioController {

    private ContaService contaService;
    
    public ContaUsuarioController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody FazerCadastro dados, UriComponentsBuilder uriBuilder) {
        
        try{
            var contaUsuario = contaService.cadastrar(dados);
    
            var uri = uriBuilder.path("/contausuario/listar/{id}").buildAndExpand(contaUsuario.getId()).toUri();
    
            return ResponseEntity.created(uri).body(new DetalhaCadastro(contaUsuario));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("Message: ", e.getMessage()));
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody FazerLogin dados) {
        
        return contaService.login(dados);
    }
    
    @GetMapping("/lista") 
    public ResponseEntity<List<DetalhaCadastro>> listar() {
        
        return contaService.listar();
    }   
    
    
}
