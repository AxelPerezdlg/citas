package mx.edu.utez.scct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mx.edu.utez.scct.entity.Rol;
import mx.edu.utez.scct.entity.Solicitante;
import mx.edu.utez.scct.entity.User;
import mx.edu.utez.scct.impl.CarreraServiceImpl;
import mx.edu.utez.scct.impl.RolServiceImpl;
import mx.edu.utez.scct.impl.SolicitanteServiceImpl;
import mx.edu.utez.scct.impl.UserServiceImpl;

@Controller
@RequestMapping(value = "/usuarios")
public class UserController {

    @Autowired
    CarreraServiceImpl carreraServiceImpl;

    @Autowired
    RolServiceImpl rolServiceImpl;

    @Autowired
    SolicitanteServiceImpl solicitanteServiceImpl;

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/lista", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("userList", userServiceImpl.listAllUsers());
        return "administrador/userList";
    }

    @RequestMapping(value = "/registroUsuario", method = RequestMethod.GET)
    public String crearUser(User user) {
        return "administrador/userCreate";
    }

    @RequestMapping(value = "/guardarUsuario", method = RequestMethod.POST)
    public String saveUser(User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/usuarios/lista";
    }

    @RequestMapping(value = "/registroSolicitante", method = RequestMethod.GET)
    public String crearSolicitante(User user, Solicitante solicitante, Model model) {
        model.addAttribute("carreraList", carreraServiceImpl.listAllCarreras());
        return "registro";
    }

    @RequestMapping(value = "/guardarSolicitante", method = RequestMethod.POST)
    public String saveSolicitante(User user, Solicitante solicitante) {
        User usuario = userServiceImpl.saveUser(user);
        solicitante.setUser(usuario);
        solicitanteServiceImpl.saveSolicitante(solicitante);
        return "redirect:/login";
    }

    @RequestMapping(value = "/detalleUsuario/{username}", method = RequestMethod.GET)
    public String detalle(@PathVariable("username") String username, Model model) {
        User usuario = userServiceImpl.findUserByUsername(username);
        model.addAttribute("user", usuario);
        for (Rol rol : usuario.getRols()) {
            if (rol.getName().equals("ROLE_USER")) {
                model.addAttribute("detalle", solicitanteServiceImpl.findByUser(usuario));
                return "administrador/solicitanteDetail";
            }
        }
        return "administrador/userDetail";
    }

    @RequestMapping(value = "/eliminarUsuario/{username}", method = RequestMethod.GET)
    public String eliminar(@PathVariable("username") String username) {
        User usuario = userServiceImpl.findUserByUsername(username);
        for (Rol rol : usuario.getRols()) {
            if (rol.getName().equals("ROLE_USER")) {
                Solicitante solicitante = solicitanteServiceImpl.findByUser(usuario);
                solicitanteServiceImpl.deleteSolicitante(solicitante.getMatricula());
            }
        }
        userServiceImpl.deleteUser(username);
        return "redirect:/usuarios/lista";
    }

    @RequestMapping(value = "/editarUsuario/{username}", method = RequestMethod.GET)
    public String editar(@PathVariable("username") String username, Model model, User user, Solicitante solicitante) {
        User usuario = userServiceImpl.findUserByUsername(username);
        model.addAttribute("user", usuario);
        for (Rol rol : usuario.getRols()) {
            if (rol.getName().equals("ROLE_USER")) {
                Solicitante solicitanteSend = solicitanteServiceImpl.findByUser(usuario);
                model.addAttribute("solicitante", solicitanteSend);
                model.addAttribute("carreraList", carreraServiceImpl.listAllCarreras());
                return "registro";
            }
        }
        return "administrador/userCreate";
    }
}
