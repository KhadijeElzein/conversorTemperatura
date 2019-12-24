package br.com.khadije.conversortemperatura.entities;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Immutable
@Table(name = "historico")
public class Historico implements Serializable{

	private static final long serialVersionUID = -5524697044712805424L;
	
	@Id
	@EqualsAndHashCode.Include
	@Column(name="id_historico")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column(name = "temperatura")
	private Double temperatura;
	
	@Column(name = "escala")
	private String escalaTemperatura;
	
	@Column(name = "data_consulta")
	private LocalDateTime dataConsulta;	
	
	@Column(name = "resultado")
	private String resultadoConversao;	

}