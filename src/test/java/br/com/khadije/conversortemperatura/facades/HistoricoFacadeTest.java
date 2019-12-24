package br.com.khadije.conversortemperatura.facades;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.repositories.HistoricoRepository;



@TestInstance(Lifecycle.PER_CLASS)
class HistoricoFacadeTest {

	@Mock
	private HistoricoRepository repository;
	
	@InjectMocks
	private HistoricoFacade facade;

	@BeforeAll
	private void setUpBeforeClass() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void criarHistoricoTemperaturaVazia() {
		Historico historico = new Historico();
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura("celcius");
		historico.setTemperatura(null);
		when(repository.save(historico))
		.thenReturn(null);
		Historico result = facade.criarHistorico(new ConversorTemperatura(null,"celcius"), null);
		assertEquals(null, result);
	}
	
	@Test
	public void criarHistoricoEscalaVazia() {
		Historico historico = new Historico();
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura("");
		historico.setTemperatura(20d);
		when(repository.save(historico))
		.thenReturn(null);
		Historico result = facade.criarHistorico(new ConversorTemperatura(20d,""), null);
		assertEquals(null, result);
	}
	
	@Test
	public void criarHistoricoDadosVazios() {
		Historico historico = new Historico();
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura("");
		historico.setTemperatura(null);
		when(repository.save(historico))
		.thenReturn(null);
		Historico result = facade.criarHistorico(new ConversorTemperatura(null,""), null);
		assertEquals(null, result);
	}
	
	
	@Test
	public void criarHistoricoEscalaInvalida() {
		Historico historico = new Historico();
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura("kelvin");
		historico.setTemperatura(20d);
		when(repository.save(historico))
		.thenReturn(null);
		Historico result = facade.criarHistorico(new ConversorTemperatura(20d,"kelvin"), null);
		assertEquals(null, result);
	}
	

	@Test
	public void criarHistoricoDadosValidos() {
		Historico historico = new Historico();
		String escala = "celcius";
		Double temperatura = 20d;
		String resultado = "68.0 °F";
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura(escala);
		historico.setTemperatura(temperatura);
		historico.setResultadoConversao(resultado);
		when(repository.save(any(Historico.class)))
		.thenReturn(historico);
		Historico result = facade.criarHistorico(new ConversorTemperatura(temperatura,escala), resultado);

		assertThat(result.getEscalaTemperatura(), equalTo("celcius"));
		assertThat(result.getResultadoConversao(), equalTo("68.0 °F"));
		assertThat(result.getTemperatura(), equalTo(20d));
	}
	
	@Test
	public void getHistoricoSemValores() {
		when(repository.findAll())
		.thenReturn(null);
		List<Historico> result = facade.getHistorico();
		assertEquals(null, result);
	}
	
	@Test
	public void getHistorico() {
		List<Historico> historicos = new ArrayList<>();
		Historico historico = new Historico();
		String escala = "celcius";
		Double temperatura = 20d;
		String resultado = "68.0 °F";
		historico.setDataConsulta(LocalDateTime.now());
		historico.setEscalaTemperatura(escala);
		historico.setTemperatura(temperatura);
		historico.setResultadoConversao(resultado);
		historicos.add(historico);
		when(repository.findAll())
		.thenReturn(historicos);
		List<Historico> result = facade.getHistorico();
		 assertThat(result.size(), equalTo(1));
		Historico resultHistorico = result.get(0);
		assertThat(resultHistorico.getEscalaTemperatura(), equalTo("celcius"));
		assertThat(resultHistorico.getResultadoConversao(), equalTo("68.0 °F"));
		assertThat(resultHistorico.getTemperatura(), equalTo(20d));
	}
	
}