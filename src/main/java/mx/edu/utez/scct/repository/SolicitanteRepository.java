package mx.edu.utez.scct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.scct.entity.Solicitante;
import mx.edu.utez.scct.entity.User;

@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, String> {
    Solicitante findByUser(User user);
}
