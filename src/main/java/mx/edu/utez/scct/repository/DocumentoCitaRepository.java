package mx.edu.utez.scct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.utez.scct.entity.Cita;
import mx.edu.utez.scct.entity.DocumentoCita;

@Repository
public interface DocumentoCitaRepository extends JpaRepository<DocumentoCita, Long> {


    List<DocumentoCita> findByCita(Cita cita);

}
