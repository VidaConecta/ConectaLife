package com.generation.conectalife.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.conectalife.model.Apolice;
import com.generation.conectalife.repository.ApoliceRepository;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/apolices")
@CrossOrigin(origins = " * " , allowedHeaders = "*")
public class ApoliceController {
	
	@Autowired
	private ApoliceRepository apoliceRepository;
	
	@GetMapping
	public ResponseEntity<List<Apolice>>getAll(){
		return ResponseEntity.ok(apoliceRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Apolice> getEntity(@PathVariable Long id){
		
		return apoliceRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping
    public ResponseEntity<Apolice> post(@Valid @RequestBody Apolice apolice) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apoliceRepository.save(apolice));
    }
	
<<<<<<< HEAD
	@PutMapping("/{id}")
=======
	/*@PutMapping
>>>>>>> a0ac5d62d2a4186fbe217502443d684d9cf90dca
    public ResponseEntity<Apolice> put(@Valid @RequestBody Apolice apolice) {
        if (apolice.getId() == null || !apoliceRepository.existsById(apolice.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(apoliceRepository.save(apolice));
    }*/
	@PutMapping("/{id}")
	public ResponseEntity<Apolice> put(
	        @PathVariable Long id,
	        @Valid @RequestBody Apolice apolice) {

	    if (!apoliceRepository.existsById(id)) {
	        return ResponseEntity.notFound().build();
	    }

	    apolice.setId(id);

	    return ResponseEntity.ok(apoliceRepository.save(apolice));
	}
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!apoliceRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Apólice não encontrada!");
        }
        apoliceRepository.deleteById(id);
    }
	
	
	// @RequestBody: converte o JSON do corpo da requisição em um objeto Java
	// Map<String, Boolean>: recebe dados no formato chave-valor ex: {"temCrimeHediondo": false}
	//payload Map<String, Object>
	@PostMapping("/{id}/validar-cobertura")
	public ResponseEntity<String> validarCobertura(
	        @PathVariable Long id, 
	        @RequestBody Map<String, Boolean> payload) {

	    //  Pega o valor enviado no JSON: { "temCrimeHediondo": false }
	    boolean temCrime = payload.getOrDefault("temCrimeHediondo", false);

	    // Busca a apólice no banco
	    Apolice apolice = apoliceRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

	    //  USA O MÉTODO DA MODEL AQUI:
	    boolean eElegivel = apolice.clienteElegivelCobertura(temCrime);

	    if (eElegivel) {
	        return ResponseEntity.ok("Cobertura APROVADA para o sinistro.");
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("Cobertura NEGADA: Apólice inativa ou divergência nos antecedentes.");
	    }
	}
	
	
}
