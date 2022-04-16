package mx.edu.utez.scct.service;

import java.util.Date;
import java.util.List;

import mx.edu.utez.scct.entity.Horario;

public interface HorarioService {
    List<Horario> listarHorarios();

    Boolean guardarHorario(Horario horario);

    Horario findById(Long idhorarios);

    Boolean eliminar(Long idhorarios);

    List<Horario> listarPorHora(String horaInicioRegistro, String horaFinalRegistro);

    List<Horario> listarPorDiaAndVentanilla(Date dia, Long idVentanilla);

    List<Horario> listarPorDiaAndVentanillaAndHorario(Date dia, Long idVentanilla, Long idHorarios);
}
