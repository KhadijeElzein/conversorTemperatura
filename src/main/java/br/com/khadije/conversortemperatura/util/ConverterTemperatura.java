package br.com.khadije.conversortemperatura.util;

public class ConverterTemperatura {

	public Double celciusToFahrenheit(Double temperatura) {
		return (temperatura * (9d/5d)) +32;
	}
	
	public Double fahrenheitToCelcius(Double temperatura) {
		return (temperatura - 32)*(5d/9d);
	}
}
