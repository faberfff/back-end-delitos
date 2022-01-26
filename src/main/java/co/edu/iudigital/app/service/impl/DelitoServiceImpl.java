package co.edu.iudigital.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.iudigital.app.dto.DelitoDto;
import co.edu.iudigital.app.model.Delito;
import co.edu.iudigital.app.service.iface.IDelitoService;

/**
 * 
 * @author Faber
 *
 */

@Service
public class DelitoServiceImpl implements IDelitoService {
	
	@Override
	public List<DelitoDto> findAll(){
		
		return null;	
	}
	
	@Override
	public Delito findById(Long id) {
		
		return null;
		
	}
	
	@Override
	public Delito save(Delito delito) {
		
		return null;
	}
	
	@Override
	public void delete(Long id) {
		
	}
	

}
