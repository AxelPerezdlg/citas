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

import mx.edu.utez.scct.entity.Transaccion;
import mx.edu.utez.scct.impl.TransaccionServiceImpl;

@Controller
@RequestMapping(value = "/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionServiceImpl transaccionServiceImpl;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listarServicios(Model model) {
        model.addAttribute("listaTransacciones", transaccionServiceImpl.listar());
        return "transacciones/listarTransacciones";
    }

    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public String crearTransaccion(Transaccion transaccion) {
        return "transacciones/formTransaccion";
    }

    @RequestMapping(value = "/crearPago", method = RequestMethod.GET)
    public String crearPago(Transaccion transaccion, Model model) {
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setEstado(true);
        transaccion1.setIdCita(1);
        transaccion1.setMonto(100.00);
        transaccionServiceImpl.guardar(transaccion1);
        return "transacciones/wizardPago";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarTransaccion(@Valid @ModelAttribute("transaccion") Transaccion transaccion,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        boolean respuesta = transaccionServiceImpl.guardar(transaccion);
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Error : " + error.getDefaultMessage());
            }
            return "transacciones/formTransaccion";
        }
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Registro de Documento exitoso!");
            return "redirect:/transacciones/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Registro de Documento fallido!");
            return "redirect:/transacciones/crear";
        }
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarTransaccion(@PathVariable long id, RedirectAttributes redirectAttributes) {
        boolean respuesta = transaccionServiceImpl.eliminar(id);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "¡Eliminacion exitosa!");
            return "redirect:/transacciones/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", "¡Eliminación fallida!");
            return "redirect:/transacciones/listar";
        }
    }

    @RequestMapping(value = "/mostrar/{id}", method = RequestMethod.GET)
    public String mostrarTransaccion(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Transaccion transaccion = transaccionServiceImpl.mostrar(id);
        if (transaccion != null) {
            modelo.addAttribute("transaccion", transaccion);
            return "transacciones/mostrarTransaccion";
        }
        redirectAttributes.addFlashAttribute("msg_error", "consulta fallida");
        return "redirect:/transaccion/listar";
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Transaccion transaccion = transaccionServiceImpl.mostrar(id);
        if (transaccion != null) {
            modelo.addAttribute("transaccion", transaccion);

            return "transacciones/formTransaccion";

        }

        redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
        return "redirect:/transacciones/listar";
    }

}