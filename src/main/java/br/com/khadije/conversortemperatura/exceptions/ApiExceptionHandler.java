package br.com.khadije.conversortemperatura.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<Object>  invalidFormatExceptionHandler(InvalidFormatException ex) {
		 ApiError apiException = new ApiError(HttpStatus.PRECONDITION_FAILED,"Tipo de dado inválido");
		 return new ResponseEntity<>(apiException, apiException.getStatus());
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object>  illegalArgumentExpcetionHandler(IllegalArgumentException ex) {
		 ApiError apiException = new ApiError(HttpStatus.PRECONDITION_FAILED,"Campo temperatura ou escala é nulo. Campos são obrigatórios");
		 return new ResponseEntity<>(apiException, apiException.getStatus());
	}
	
	@ExceptionHandler(EscalaException.class)
	public ResponseEntity<Object>  EscalaExpcetionHandler(EscalaException ex) {
		 ApiError apiException = new ApiError(HttpStatus.PRECONDITION_FAILED,"Valor "+ex.getEscala()+" encontrado no campo escala. Esperado celcius ou fahrenheit.");
		 return new ResponseEntity<>(apiException, apiException.getStatus());
	}
	
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<Object>  NoContentExpcetionHandler(NoContentException ex) {
		 ApiError apiException = new ApiError(HttpStatus.NO_CONTENT,"Nenhum Valor retornado.");
		 return new ResponseEntity<>(apiException, apiException.getStatus());
	}

}