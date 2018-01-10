package br.com.studiolpilates.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Paciente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 0, max = 80, message = "Tamanho mínimo do nome é 0 e máximo de 80 caracteres!")
	@NotEmpty(message = "Nome é obrigatório!")
	@Column(name = "nome", nullable = false, length = 80)
	private String nome;

	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,4}$")
	@Column(name = "email", length = 35)
	private String email;

	@NotNull(message = "Data de Nascimento é obrigatório!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@NotNull(message = "Sexo é obrigatório!")
	@Enumerated(EnumType.STRING)
	@Column(name = "sexo")
	private Sexo sexo;

	@Embedded
	private Endereco endereco;

	@Column(name = "contato")
	private String telefoneContato;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;

	// @NotNull(message = "Status é obrigatório!")
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Column(name = "email_confirmacao")
	private boolean enviarEmailConfirmacao;

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

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefoneContato() {
		return telefoneContato;
	}

	public void setTelefoneContato(String telefoneContato) {
		this.telefoneContato = telefoneContato;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isAtivo() {
		return Status.ATIVO.equals(this.status);
	}

	public boolean isInativo() {
		return Status.INATIVO.equals(this.status);
	}

	public boolean isEnviarEmailConfirmacao() {
		return enviarEmailConfirmacao;
	}

	public void setEnviarEmailConfirmacao(boolean enviarEmailConfirmacao) {
		this.enviarEmailConfirmacao = enviarEmailConfirmacao;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Paciente other = (Paciente) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Cliente{" + "id=" + id + ", nome=" + nome + ", email=" + email + ", dataNascimento=" + dataNascimento
				+ ", sexo=" + sexo + ", telefoneContato=" + telefoneContato + ", dataCadastro=" + dataCadastro
				+ ", status=" + status + '}';
	}
}
