package com.totalshop.sap.commerce.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorGenerico error = new ErrorGenerico(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorGenerico errorDetails = new ErrorGenerico(new Date(), "Datos de Entrada Inválidos", ex.getBindingResult().toString());
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ServiceException.class)
	public final ResponseEntity<Object> handleServiceExeption(Exception ex, WebRequest request) {
		ErrorGenerico error = new ErrorGenerico(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}
	
	private String getDetalleError(BindingResult error) {
		FieldError field =  error.getFieldError();
		return field.getField()+" - "+field.getDefaultMessage();
	}
}
