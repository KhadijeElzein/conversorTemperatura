package br.com.khadije.conversortemperatura.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.exceptions.ApiExceptionHandler;
import br.com.khadije.conversortemperatura.facades.ConversorTemperaturaFacade;
import br.com.khadije.conversortemperatura.facades.HistoricoFacade;

@TestInstance(Lifecycle.PER_CLASS)
public class ConversorTemperaturaResourceTest {

private MockMvc mockMvc;
	
	@Mock
	private ConversorTemperaturaFacade conversorTemperaturaFacade;
	
	@Mock
	private HistoricoFacade historicoFacade;
	
	@InjectMocks
	private ConversorTemperaturaResource controller;
	


	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ApiExceptionHandler()).build();
	}

	private ResultActions doPost(String request) throws Exception {
		return this.mockMvc.perform(post("/v1.0/conversor/converter-temperatura").
				contentType(MediaType.APPLICATION_JSON).
				content(request)).andDo(print());
                
	}
	private String mapToJson(ConversorTemperatura conversor) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(conversor);
	}
	@Test
	public void converterTemperaturaEscalaInvalida() throws Exception {
		String escala = "kelvin";
		Double temperatura = 20d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		
		when(conversorTemperaturaFacade.converterTemperatura(
	    		any(ConversorTemperatura.class)))
		.thenReturn(null);
		
		doPost(request).
		andExpect(status().isPreconditionFailed());

	}
	
	@Test
	public void converterTemperaturaEscalaVazia() throws Exception {
		String escala = "";
		Double temperatura = 20d;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		
		when(conversorTemperaturaFacade.converterTemperatura(
	    		any(ConversorTemperatura.class)))
		.thenReturn(null);
		
		doPost(request).
		andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void converterTemperaturaTemperaturaVazia() throws Exception {
		String escala = "celcius";
		Double temperatura = null;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		
		when(conversorTemperaturaFacade.converterTemperatura(
	    		any(ConversorTemperatura.class)))
		.thenReturn(null);
		
		doPost(request).
		andExpect(status().isPreconditionFailed());
	}
	
	@Test
	public void converterTemperaturaCamposVazios() throws Exception {
		String escala = "";
		Double temperatura = null;
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		
		when(conversorTemperaturaFacade.converterTemperatura(
	    		any(ConversorTemperatura.class)))
		.thenReturn(null);
		
		doPost(request).
		andExpect(status().isPreconditionFailed());
	}
	@Test
	public void converterTemperaturaCamposValidos() throws Exception {		
		Double temperatura = 20d;
		String escala = "celcius";
		String resultado = "68.0 °F";
		
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		
		Historico historico  = new Historico();
		historico.setEscalaTemperatura(escala);
		historico.setResultadoConversao(resultado);
		historico.setTemperatura(temperatura);
		historico.setDataConsulta(LocalDateTime.now());
		
		String request = mapToJson(conversor);
	 
	    when(conversorTemperaturaFacade.converterTemperatura(
	    		any(ConversorTemperatura.class)))
		.thenReturn(resultado);
	    
	    when(historicoFacade.criarHistorico(
	    		any(ConversorTemperatura.class),
	    		anyString()))
	    		.thenReturn(historico);

	    doPost(request).
	    andExpect(status().isOk())
	    .andExpect(jsonPath("$.resultadoConversao").value("68.0 °F"));
		
	}
}
