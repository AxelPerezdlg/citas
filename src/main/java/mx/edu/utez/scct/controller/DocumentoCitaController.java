package mx.edu.utez.scct.controller;



import java.io.IOException;

import java.util.Base64;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.scct.entity.Cita;

import mx.edu.utez.scct.entity.DocumentoCita;
import mx.edu.utez.scct.service.CitaService;
import mx.edu.utez.scct.service.DocumentoCitaService;



@Controller
@RequestMapping(value = "/documentosCitas")
public class DocumentoCitaController {

    @Autowired
	private CitaService citaService;

    @Autowired
	private DocumentoCitaService documentoCitaService;

    @GetMapping("/documentacion/{idCita}")
	public String crearCita(@PathVariable long idCita, DocumentoCita documentoCita, Model modelo, RedirectAttributes redirectAttributes) {
		Cita citaObtener = citaService.mostrar(idCita);
        
        if(citaObtener != null) {
			modelo.addAttribute("idCita", citaObtener.getIdCita());
			return "citas/formDocumentacion";
			
		}
		
		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
		return "redirect:/citas/listar";


		
	}


    @PostMapping("/guardar")
	public String guardarDocumento(@RequestParam("cita") Long cita,@RequestParam("archivo") MultipartFile multipartFile, DocumentoCita documentoCita, Model modelo, RedirectAttributes redirectAttributes) throws IOException{
		Cita citaObtener = citaService.mostrar(cita);
        documentoCita.setCita(citaObtener);
        if(true){
            
            redirectAttributes.addFlashAttribute("msg_error", "Registro exitoso");
            return "redirect:/citas/listar";
        }
        redirectAttributes.addFlashAttribute("msg_error", "Errro");
		return "redirect:/documentosCitas/documentacion/"+cita;
        
	}


    

}
