package co.edu.iudigital.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

import co.edu.iudigital.app.dto.UsuarioDto;
import co.edu.iudigital.app.exception.RestException;
import co.edu.iudigital.app.model.Usuario;
import co.edu.iudigital.app.service.iface.IEmailService;
import co.edu.iudigital.app.service.iface.IUsuarioService;
import co.edu.iudigital.app.util.ConstUtil;
import co.edu.iudigital.app.util.Helper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/usuarios")
@Api(value = "/usuarios", tags = {"Usuarios"})
@SwaggerDefinition(tags = {
		@Tag(name = "Usuarios", description = "Gestion API Usuarios")
})
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IEmailService emailService;
	
	@ApiOperation(value = "Obtiene todos los usuarios",
			       produces = "application/json",
			       httpMethod = "GET")
	@GetMapping
	public ResponseEntity<List<UsuarioDto>> index() throws RestException{
		List<UsuarioDto> usuariosDto = usuarioService.listUsers();
		return ResponseEntity.ok().body(usuariosDto);
	}
	/**
	 * hasta aca el trabajo realizado el viernes 4 febrero (10PM)
	 */
	@ApiOperation(value = "Obtiene un usuario por Id",
			         response = Usuario.class,
			         produces ="aplication/json",
			         httpMethod = "GET")
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDto> show(@PathVariable Long id) throws RestException {
		Usuario usuario = usuarioService.listUsers(id);
		UsuarioDto usuarioDto = Helper.getMapValuesClient(usuario);
		return ResponseEntity.ok().body(usuarioDto);
	}
	
	
	@ApiOperation(value = "Da de alta a un usuario en la app",
	                response = Usuario.class,
	                produces = "aplication/json",
	                httpMethod = "POST")
	
	@PostMapping("/signup")
	public ResponseEntity<UsuarioDto> create(@RequestBody Usuario usuario) throws RestException{
		
		   	Usuario usuarioSaved = usuarioService.saveUser(usuario);
		   	//TODO: IMPLEMENTAR SPRING SECURITY
		   	if(Objects.nonNull(usuarioSaved)) {
		   		String mess = "Su usuario " + usuarioSaved.getUsername() +
		   				       "su contraseña" + usuarioSaved.getPassword();
		   		String to = usuarioSaved.getUsername();
		   		String subj = ConstUtil.ASUNTO_MESSAGE;
		   		boolean sent = emailService.sendEmail(mess, to, subj);
		   		if(!sent) {
		   			System.out.print("Error en el envió");
		   			// TODO COlocar log y un exception
		   		}
		   		
		   	}
		   	UsuarioDto usuarioDto = Helper.getMapValuesClient(usuarioSaved);
		   	return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
		   	
	}
	
	@PostMapping("/upload/{email}") //TODO : CAMBIAR A SPRING SECURITY
	public ResponseEntity<?> upload(
			@RequestParam("image") MultipartFile image,
			@PathVariable String email) throws RestException{
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = usuarioService.listByUsername(email);
		if(!image.isEmpty()) {
			String nombreImage = UUID.randomUUID().toString()
					.concat("_")
					.concat(image.getOriginalFilename().replace("", ""));
			
			Path path = Paths.get("uploads").resolve(nombreImage).toAbsolutePath();
			//subir_mifoto.jpa
			try {
				
				Files.copy(image.getInputStream(), path);
		
			}catch (IOException e) {
				response.put("Error IO", e.getMessage().concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String imageBDtos = usuario.getImage();
			//borrar imagen anterior
			if(Objects.nonNull(imageBDtos) && imageBDtos.length() > 0 && !imageBDtos.startsWith("http")) {
				Path pathAtras = Paths.get("uploads").resolve(imageBDtos).toAbsolutePath();
		    //subir nueva foto
				
				File imageFileAtras = pathAtras.toFile();
				if(imageFileAtras.exists() && imageFileAtras.canRead()) {
					imageFileAtras.delete();
				}
				
			}
			
			usuario.setImage(nombreImage);
			usuarioService.updateUser(usuario);
			response.put("usuario", usuario);
					
		
	}
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED);
	               

}
	
}
