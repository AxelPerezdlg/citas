package mx.edu.utez.scct.service;

import java.util.List;

import mx.edu.utez.scct.entity.Documento;

public interface DocumentoService {

    List<Documento> listar();

    Boolean guardar(Documento documento);

    Boolean eliminar(long id);

    Documento mostrar(long id);
}
