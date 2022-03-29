package com.totalshop.sap.commerce.exceptions;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorGenerico {
	private Date timestamp;
	private String mensaje;
	private String detalles;
	
	public ErrorGenerico() {}
	public ErrorGenerico(Date timestamp, String mensaje, String detalles) {
		this.timestamp = timestamp;
		this.mensaje = mensaje;
		this.detalles = detalles;
	}
}
