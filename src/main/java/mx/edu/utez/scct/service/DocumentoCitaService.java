package mx.edu.utez.scct.service;




import java.util.List;

import mx.edu.utez.scct.entity.Cita;
import mx.edu.utez.scct.entity.DocumentoCita;


public interface DocumentoCitaService {
	
    DocumentoCita mostrar(long id);
    boolean guardar (DocumentoCita documentoCita);

    List<DocumentoCita> findByidCita(Cita cita);

}
