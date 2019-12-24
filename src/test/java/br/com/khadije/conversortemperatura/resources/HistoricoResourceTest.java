package br.com.khadije.conversortemperatura.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.exceptions.ApiExceptionHandler;
import br.com.khadije.conversortemperatura.facades.HistoricoFacade;

@TestInstance(Lifecycle.PER_CLASS)
public class HistoricoResourceTest {

private MockMvc mockMvc;
	
	@Mock
	private HistoricoFacade facade;
	
	@InjectMocks
	private HistoricoResource controller;
	


	@BeforeAll
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ApiExceptionHandler()).build();
	}

	private ResultActions doGet() throws Exception {
		return this.mockMvc.perform(get("/v1.0/historico")).andDo(print());
                
	}
	
	
	@Test
	public void getHistoricoResultadoValido() throws Exception {		
		Double temperatura = 20d;
		String escala = "celcius";
		String resultado = "68.0 °F";
			
		Historico historico  = new Historico();
		historico.setEscalaTemperatura(escala);
		historico.setResultadoConversao(resultado);
		historico.setTemperatura(temperatura);
		historico.setDataConsulta(LocalDateTime.now());
		List<Historico> historicos = new ArrayList<>();
		historicos.add(historico);
	    when(facade.getHistorico())
		.thenReturn(historicos);

	    doGet().
	    andExpect(status().isOk())
	    .andExpect(jsonPath("$.[*]").isArray())
		.andExpect(jsonPath("$.[*].length()").value(Matchers.hasSize(1)))
        .andExpect(jsonPath("$.[*].escalaTemperatura").value("celcius"))
		.andExpect(jsonPath("$.[*].resultadoConversao").value("68.0 °F"))
		.andExpect(jsonPath("$.[*].temperatura").value(20d));		
		
	}
	
	@Test
	public void getHistoricoResultadoVazio() throws Exception {		
	    when(facade.getHistorico())
		.thenReturn(null);

	    doGet().
	    andExpect(status().isNoContent());		
	}
}