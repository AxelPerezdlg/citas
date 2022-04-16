package mx.edu.utez.scct.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumento;

    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(nullable = false, length = 45)
    private String nombre;

    @NotNull(message = "Selecciona un estado valido")
    @Column(nullable = false)
    private Boolean estado;

    @ManyToMany(mappedBy = "documento")
    private Set<Servicio> servicio;

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Set<Servicio> getServicio() {
        return servicio;
    }

    public void setServicio(Set<Servicio> servicio) {
        this.servicio = servicio;
    }

}
