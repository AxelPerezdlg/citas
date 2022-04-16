package mx.edu.utez.scct.service;

import java.util.List;

import mx.edu.utez.scct.entity.Servicio;

public interface ServicioService {

    List<Servicio> listar();

    Boolean guardar(Servicio servicio);

    Boolean eliminar(long id);

    Servicio mostrar(long id);
}
