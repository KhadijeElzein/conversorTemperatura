package br.com.khadije.conversortemperatura.resources;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.exceptions.EscalaException;
import br.com.khadije.conversortemperatura.facades.ConversorTemperaturaFacade;
import br.com.khadije.conversortemperatura.facades.HistoricoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping(path = "/v1.0/conversor", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value="temperatureConverter", description = "Endpoint for converting temperature from celcius to fahrenheit and fahrenheit to celcius")
public class ConversorTemperaturaResource {
	@Autowired
	ConversorTemperaturaFacade facade;
    @Autowired
    HistoricoFacade historicoFacade;
	@PostMapping(value="/converter-temperatura")
    @ApiOperation(value="Returns the temperature converted from fahrenheit or from celcius.", response=ResponseEntity.class)
    public ResponseEntity<Map<String,String>> convertTemperature(@RequestBody ConversorTemperatura conversor) throws EscalaException {
		if(conversor.getTemperatura() == null || conversor.getEscala() == null || conversor.getEscala() == "")
			throw new IllegalArgumentException();
		String result  = facade.converterTemperatura(conversor);
		if (result == null)
			throw new EscalaException(conversor.getEscala());
		historicoFacade.criarHistorico(conversor,result);
    	return ResponseEntity.ok(Collections.singletonMap("resultadoConversao", result));
    }
}
