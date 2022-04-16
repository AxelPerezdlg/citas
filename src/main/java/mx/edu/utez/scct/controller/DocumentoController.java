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

import mx.edu.utez.scct.entity.Documento;
import mx.edu.utez.scct.impl.DocumentoServiceImpl;

@Controller
@RequestMapping(value = "/documentos")
public class DocumentoController {

    @Autowired
    DocumentoServiceImpl documentoServiceImpl;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listarServicios(Model model) {
        model.addAttribute("listaDocumentos", documentoServiceImpl.listar());
        return "documentos/listarDocumentos";
    }

    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public String crearDocumento(Documento documento, Model model) {
        return "documentos/formDocumento";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarServicio(@Valid @ModelAttribute("documento") Documento documento, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        boolean respuesta = documentoServiceImpl.guardar(documento);
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error : " + error.getDefaultMessage());
            }
            return "documentos/formDocumento";
        }
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Registro de Documento exitoso!");
            return "redirect:/documentos/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Registro de Documento fallido!");
            return "redirect:/documentos/crear";
        }
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarServicio(@PathVariable long id, RedirectAttributes redirectAttributes) {
        boolean respuesta = documentoServiceImpl.eliminar(id);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Eliminacion exitosa!");
            return "redirect:/documentos/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Eliminación fallida!");
            return "redirect:/documentos/listar";
        }
    }

    @RequestMapping(value = "/mostrar/{id}", method = RequestMethod.GET)
    public String mostrarServicio(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Documento documento = documentoServiceImpl.mostrar(id);
        if (documento != null) {
            modelo.addAttribute("documento", documento);
            return "documentos/mostrarDocumento";
        }
        redirectAttributes.addFlashAttribute("msg_error", "consulta fallida");
        return "redirect:/documentos/listar";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Documento documento = documentoServiceImpl.mostrar(id);
        if (documento != null) {
            modelo.addAttribute("documento", documento);
            return "documentos/formDocumento";
        }
        redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
        return "redirect:/documentos/listar";
    }
}