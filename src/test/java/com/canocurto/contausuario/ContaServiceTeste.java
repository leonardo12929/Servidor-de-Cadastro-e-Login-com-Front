package com.canocurto.contausuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.canocurto.contausuario.dto.FazerCadastro;
import com.canocurto.contausuario.dto.FazerLogin;
import com.canocurto.contausuario.entity.ContaUsuario;
import com.canocurto.contausuario.repository.ContaUsuarioRepository;
import com.canocurto.contausuario.service.ContaService;

class ContaServiceTeste {
    
    @Mock
    private ContaUsuarioRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    public ContaServiceTeste() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornaExecaoAoVerificarOEmail() {

        var dados = new FazerCadastro("leo", "leo@kee", "leo42424");

        when(contaRepository.existsByEmail(dados.email())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.cadastrar(dados);
        });

        verify(contaRepository, never()).save(any());
    }
    @Test
    void deverSalvaraContaAoVerificarOEmail() {

        var dados = new FazerCadastro("leo", "leo@kee", "leo42424");

        when(contaRepository.existsByEmail(dados.email())).thenReturn(false);

        contaService.cadastrar(dados);

        verify(contaRepository).save(any(ContaUsuario.class));
    }

    @Test
    void deveRetornar401QuandoLoginInvalido() {

        var dados = new FazerLogin("leo@gmail", "3322");

        when(contaRepository.findByEmail(dados.email())).thenReturn(Optional.empty());
        
        ResponseEntity<?> response = contaService.login(dados);

        assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());


    }
    void deveRetornar401QuandoSenhaInvalida() {

        var dados = new FazerLogin("leo@gmail", "3322");
         var conta = new ContaUsuario();
        conta.setEmail("leo@gmail");
        conta.setSenha("0000");

        when(contaRepository.findByEmail(dados.email())).thenReturn(Optional.of(conta));
        
        ResponseEntity<?> response = contaService.login(dados);

        assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());


    }
    @Test
    void deveContinuarQuandoLoginValido() {

        var dados = new FazerLogin("leo@gmail", "3322");
        var conta = new ContaUsuario();
        conta.setEmail("leo@gmail");
        conta.setSenha("3322");

        when(contaRepository.findByEmail(dados.email())).thenReturn(Optional.of(conta));
        
        ResponseEntity<?> response = contaService.login(dados);

        assertEquals(HttpStatus.OK , response.getStatusCode());


    }

}
