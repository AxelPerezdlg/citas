package mx.edu.utez.scct.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.utez.scct.entity.Rol;
import mx.edu.utez.scct.repository.RolRepository;
import mx.edu.utez.scct.service.RolService;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    public List<Rol> listAllRoles() {
        return rolRepository.findAll();
    }

}
