package mx.edu.utez.scct.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.scct.entity.Cita;
import mx.edu.utez.scct.impl.CitaServiceImpl;

import mx.edu.utez.scct.service.ServicioService;
import mx.edu.utez.scct.service.VentanillaService;



@Controller
@RequestMapping(value = "/citas")
public class CitaController {

	@Autowired
	private CitaServiceImpl citaService;

	@Autowired
	private VentanillaService ventanillaService;

	@Autowired
	private ServicioService servicioService;
	
	@GetMapping(value="/crear")
	public String crearCita(Cita cita, Model modelo,  Authentication authentication) {
		modelo.addAttribute("listaCitas", citaService.listar());
		LocalDate now = LocalDate.now();
		modelo.addAttribute("now", now);
		modelo.addAttribute("listaVentanilla", ventanillaService.listarVentanillas());
		modelo.addAttribute("listaServicios", servicioService.listar());
		modelo.addAttribute("username", authentication.getName());
		return "citas/formCita";
	}

	@GetMapping(value = { "/listar"})
	public String listarCitas( Model model ,RedirectAttributes redirectAttributes) {
	   List<Cita> listaCitas = citaService.listar();
	   model.addAttribute("listaCitas", listaCitas);
	   return "citas/listarCitas";
	}
	
	@GetMapping("/mostrar/{id}")
	public String mostrarCita(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
		Cita cita = citaService.mostrar(id);
		if(cita != null) {
			modelo.addAttribute("cita", cita);
			return "citas/mostrarCita";
		}
		redirectAttributes.addFlashAttribute("msg_error", "consulta fallida");
		return "redirect:/citas/listar";
		
		
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
		Cita cita = citaService.mostrar(id);
		if(cita != null) {
			modelo.addAttribute("cita", cita);
			LocalDate now = LocalDate.now();
			modelo.addAttribute("now", now);
			modelo.addAttribute("estadoUpdate", cita.isEstado());
			modelo.addAttribute("listaVentanilla", ventanillaService.listarVentanillas());
			modelo.addAttribute("listaServicios", servicioService.listar());
			return "citas/editarCita";
			
		}
		
		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
		return "redirect:/citas/listar";
	}
	
	@PostMapping("/guardar")
	public String guardarCita(@ModelAttribute("cita") Cita cita, @RequestParam("hora") String hora, Model modelo, RedirectAttributes redirectAttributes){
		boolean respuesta = false;
		String successMessage = "";
		String errorMessage = "";
		boolean bandera = true;
		

		if (cita.getIdCita() == null) { // Create
			
			List<Cita> listaDia = citaService.listarPorDiaAndVentanilla(cita.getFecha(), cita.getVentanilla().getIdventanilla());
			for (int i = 0; i < listaDia.size(); i++) {
				if (hora.equals(listaDia.get(i).getHora())) {
					errorMessage = "No se puede registrar una cita a la ventanilla " + listaDia.get(i).getVentanilla().getNumero()+ " por que ya cuenta con una";
					bandera = false;
				}
			}
			if (bandera) {
				cita.setHora(hora);
				successMessage = "Cita Registrada Exitosamente";
			}


		} else { // Update
			List<Cita> listaDia = citaService.listarPorDiaAndVentanillaAndCita(cita.getFecha(), cita.getVentanilla().getIdventanilla(), cita.getIdCita());
			for (int i = 0; i < listaDia.size(); i++) {
				if (hora.equals(listaDia.get(i).getHora())) {
					errorMessage = "No se puede modificar una cita a la ventanilla " + listaDia.get(i).getVentanilla().getNumero()+ " por que ya cuenta con una";
					bandera = false;
				}
			}
			if (bandera) {
				cita.setFecha(cita.getFecha());
				cita.setHora(cita.getHora());
				cita.setEstado(cita.isEstado());
				cita.setVentanilla(cita.getVentanilla());
				successMessage = "Cita Modificada Exitosamente";
			}
			
			
		}
		
		if(bandera){
			respuesta = citaService.guardar(cita);
			if (!respuesta) {
				errorMessage = "Registro Fallido por favor intente de nuevo";
			}
		}
		
		


		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", successMessage);
			return "redirect:/citas/listar";
		}else {
			redirectAttributes.addFlashAttribute("msg_error", errorMessage);
			if(cita.getIdCita() == null){
				return "redirect:/citas/crear";
			}
			return "redirect:/citas/editar/"+cita.getIdCita();
		}
		
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarCita(@PathVariable long id, RedirectAttributes redirectAttributes) {
		
		boolean respuesta = citaService.eliminar(id);
		
		if(respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
			return "redirect:/citas/listar";
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "eliminación fallida");
			return "redirect:/citas/listar";
		}
	}}