package mx.edu.utez.scct.service;

import java.util.Date;
import java.util.List;

import mx.edu.utez.scct.entity.Cita;



public interface CitaService {
	List<Cita> listar();
	boolean guardar(Cita cita);
	boolean eliminar(long id);
	Cita mostrar(long id);
	List<Cita> listarPorDiaAndVentanilla(Date dia, Long idVentanilla);
	List<Cita> listarPorDiaAndVentanillaAndCita(Date dia, Long idVentanilla, Long idCita);
}
