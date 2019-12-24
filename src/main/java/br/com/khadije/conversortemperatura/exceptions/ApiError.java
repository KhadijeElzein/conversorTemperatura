package br.com.khadije.conversortemperatura.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class ApiError {
	   private HttpStatus status;
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime data;
	   private String mensagem;

	   private ApiError() {
	       data = LocalDateTime.now();
	   }
	   
	   ApiError(HttpStatus status, String mensagem) {
	       this();
	       this.status = status;
	       this.mensagem = mensagem;
	   }
}
