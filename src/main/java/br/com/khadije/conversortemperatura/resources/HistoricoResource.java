package br.com.khadije.conversortemperatura.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.exceptions.NoContentException;
import br.com.khadije.conversortemperatura.facades.ConversorTemperaturaFacade;
import br.com.khadije.conversortemperatura.facades.HistoricoFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/v1.0/historico", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value="temperatureConverter", description = "Endpoint for getting the results of the historic of temperature conversions")
public class HistoricoResource {
	@Autowired
	ConversorTemperaturaFacade facade;
    @Autowired
    HistoricoFacade historicoFacade;
	@GetMapping
    @ApiOperation(value="Returns the historic of temperature conversions.", response=ResponseEntity.class)
    public ResponseEntity<List<Historico>> getHistorico(){
		List<Historico> historicos = historicoFacade.getHistorico();
		if(historicos==null)
			throw new NoContentException();
    	return ResponseEntity.ok(historicos);
    }
}