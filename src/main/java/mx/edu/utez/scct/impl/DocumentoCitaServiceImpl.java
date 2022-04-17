package mx.edu.utez.scct.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.DocumentoCita;
import mx.edu.utez.scct.repository.DocumentoCitaRepository;
import mx.edu.utez.scct.service.DocumentoCitaService;

@Service
public class DocumentoCitaServiceImpl implements DocumentoCitaService{


    
    
    @Autowired
    private DocumentoCitaRepository documentoCitaRepository;

    @Override
    public DocumentoCita mostrar(long id) {
        Optional<DocumentoCita> optional = documentoCitaRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    

    

 


    

}
