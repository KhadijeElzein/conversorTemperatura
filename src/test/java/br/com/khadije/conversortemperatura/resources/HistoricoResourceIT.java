package br.com.khadije.conversortemperatura.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.khadije.conversortemperatura.ConversorTemperaturaApplication;
import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.repositories.HistoricoRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConversorTemperaturaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HistoricoResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	HistoricoRepository repository;
	
	private ResultActions doGet() throws Exception {
		return this.mockMvc.perform(get("/v1.0/historico")).andDo(print());
                
	}
	
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void getHistoricoResultadoValido() throws Exception {
		Historico historico = new Historico();
		Double temperatura = 20d;
		String escala = "celcius";
		String resultado = "68.0 °F";
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura(escala);
		historico.setResultadoConversao(resultado);
		historico.setTemperatura(temperatura);
		repository.save(historico);
		doGet().andExpect(status().isOk())
		.andExpect(jsonPath("$.[*]").isArray())
		.andExpect(jsonPath("$.[*].length()").value(Matchers.hasSize(1)))
        .andExpect(jsonPath("$.[*].escalaTemperatura").value("celcius"))
		.andExpect(jsonPath("$.[*].resultadoConversao").value("68.0 °F"))
		.andExpect(jsonPath("$.[*].temperatura").value(20d));				
		
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void getHistoricoResultadoVazio() throws Exception {		
	    doGet().
	    andExpect(status().isNoContent());		
	}
}