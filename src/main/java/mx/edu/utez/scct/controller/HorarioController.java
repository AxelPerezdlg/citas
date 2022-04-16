package mx.edu.utez.scct.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.utez.scct.entity.Horario;
import mx.edu.utez.scct.impl.HorarioServiceImpl;
import mx.edu.utez.scct.impl.VentanillaServiceImpl;

@Controller
@RequestMapping(value = "/horarios")
public class HorarioController {

    @Autowired
    HorarioServiceImpl horarioServiceImpl;

    @Autowired
    VentanillaServiceImpl ventanillaServiceImpl;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listarHorarios(Model modelo) {
        modelo.addAttribute("listaHorarios", horarioServiceImpl.listarHorarios());
        return "horarios/listaHorarios";
    }

    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public String crearHorario(Horario horario, Model modelo, Authentication authentication) {
        modelo.addAttribute("listaVentanilla", ventanillaServiceImpl.listarVentanillas());
        modelo.addAttribute("username", authentication.getName());
        LocalDate now = LocalDate.now();
        modelo.addAttribute("now", now);
        return "horarios/formHorarios";
    }

    @RequestMapping(value = "/editar/{idhorarios}", method = RequestMethod.GET)
    public String editar(@PathVariable Long idhorarios, Model modelo, RedirectAttributes redirectAttributes) {
        Horario horario = horarioServiceImpl.findById(idhorarios);
        if (horario != null) {
            modelo.addAttribute("horario", horario);
            SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatterHour = new SimpleDateFormat("HH:mm");
            modelo.addAttribute("fechaDiaUpdate", formatterDate.format(horario.getDia()));
            modelo.addAttribute("horaInicioUpdate", formatterHour.format(horario.getHora_inicio()));
            modelo.addAttribute("horaFinUpdate", formatterHour.format(horario.getHora_fin()));
            modelo.addAttribute("listaVentanilla", ventanillaServiceImpl.listarVentanillas());
            return "horarios/editHorarios";
        }
        redirectAttributes.addFlashAttribute("msg_error", "Registro No Encontrado");
        return "redirect:/horarios/listar";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardarHorario(
            @RequestParam("fechaDia") String fechaDia,
            @RequestParam("horaInicio") String horaInicio,
            @RequestParam("horaFinal") String horaFinal, Horario horario, Model modelo,
            RedirectAttributes redirectAttributes, BindingResult result) throws ParseException {
        boolean isRegistro = true;
        if (horario.getIdhorarios() != null) {
            isRegistro = false;
        }
        int repeticiones = 1;
        if (horario.getRepeticiones() != null) {
            repeticiones = horario.getRepeticiones();
        }
        boolean respuesta = false;
        String successMessage = "";
        String errorMessage = "";
        String fechaTempo = fechaDia;
        int registrosExitosos = 0;
        for (int x = 0; x < repeticiones; x++) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String horaInicioRegistro = fechaTempo + " " + horaInicio + ":00";
            String horaFinalRegistro = fechaTempo + " " + horaFinal + ":00";
            Date fechaDiaRegistro = formatter.parse(fechaTempo + " 00:00");
            Date inicioH = formatter.parse(horaInicioRegistro);
            Date finH = formatter.parse(horaFinalRegistro);
            boolean bandera = true;
            if (isRegistro) { // Create
                List<Horario> listaDia = horarioServiceImpl.listarPorDiaAndVentanilla(fechaDiaRegistro,
                        horario.getVentanilla().getIdventanilla());
                for (int i = 0; i < listaDia.size(); i++) {
                    if (inicioH.getTime() >= listaDia.get(i).getHora_inicio().getTime()
                            || finH.getTime() <= listaDia.get(i).getHora_fin().getTime()) {
                        errorMessage = "La ventanilla " + listaDia.get(i).getVentanilla().getNumero()
                                + " no puede registrar entre las horas de otro horario";
                        bandera = false;
                    }
                }
                if (bandera) {
                    horario.setDia(fechaDiaRegistro);
                    horario.setHora_inicio(inicioH);
                    horario.setHora_fin(finH);
                    successMessage = "Registro Creado Exitosamente";
                }
            } else { // Update
                List<Horario> listaUpdate = horarioServiceImpl.listarPorDiaAndVentanillaAndHorario(fechaDiaRegistro,
                        horario.getVentanilla().getIdventanilla(), horario.getIdhorarios());
                for (int i = 0; i < listaUpdate.size(); i++) {
                    if (inicioH.getTime() >= listaUpdate.get(i).getHora_inicio().getTime()
                            || finH.getTime() <= listaUpdate.get(i).getHora_fin().getTime()) {
                        errorMessage = "No se puede poner fechas encimadas";
                        bandera = false;
                    }
                }
                if (bandera) {
                    horario.setDia(fechaDiaRegistro);
                    horario.setHora_inicio(inicioH);
                    horario.setHora_fin(finH);
                    horario.setRepeticiones(horario.getRepeticiones());
                    horario.setUser(horario.getUser());
                    successMessage = "Registro Modificado Exitosamente";
                }
            }
            if (bandera) {
                Horario horarioTempo = new Horario();
                horarioTempo.setDia(horario.getDia());
                horarioTempo.setHora_inicio(horario.getHora_inicio());
                horarioTempo.setHora_fin(horario.getHora_fin());
                horarioTempo.setRepeticiones(1);
                horarioTempo.setUser(horario.getUser());
                horarioTempo.setVentanilla(horario.getVentanilla());
                if (!isRegistro) {
                    horarioTempo.setIdhorarios(horario.getIdhorarios());
                }
                respuesta = horarioServiceImpl.guardarHorario(horarioTempo);
                if (!respuesta) {
                    errorMessage = "Registro Fallido por favor intente de nuevo";
                } else {
                    registrosExitosos = registrosExitosos + 1;
                }
                if (!isRegistro) {
                    break;
                }
            }
            SimpleDateFormat formatterTempo = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCambio = formatter.parse(fechaTempo + " 00:00");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaCambio);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date dateOneWeek = calendar.getTime();
            fechaTempo = formatterTempo.format(dateOneWeek);
        }
        if (registrosExitosos > 0) {
            if (isRegistro) {
                redirectAttributes.addFlashAttribute("msg_success",
                        successMessage + ", " + registrosExitosos + " Registros Exitosos");
            }
            return "redirect:/horarios/listar";
        } else {
            redirectAttributes.addFlashAttribute("msg_error", errorMessage);
            if (isRegistro) {
                return "redirect:/horarios/crear";
            } else {
                return "redirect:/horarios/editar/" + horario.getIdhorarios();
            }
        }
    }

    @RequestMapping(value = "/eliminar/{idhorarios}", method = RequestMethod.GET)
    public String eliminarHorario(@PathVariable long idhorarios, RedirectAttributes redirectAttributes) {
        boolean respuesta = horarioServiceImpl.eliminar(idhorarios);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");

        } else {
            redirectAttributes.addFlashAttribute("msg_error", "Eliminacion Fallida");

        }
        return "redirect:/horarios/listar";
    }
}
