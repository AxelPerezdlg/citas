package mx.edu.utez.scct.service;

import java.util.List;

import mx.edu.utez.scct.entity.Solicitante;
import mx.edu.utez.scct.entity.User;

public interface SolicitanteService {

    List<Solicitante> listAllSolicitantes();

    Solicitante findSolicitanteByMatricula(String matricula);

    Boolean saveSolicitante(Solicitante solicitante);

    Boolean deleteSolicitante(String matricula);

    Solicitante findByUser(User user);
}
