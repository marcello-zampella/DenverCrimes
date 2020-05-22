package it.polito.tdp.crimes.model;

public class Reato {
	
	String tipoReato;
	String quartiere;
	public Reato(String tipoReato, String quartiere) {
		super();
		this.tipoReato = tipoReato;
		this.quartiere = quartiere;
	}
	public String getTipoReato() {
		return tipoReato;
	}
	public void setTipoReato(String tipoReato) {
		this.tipoReato = tipoReato;
	}
	public String getQuartiere() {
		return quartiere;
	}
	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}
	
	

}
