package br.com.khadije.conversortemperatura.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.khadije.conversortemperatura.entities.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{
	
}