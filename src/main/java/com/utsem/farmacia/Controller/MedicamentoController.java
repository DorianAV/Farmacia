package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.FiltroMedicametoDTO;
import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Service.MedicamentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicamento")
public class MedicamentoController {

    @Autowired
    MedicamentoService medicamentoService;

    @PostMapping("listar")
    public List<MedicamentoDTO> listar(HttpSession session) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.listar();		}
        return null;
    }
    @PostMapping("filtrar")
    public List<MedicamentoDTO> filtrar(HttpSession session, @RequestBody FiltroMedicametoDTO filtros) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.filtrar(filtros);		}
        return null;
    }
    @PostMapping("caducados")
    public List<LoteDTO> listarCaducados(HttpSession session){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.listarCaducados();		}
        return null;
    }
    @PostMapping("promos")
    public List<LoteDTO> proximosCaducar(HttpSession session){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.listarProximosCaducar();		}
        return null;
    }
    @PostMapping("registro")
    public String Registro(HttpSession session,@RequestBody MedicamentoDTO medicamentoDTO) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {

            return medicamentoService.registro(medicamentoDTO);}
        else return null;
    }
    @PostMapping("obtener")
    public MedicamentoDTO obtener(HttpSession session,@RequestBody MedicamentoDTO medicamentoDTO){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {

            return medicamentoService.obtener(medicamentoDTO);}
        else return null;
    }

    @PostMapping("actualizar")
    public String actualizar(HttpSession session,@RequestBody MedicamentoDTO medicamentoDTO){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {

            return medicamentoService.actualizar(medicamentoDTO);}
        else return null;
    }

}
