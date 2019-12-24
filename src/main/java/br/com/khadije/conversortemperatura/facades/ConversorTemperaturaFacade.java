package br.com.khadije.conversortemperatura.facades;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.enums.EscalaEnum;
import br.com.khadije.conversortemperatura.util.ConverterTemperatura;

@Service
@Component
public class ConversorTemperaturaFacade{
	
	public String converterTemperatura(ConversorTemperatura conversor) {
		ConverterTemperatura converter = new ConverterTemperatura();
		String escala = conversor.getEscala().toLowerCase();
		Double temperatura = conversor.getTemperatura();
		if(escala!=null && temperatura != null && escala!="") {
		//Celcius para Fahrenheit
		if(escala.contentEquals(EscalaEnum.CELCIUS.getDescricao()))
			 return converter.celciusToFahrenheit(temperatura) + " °F";
		// Fahrenheit para Celcius
		else if(escala.contentEquals(EscalaEnum.FAHRENHEIT.getDescricao()))
			return converter.fahrenheitToCelcius(temperatura) + " °C";
		}
		return null;
	}
}
