package mx.edu.utez.scct.service;

import java.util.List;

import mx.edu.utez.scct.entity.Transaccion;

public interface TransaccionService {

    List<Transaccion> listar();

    Boolean guardar(Transaccion transaccion);

    Boolean eliminar(long id);

    Transaccion mostrar(long id);
}
