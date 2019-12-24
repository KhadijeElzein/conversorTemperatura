package br.com.khadije.conversortemperatura.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversorTemperatura {
	private Double temperatura; 
	private String escala;
}
