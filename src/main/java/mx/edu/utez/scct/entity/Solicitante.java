package mx.edu.utez.scct.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_solicitante")
public class Solicitante {

    @Id
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "telefono")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
