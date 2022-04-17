package mx.edu.utez.scct.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "citas")
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCita;

	@Column(name = "fecha", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@Column(name = "hora", nullable = false)
    private String hora;
	
	@ManyToOne
    @JoinColumn(name = "idventanilla", nullable = false)
    private Ventanilla ventanilla;

	@ManyToOne
    @JoinColumn(name = "idusuarios", nullable = false)
    private User user;

	@ManyToOne
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

	@Column(nullable = false)
	private boolean estado;
	
	public Cita() {
		super();
	}

	
	
	public Servicio getServicio() {
		return servicio;
	}



	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	
	

	public Long getIdCita() {
		return idCita;
	}


	public void setIdCita(Long idCita) {
		this.idCita = idCita;
	}
	

	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public Ventanilla getVentanilla() {
		return ventanilla;
	}


	public void setVentanilla(Ventanilla ventanilla) {
		this.ventanilla = ventanilla;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	

	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	


	
}
