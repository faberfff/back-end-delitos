package co.edu.iudigital.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.app.dto.DelitoDto;
import co.edu.iudigital.app.exception.RestException;
import co.edu.iudigital.app.model.Delito;
import co.edu.iudigital.app.service.iface.IDelitoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * 
 * @author Faber
 *
 */
@RestController
@RequestMapping("/delitos")

@Api(value = "/delitos", tags = {"Delitos"})
@SwaggerDefinition(tags = {
		@Tag(name = "Delitos", description = "Gestion API delitos")
})
/*TODO: COLOCAR ENDPOINTS CON RESPONSEENTITY<>*/
public class DelitoController {
	
	private static final Logger log = LoggerFactory.getLogger(DelitoController.class);

	@Autowired
	private IDelitoService delitoService;

	@ApiOperation(value = "Obtiene todos los delitos", 
			response = DelitoDto.class, 
			responseContainer = "List",
			produces = "aplication/json",
			httpMethod = "GET")

	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<DelitoDto> index() {
		log.info("Inicio index controller");
		return delitoService.findAll();

	}

	@ApiOperation(value = "Obtiene un delito por su id",
			response = Delito.class,
			produces = "aplication/json",
			httpMethod = "GET")

	@GetMapping("/code/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Delito show(@PathVariable final Long id) {// TODO: RETPRNA DELITODTO

		return delitoService.findById(id);

	}

	@ApiOperation(value = "Guarda un delito",
			response = Delito.class,
			produces = "aplication/json",
			httpMethod = "POST")

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public Delito create(@RequestBody @Valid Delito delito) { // TODO: RETPRNAR DELITODTO

		return delitoService.save(delito);

	}

	@ApiOperation(value = "Elimina un delito por su id",
			produces = "aplication/json",
			httpMethod = "POST")

	@DeleteMapping("/delito/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) throws RestException { // TODO: RETPRNA DELITODTO

	    delitoService.delete(id);
    
	}
}

