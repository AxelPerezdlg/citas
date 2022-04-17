package mx.edu.utez.scct.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "documentos_citas")
public class DocumentoCita {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDocumentoCita;
	
	@Lob
	@Column(name = "documento", nullable = false)
	private byte[] archivo;
	
	@ManyToOne
    @JoinColumn(name = "idCita", nullable = false)
    private Cita cita;
	 

	 
	 
	public DocumentoCita() {
		super();
	}
	

	public Long getIdDocumentoCita() {
		return idDocumentoCita;
	}


	public void setIdDocumentoCita(Long idDocumentoCita) {
		this.idDocumentoCita = idDocumentoCita;
	}


	public byte[] getArchivo() {
		return archivo;
	}


	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}


	public Cita getCita() {
		return cita;
	}


	public void setCita(Cita cita) {
		this.cita = cita;
	}



	
	
	 
	 
	
	

}
