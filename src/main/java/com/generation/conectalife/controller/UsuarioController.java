package com.generation.conectalife.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.conectalife.model.Usuario;
import com.generation.conectalife.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController // Indica que a classe é um controlador REST
@RequestMapping("/usuarios") // Caminho base para as requisições
@CrossOrigin(origins = "*", allowedHeaders = "*") // Permite requisições de qualquer origem
public class UsuarioController {
    
    @Autowired // Injeção automática do repositório pelo Spring
    private UsuarioRepository usuarioRepository;
    
    // Listar todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll(); // Retorna todos os usuários do banco
    }
    
    // Buscar usuário por ID
    @GetMapping("/{id}") 
    public ResponseEntity<Usuario> listarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id); // Busca pelo ID
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 200 ou 404
    }
    
    // Criar novo usuário (com validação)
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioRepository.save(usuario); // Salva no banco
        return ResponseEntity.ok(novoUsuario); // Retorna o usuário criado
    }
    
    // Atualizar usuário existente (com validação)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        if (!usuarioRepository.existsById(id)) { // Verifica se existe
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrado
        }
        usuario.setId(id); // Define o ID para garantir atualização
        Usuario usuarioSalvo = usuarioRepository.save(usuario); // Salva no banco
        return ResponseEntity.ok(usuarioSalvo); // Retorna o usuário atualizado
    }

}

