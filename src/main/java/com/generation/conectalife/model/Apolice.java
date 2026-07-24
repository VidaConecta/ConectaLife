package com.generation.conectalife.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "tb_apolices")
public class Apolice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String numeroApolice;
	
	 
    @Enumerated(EnumType.STRING) // salva o texto do enum no bando de dados
    @Column(nullable = false)
	private StatusApolice status;
	
    @NotNull(message = "O valor da cobertura é obrigatório")
    @Positive(message = "O valor da cobertura deve ser maior que zero")
    @Column(name = "valor_cobertura", nullable = false, precision = 10, scale = 2)
	private BigDecimal  valorCobertura;
	
    @NotNull(message = "A data de vigência é obrigatória")
    @FutureOrPresent(message = "A data de vigência não pode ser no passado") //valida a data
    @Column(name = "data_vigencia", nullable = false)
	private LocalDate dataVigencia;
    
     
	
	 
    //Gerando o numero de apolice automaticamente pelo JPA
    @PrePersist //Antes do INSERT da entidade executa este método primeiro
    public void gerarNumeroAutomatico() {
    	if(this.numeroApolice == null) {
    		
    		this.numeroApolice = "AP-" + LocalDate.now().getYear() + "-" + 
    				UUID.randomUUID().toString().substring(0,8).toUpperCase(); //UUID.randomUUID() Gera um identificador universalmente único
    	}
    }
    
    //apolice vigente 
    @Transient //garante que o JPA não tente procurar uma coluna 'esta_vigente' no banco.
    public boolean estaVigente() {
    	if (this.dataVigencia == null || this.status == null) {
    		return false;
    	}
    	
    	boolean statusAtivo = StatusApolice.ATIVO.equals(this.status);
    	boolean dentroDoPrazo = !LocalDate.now().isAfter(this.dataVigencia);
    	
    	return statusAtivo && dentroDoPrazo;
    }


    //Cliente elegivel
     
    public boolean clienteElegivelCobertura(boolean temCrimeHediondo) {
        return this.estaVigente() && !temCrimeHediondo;
    }
    
    
 
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNumeroApolice() {
		return numeroApolice;
	}


	public void setNumeroApolice(String numeroApolice) {
		this.numeroApolice = numeroApolice;
	}


	public StatusApolice getStatus() {
		return status;
	}


	public void setStatus(StatusApolice status) {
		this.status = status;
	}


	public BigDecimal getValorCobertura() {
		return valorCobertura;
	}


	public void setValorCobertura(BigDecimal valorCobertura) {
		this.valorCobertura = valorCobertura;
	}


	public LocalDate getDataVigencia() {
		return dataVigencia;
	}


	public void setDataVigencia(LocalDate dataVigencia) {
		this.dataVigencia = dataVigencia;
	}
    
    
	
	
	
	
	
	
	

}
