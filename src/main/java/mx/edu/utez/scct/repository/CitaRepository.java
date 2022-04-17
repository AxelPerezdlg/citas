package mx.edu.utez.scct.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.utez.scct.entity.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {


    @Query(value = "SELECT * FROM citas c WHERE c.fecha=:fecha and c.idventanilla=:idVentanilla", nativeQuery=true)
    List<Cita> findByDiaAndVentanilla(@Param("fecha") Date fecha, @Param("idVentanilla") Long idVentanilla);


    @Query(value = "SELECT * FROM citas c WHERE c.fecha=:fecha and c.idventanilla=:idVentanilla and c.id_cita!=:idCita", nativeQuery=true)
    List<Cita> findByDiaAndVentanillaAndCita(@Param("fecha") Date fecha, @Param("idVentanilla") Long idVentanilla,  
    @Param("idCita") Long idCita);

}
