package mx.edu.utez.scct.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Ventanilla;
import mx.edu.utez.scct.repository.VentanillaRepository;
import mx.edu.utez.scct.service.VentanillaService;

@Service
public class VentanillaServiceImpl implements VentanillaService {

    @Autowired
    VentanillaRepository ventanillaRepository;

    @Override
    public List<Ventanilla> listarVentanillas() {
        return ventanillaRepository.findAll();
    }

}
