package mx.edu.utez.scct.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Solicitante;
import mx.edu.utez.scct.entity.User;
import mx.edu.utez.scct.repository.SolicitanteRepository;
import mx.edu.utez.scct.service.SolicitanteService;

@Service
public class SolicitanteServiceImpl implements SolicitanteService {

    @Autowired
    SolicitanteRepository solicitanteRepository;

    @Override
    public List<Solicitante> listAllSolicitantes() {
        return solicitanteRepository.findAll();
    }

    @Override
    public Solicitante findSolicitanteByMatricula(String matricula) {
        Optional<Solicitante> solicitante = solicitanteRepository.findById(matricula);
        if (solicitante.isPresent()) {
            return solicitante.get();
        }
        return null;
    }

    @Override
    public Boolean saveSolicitante(Solicitante solicitante) {
        Boolean response = false;
        if (solicitanteRepository.save(solicitante) != null) {
            response = true;
        }
        return response;
    }

    @Override
    public Boolean deleteSolicitante(String matricula) {
        Boolean response = false;
        if (solicitanteRepository.existsById(matricula)) {
            solicitanteRepository.deleteById(matricula);
            response = true;
        }
        return response;
    }

    @Override
    public Solicitante findByUser(User user) {
        return solicitanteRepository.findByUser(user);
    }

}
