package br.com.khadije.conversortemperatura.resources;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.khadije.conversortemperatura.ConversorTemperaturaApplication;
import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.repositories.HistoricoRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConversorTemperaturaApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ConversorTemperaturaResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	HistoricoRepository repository;
	
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
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void converterTemperaturaCamposValidos() throws Exception {
		Double temperatura = 20d;
		String escala = "celcius";
		String resultado = "68.0 °F";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request).andExpect(status().isOk())
        .andExpect(jsonPath("$.resultadoConversao").value(resultado));
		
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void salvarHistoricoCamposValidos() throws Exception {
		Double temperatura = 20d;
		String escala = "celcius";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request);
		List<Historico> historicos = repository.findAll();
		Historico historico = historicos.get(0);
		assertThat(historicos.size(), Matchers.equalTo(1));
		assertThat(historico.getEscalaTemperatura(),Matchers.equalTo("celcius"));
		assertThat(historico.getTemperatura(), Matchers.equalTo(20d));
		assertThat(historico.getResultadoConversao(),Matchers.comparesEqualTo("68.0 °F"));
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void converterTemperaturaEscalaInvalida() throws Exception {
		Double temperatura = 20d;
		String escala = "kelvin";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request).andExpect(status().isPreconditionFailed());
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void converterTemperaturaTemperaturaVazia() throws Exception {
		Double temperatura = null;
		String escala = "celcius";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request).andExpect(status().isPreconditionFailed());
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void converterTemperaturaEscalaVazia() throws Exception {
		Double temperatura = 20d;
		String escala = "";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request).andExpect(status().isPreconditionFailed());
	}
	
	@Test
	@Sql(scripts = "classpath:historico.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void converterTemperaturaDadosVazios() throws Exception {
		Double temperatura = null;
		String escala = "";
		ConversorTemperatura conversor = new ConversorTemperatura(temperatura,escala);
		String request = mapToJson(conversor);
		doPost(request).andExpect(status().isPreconditionFailed());
	}
}