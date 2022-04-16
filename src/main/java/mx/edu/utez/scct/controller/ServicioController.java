package mx.edu.utez.scct.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.scct.entity.Servicio;
import mx.edu.utez.scct.impl.DocumentoServiceImpl;
import mx.edu.utez.scct.impl.ServicioServiceImpl;

@Controller
@RequestMapping(value = "/servicios")
public class ServicioController {

    @Autowired
    ServicioServiceImpl servicioServiceImpl;

    @Autowired
    DocumentoServiceImpl documentoServiceImpl;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listarServicios(Model model) {
        model.addAttribute("listaServicios", servicioServiceImpl.listar());
        return "servicios/listarServicios";
    }

    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public String crearServicio(Servicio servicio, Model model) {
        model.addAttribute("listaDocumentos", documentoServiceImpl.listar());
        return "servicios/formServicio";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarServicio(@Valid @ModelAttribute("servicio") Servicio servicio, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        boolean respuesta = servicioServiceImpl.guardar(servicio);
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error : " + error.getDefaultMessage());
            }
            return "servicios/formServicio";
        }
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Registro de Servicio exitoso!");
            return "redirect:/servicios/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Registro de Servicio fallido!");
            return "redirect:/servicios/crear";
        }
    }

    //// METODO PARA PROBAR LA RELACION DE LAS ENTIDADES SERVICIO - DOCUMENTO
    /*
     * @GetMapping("/prueba")
     * public String guardarServicio() {
     * Documento documento = new Documento();
     * documento.setNombre("Acta de nacimiento");
     * documento.setEstado(true);
     * Servicio servicio1 = new Servicio();
     * servicio1.setNombre("Titulacion");
     * servicio1.setDescripcion("tramite para poder recibir titulo...");
     * servicio1.setPrecio(100.00);
     * servicio1.addDocumento(documento);
     * boolean respuesta = servicioService.guardar(servicio1);
     * return "prueba";
     * }
     */

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarServicio(@PathVariable long id, RedirectAttributes redirectAttributes) {
        boolean respuesta = servicioServiceImpl.eliminar(id);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Eliminacion exitosa!");
            return "redirect:/servicios/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Eliminación fallida!");
            return "redirect:/servicios/listar";
        }
    }

    @RequestMapping(value = "/mostrar/{id}", method = RequestMethod.GET)
    public String mostrarServicio(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Servicio servicio = servicioServiceImpl.mostrar(id);
        if (servicio != null) {
            modelo.addAttribute("servicio", servicio);
            return "servicios/mostrarServicio";
        }
        redirectAttributes.addFlashAttribute("msg_error", "consulta fallida");
        return "redirect:/servicios/listar";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Servicio servicio = servicioServiceImpl.mostrar(id);
        if (servicio != null) {
            modelo.addAttribute("listaDocumentos", documentoServiceImpl.listar());
            modelo.addAttribute("servicio", servicio);
            return "servicios/formServicio";
        }
        redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
        return "redirect:/Servicios/listar";
    }
}
