package mx.edu.utez.scct.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Transaccion;
import mx.edu.utez.scct.repository.TransaccionRepository;
import mx.edu.utez.scct.service.TransaccionService;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Override
    public List<Transaccion> listar() {
        return transaccionRepository.findAll();
    }

    @Override
    public Boolean guardar(Transaccion transaccion) {
        Boolean response = false;
        if (transaccionRepository.save(transaccion) != null) {
            response = true;
        }
        return response;
    }

    @Override
    public Boolean eliminar(long id) {
        Boolean response = false;
        if (transaccionRepository.existsById(id)) {
            transaccionRepository.deleteById(id);
            response = true;
        }
        return response;
    }

    @Override
    public Transaccion mostrar(long id) {
        Optional<Transaccion> optional = transaccionRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
