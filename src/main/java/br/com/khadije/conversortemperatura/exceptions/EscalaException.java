package br.com.khadije.conversortemperatura.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class EscalaException extends Exception {
	private static final long serialVersionUID = 1L;
	private String escala;
   

    public EscalaException(Throwable throwable) {
        super(throwable);
    }


    public EscalaException(String escala) {
        super();
        this.escala = escala;
    }
}