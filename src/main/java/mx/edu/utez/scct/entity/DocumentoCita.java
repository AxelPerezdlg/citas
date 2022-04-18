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
	

	@Column(name = "archivo", nullable = false)
	private String archivo;
	
	@ManyToOne
    @JoinColumn(name = "cita", nullable = false)
    private Cita cita;
	 
	@ManyToOne
    @JoinColumn(name = "documento", nullable = false)
    private Documento documento;
	 
	 
	 
	public DocumentoCita() {
		super();
	}
	
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}




	public Long getIdDocumentoCita() {
		return idDocumentoCita;
	}


	public void setIdDocumentoCita(Long idDocumentoCita) {
		this.idDocumentoCita = idDocumentoCita;
	}


	public String getArchivo() {
		return archivo;
	}


	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}


	public Cita getCita() {
		return cita;
	}


	public void setCita(Cita cita) {
		this.cita = cita;
	}



	
	
	 
	 
	
	

}
