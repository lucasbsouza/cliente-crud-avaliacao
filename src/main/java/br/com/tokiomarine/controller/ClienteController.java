package br.com.tokiomarine.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.repository.ClienteRepository;
import br.com.tokiomarine.util.Validate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("clientes")
@Api("API Clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	@ApiOperation(value = "Listagem de Clientes")
	public ResponseEntity<Iterable<Cliente>> listarClientes() {
		return ResponseEntity.ok(clienteRepository.findAll());
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remoção de Clientes")
	public ResponseEntity<Boolean> deletarCliente(@PathVariable Long id) {
		clienteRepository.deleteById(id);
		return ResponseEntity.ok(true);
	}

	@PostMapping("/{id}/email/{email}/nome/{nome}")
	@ApiOperation(value = "Alteração de cliente")
	public ResponseEntity<Object> alterarCliente(@NotEmpty(message = "Campo id é obrigatório") 
												 @PathVariable("id") Long id,
												 @PathVariable("email") String email,
												 @PathVariable("nome") String nome) {
		
		Cliente bdCli;
		try {
			Validate.isValid(email, "email");
			Validate.isValid(nome, "nome");
			bdCli = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		Cliente save = Cliente.builder().nome(nome).email(email).id(bdCli.getId()).build();
		clienteRepository.save(save);
		return ResponseEntity.ok(save);
	}
}
