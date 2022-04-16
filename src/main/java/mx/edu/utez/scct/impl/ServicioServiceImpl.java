package mx.edu.utez.scct.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Servicio;
import mx.edu.utez.scct.repository.ServicioRepository;
import mx.edu.utez.scct.service.ServicioService;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    ServicioRepository servicioRepository;

    @Override
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @Override
    public Boolean guardar(Servicio servicio) {
        Boolean response = false;
        if (servicioRepository.save(servicio) != null) {
            response = true;
        }
        return response;
    }

    @Override
    public Boolean eliminar(long id) {
        Boolean response = false;
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            response = true;
        }
        return response;
    }

    @Override
    public Servicio mostrar(long id) {
        Optional<Servicio> optional = servicioRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
