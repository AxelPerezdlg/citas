package mx.edu.utez.scct.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Cita;
import mx.edu.utez.scct.repository.CitaRepository;
import mx.edu.utez.scct.service.CitaService;

@Service
public class CitaServiceImpl  implements CitaService{
	@Autowired
	CitaRepository citaRepository;
	
	public List<Cita> listar() {
		return citaRepository.findAll();
		
	}
	public boolean guardar(Cita cita) {
		try{
			citaRepository.save(cita);
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean eliminar(long id) {
		boolean existe = citaRepository.existsById(id);
		if(existe) {
			citaRepository.deleteById(id);
			return !citaRepository.existsById(id);
		}else {
			return false;
		}
	}
	public Cita mostrar(long id) {
		Optional<Cita> optional = citaRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	@Override
	public List<Cita> listarPorDiaAndVentanilla(Date dia, Long idVentanilla) {
		return citaRepository.findByDiaAndVentanilla(dia, idVentanilla);
	}
	@Override
	public List<Cita> listarPorDiaAndVentanillaAndCita(Date dia, Long idVentanilla, Long idCita) {
		return citaRepository.findByDiaAndVentanillaAndCita(dia, idVentanilla, idCita);
		
	}

}
