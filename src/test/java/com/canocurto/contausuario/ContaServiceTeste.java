package com.canocurto.contausuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.canocurto.contausuario.dto.FazerCadastro;
import com.canocurto.contausuario.dto.FazerLogin;
import com.canocurto.contausuario.entity.ContaUsuario;
import com.canocurto.contausuario.repository.ContaUsuarioRepository;
import com.canocurto.contausuario.service.ContaService;

class ContaServiceTeste {

    FazerCadastro dadosCadastro;
    FazerLogin dadosLogin;
    
    @Mock
    private ContaUsuarioRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    public ContaServiceTeste() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void beforeEachMetodo() {

        dadosCadastro = new FazerCadastro("leo", "leo@kee", "leo42424");

        dadosLogin = new FazerLogin("leo@gmail", "3322");
        
    }


    @Test
    void deveRetornaExecaoAoVerificarOEmail() {


        when(contaRepository.existsByEmail(dadosCadastro.email())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            contaService.cadastrar(dadosCadastro);
        });

        verify(contaRepository, never()).save(any());
    }
    @Test
    void deverSalvaraContaAoVerificarOEmail() {


        when(contaRepository.existsByEmail(dadosCadastro.email())).thenReturn(false);

        contaService.cadastrar(dadosCadastro);

        verify(contaRepository).save(any(ContaUsuario.class));
    }

    @Test
    void deveRetornar401QuandoLoginInvalido() {


        when(contaRepository.findByEmail(dadosLogin.email())).thenReturn(Optional.empty());
        
        ResponseEntity<?> response = contaService.login(dadosLogin);

        assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());


    }
    @Test
    void deveRetornar401QuandoSenhaInvalida() {

        var conta = new ContaUsuario();
        conta.setEmail("leo@gmail");
        conta.setSenha("0000");

        when(contaRepository.findByEmail(dadosLogin.email())).thenReturn(Optional.of(conta));
        
        ResponseEntity<?> response = contaService.login(dadosLogin);

        assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());


    }
    @Test
    void deveContinuarQuandoLoginValido() {

        var conta = new ContaUsuario();
        conta.setEmail("leo@gmail");
        conta.setSenha("3322");

        when(contaRepository.findByEmail(dadosLogin.email())).thenReturn(Optional.of(conta));
        
        ResponseEntity<?> response = contaService.login(dadosLogin);

        assertEquals(HttpStatus.OK , response.getStatusCode());


    }

}
