package com.canocurto.contausuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canocurto.contausuario.entity.ContaUsuario;

public interface ContaUsuarioRepository extends JpaRepository<ContaUsuario, Long>{
    
    Optional<ContaUsuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
