package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.DTO.VentaDTO;
import com.utsem.farmacia.Service.VentaServie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta")
public class VentaController {
    @Autowired
    VentaServie ventaService;

    @PostMapping("buscarCodigo")
    public String buscaCodigo(HttpSession session, @RequestBody MedicamentoDTO medicamentoDTO) {
        if (session.getAttribute("rol") != null && (session.getAttribute("rol").equals("Administrador") || session.getAttribute("rol").equals("Empleado"))) {

            return ventaService.buscaProductoPorCodigo(session, medicamentoDTO);
        }
        return null;
    }
    @PostMapping("consultarVenta")
    public VentaDTO ConsultaVenta(HttpSession session) {
        return ventaService.consultaVenta(session);
    }

    @PostMapping("limpiarVenta")
    public String limpiaVenta(HttpSession session) {
        return ventaService.limpiaVenta(session);
    }
    @PostMapping("eliminarDetalle/{pos}")
    public String eliminaDetalle(HttpSession session,@PathVariable int pos) {
        return ventaService.eliminaDetalle(session,pos);
    }
    @PostMapping("realizarVenta")
    public String realizarVenta(HttpSession session) {
        return ventaService.realizarVenta(session);
    }

    @PostMapping("actualizarCantidad/{pos}&{cantidad}")
    public String actualizarCantidad(HttpSession session,@PathVariable int pos,@PathVariable int cantidad) {
        return ventaService.actualizarCantidad(session,pos,cantidad);
    }
}
