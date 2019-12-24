package br.com.khadije.conversortemperatura.enums;

public enum EscalaEnum {
	
	CELCIUS("celcius"), FAHRENHEIT("fahrenheit");
	
	private String descricao;
	
	private EscalaEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}