package br.com.khadije.conversortemperatura.facades;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;


@TestInstance(Lifecycle.PER_CLASS)
class ConversorTemperaturaFacadeTest {
	@InjectMocks
	private ConversorTemperaturaFacade facade;
	@BeforeAll
	private void setUpBeforeClass() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void converterTemperaturaTemperaturaVazia() {
		String escala = "celcius"; 
		ConversorTemperatura conversor = new ConversorTemperatura(null,escala);
		String result = facade.converterTemperatura(conversor);
		assertEquals(null, result);
	}
	
	@Test
	public void converterTemperaturaEscalaVazia() {
		String escala = ""; 
		Double temperatura = 20d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String result = facade.converterTemperatura(conversor);		
		assertEquals(null, result);
	}
	
	@Test
	public void converterTemperaturaCamposVazios() {
		String escala = ""; 
		Double temperatura = null;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String result = facade.converterTemperatura(conversor);		
		assertEquals(null, result);
	}

	@Test
	public void converterTemperaturaEscalaInvalida() {
		String escala = "kelvin";
		Double temperatura = 20d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String result = facade.converterTemperatura(conversor);
		assertEquals(null, result);
	}
	
	
	@Test
	public void converterTemperaturaCelciusFahrenheitValida() {
		String escala = "celcius";
		Double temperatura = 20d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String result = facade.converterTemperatura(conversor);
		
		assertEquals(result,"68.0 °F");
	}
	

	@Test
	public void converterTemperaturaFahrenheitCelciusValida() {
		String escala = "fahrenheit";
		Double temperatura = 68d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);

		String result = facade.converterTemperatura(conversor);
		
		assertEquals(result,"20.0 °C");
	}
}
