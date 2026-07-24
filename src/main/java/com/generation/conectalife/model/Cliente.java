package com.generation.conectalife.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	@NotBlank(message = "Atributo nome do cliente é obrigatorio!")
	@Size(min = 2, max = 100, message = "O atributo nome do cliente deve conter entre 2 e 100 caracteres!")
	@Column(name = "nome", nullable = false, length = 100)
	public String nome;
	
	@NotNull(message = "Atributo data de nascimento do cliente é obrigatório!")
    @Past(message = "A data de nascimento deve ser anterior à data atual!")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;
	
	@NotBlank(message = "O CPF do cliente é obrigatório!")
    @Pattern(
        regexp = "\\d{11}",
        message = "O CPF deve conter exatamente 11 dígitos numéricos!"
    )
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;
	
	@NotBlank(message = "O email do cliente é obrigatório!")
    @Email(message = "O email informado não é válido!")
    @Column(name = "email", nullable = false, unique = true, length = 150) //significa que o valor daquela coluna não pode se repetir no banco de dados
    private String email;
	
	@OneToMany(mappedBy = "clientes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apolice> apolices;
	
	public int calcularIdade() {
	    return Period.between(this.dataNascimento, LocalDate.now()).getYears();
	}
	
	public Boolean verificarEligibilidadeSeguro(int idade) {
		return idade >= 18;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nomeCliente) {
		this.nome = nomeCliente;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
