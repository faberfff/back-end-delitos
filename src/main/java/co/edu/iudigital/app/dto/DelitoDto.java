package co.edu.iudigital.app.dto;

import lombok.Builder;
import lombok.Data;

@Data  //getters and setters
@Builder  //construccion de un objeto
public class DelitoDto {
	
	private Long id;
	private String nombre;
	private String descripcion;

}
