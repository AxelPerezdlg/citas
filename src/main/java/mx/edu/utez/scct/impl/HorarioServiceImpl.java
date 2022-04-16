package mx.edu.utez.scct.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Horario;
import mx.edu.utez.scct.repository.HorarioRepository;
import mx.edu.utez.scct.service.HorarioService;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    HorarioRepository horarioRepository;

    @Override
    public List<Horario> listarHorarios() {
        return horarioRepository.findAll();
    }

    @Override
    public Boolean guardarHorario(Horario horario) {
        Boolean response = false;
        if (horarioRepository.save(horario) != null) {
            response = true;
        }
        return response;
    }

    @Override
    public Horario findById(Long idhorarios) {
        Optional<Horario> optional = horarioRepository.findById(idhorarios);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public Boolean eliminar(Long idhorarios) {
        Boolean response = false;
        if (horarioRepository.existsById(idhorarios)) {
            horarioRepository.deleteById(idhorarios);
            response = true;
        }
        return response;
    }

    @Override
    public List<Horario> listarPorHora(String horaInicioRegistro, String horaFinalRegistro) {
        return horarioRepository.findByhora_inicioAndhora_fin(horaInicioRegistro, horaFinalRegistro);
    }

    @Override
    public List<Horario> listarPorDiaAndVentanilla(Date dia, Long idVentanilla) {
        return horarioRepository.findByDiaAndVentanilla(dia, idVentanilla);
    }

    @Override
    public List<Horario> listarPorDiaAndVentanillaAndHorario(Date dia, Long idVentanilla, Long idHorarios) {
        return horarioRepository.findByDiaAndVentanillaAndHorario(dia, idVentanilla, idHorarios);
    }

}
