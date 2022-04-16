package mx.edu.utez.scct.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Documento;
import mx.edu.utez.scct.repository.DocumentoRepository;
import mx.edu.utez.scct.service.DocumentoService;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    DocumentoRepository documentoRepository;

    @Override
    public List<Documento> listar() {
        return documentoRepository.findAll();
    }

    @Override
    public Boolean guardar(Documento documento) {
        Boolean response = false;
        if (documentoRepository.save(documento) != null) {
            response = true;
        }
        return response;
    }

    @Override
    public Boolean eliminar(long id) {
        Boolean response = false;
        if (documentoRepository.existsById(id)) {
            documentoRepository.deleteById(id);
            response = true;
        }
        return response;
    }

    @Override
    public Documento mostrar(long id) {
        Optional<Documento> optional = documentoRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

}
