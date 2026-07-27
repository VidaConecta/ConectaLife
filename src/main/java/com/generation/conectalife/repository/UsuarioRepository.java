package com.generation.conectalife.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.conectalife.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// Consulta personalizada para buscar um usuário pelo email
	List<Usuario> findByEmailContainingIgnoreCase(String email);
	
}
