package br.com.tokiomarine.domain;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@NotEmpty(message = "Preencha o Nome")
	private String nome;

	@Column(nullable = false)
	@NotEmpty
	@NotEmpty(message = "Preencha o Email")
	private String email;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Endereco> enderecos;
	
	public Cliente() {}

	public Cliente(Long id, @NotEmpty String nome, @NotEmpty @Email String email, List<Endereco> enderecos) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", enderecos=" + enderecos + "]";
	}
	
	
	

}