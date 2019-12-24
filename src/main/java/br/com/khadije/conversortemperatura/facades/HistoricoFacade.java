package br.com.khadije.conversortemperatura.facades;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.khadije.conversortemperatura.entities.ConversorTemperatura;
import br.com.khadije.conversortemperatura.entities.Historico;
import br.com.khadije.conversortemperatura.repositories.HistoricoRepository;

@Service
@Component
public class HistoricoFacade {
	@Autowired
	HistoricoRepository repository;
	
	public Historico criarHistorico(ConversorTemperatura conversor, String result) { 
			Historico historico = new Historico();
			historico.setTemperatura(conversor.getTemperatura());
			historico.setEscalaTemperatura(conversor.getEscala().toLowerCase());
			LocalDateTime now = LocalDateTime.now();
			historico.setDataConsulta(now);
			historico.setResultadoConversao(result);
			Historico resp =  repository.save(historico);
			return resp;
	}

	public List<Historico> getHistorico() {
		List<Historico> historico = repository.findAll();
		if(!historico.isEmpty())
			return historico;
		return null;
	}
	
}
