package mx.edu.utez.scct.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Carrera;
import mx.edu.utez.scct.repository.CarreraRepository;
import mx.edu.utez.scct.service.CarreraService;

@Service
public class CarreraServiceImpl implements CarreraService {

    @Autowired
    CarreraRepository carreraRepository;

    @Override
    public List<Carrera> listAllCarreras() {
        return carreraRepository.findAll();
    }

}
