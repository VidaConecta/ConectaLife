package com.generation.conectalife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.conectalife.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	// Consulta personalizada para buscar um usuário pelo email
	Usuario findByEmail(String email);

}
