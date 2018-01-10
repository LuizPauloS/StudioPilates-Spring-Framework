package br.com.studiolpilates.model;

public enum StatusRepasse {
	
	PENDENTE("Pendente"),
	EFETUADO("Efetuado");

	private String descricaoStatusRepasse;

	private StatusRepasse(String descricaoStatusRepasse) {
		this.descricaoStatusRepasse = descricaoStatusRepasse;
	}

	public String getDescricaoStatusRepasse() {
		return descricaoStatusRepasse;
	}
}
