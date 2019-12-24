package br.com.khadije.conversortemperatura.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterTemperaturaTest {	
	
	private ConverterTemperatura converterTemperatura;
	@BeforeEach
	public void preparaDados() {
		this.converterTemperatura = new ConverterTemperatura();
	}
	@Test
	public void celciusFahrenheitValido() {
		Double temperatura = 20d;
		double resultado = converterTemperatura.celciusToFahrenheit(temperatura);
		assertThat(resultado, equalTo(68d));
	}
	
	@Test
	public void fahrenheitCelciusValido() {
		Double temperatura = 68d;
		double resultado = converterTemperatura.fahrenheitToCelcius(temperatura);
		assertThat(resultado, equalTo(20d));
	}
}