package br.com.studiolpilates.model;

public enum Status {

	ATIVO("Ativo"), 
	INATIVO("Inativo");

	private String descricaoStatus;

	Status(String descricaoStatus) {
		this.descricaoStatus = descricaoStatus;
	}

	public String getDescricaoStatus() {
		return descricaoStatus;
	}
}
