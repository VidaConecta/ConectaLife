package com.generation.conectalife.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // Indica a entidade do banco de dados
@Table(name = "tb_usuarios") // Indica o nome da tabela no banco de dados
public class Usuario {
	
	@Id // Indica a chave primária da tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que o valor da chave primária será gerado automaticamente
	private Long id; // Atributo id do tipo Long
	
	@NotBlank(message = "O nome é obrigatório") // Validação para não permitir valor nulo ou vazio
	private String nome;
	
	@NotBlank(message = "O email é obrigatório") // Validação para não permitir valor nulo ou vazio
	@Email(message = "O email deve ser válido") // Validação para verificar se o valor é um email válido
	private String email;
	
	@NotBlank(message = "A senha é obrigatória") // Validação para não permitir valor nulo ou vazio
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") // Validação para verificar se o valor tem no mínimo 6 caracteres
	private String senha;
	
	// Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
