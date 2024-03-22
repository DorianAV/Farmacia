package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.*;
import com.utsem.farmacia.Model.*;
import com.utsem.farmacia.Repository.*;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class VentaServie {
    @Autowired
    MedicamentoRepository medicamentoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    FabricanteRepository fabricanteRepository;
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    DetalleVentaRepository detalleVentaRepository;
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    ModelMapper mapper;

    int n=1;
    public String buscaProductoPorCodigo(HttpSession session, @RequestBody MedicamentoDTO medicamentoDTO) {
        // buscamos producto por codigo
        Optional<Medicamento> med = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());
        if (med.isPresent()) {
            // Si no existe venta se crea
            if (session.getAttribute("miVenta") == null) {
                session.setAttribute("miVenta", new VentaDTO());
            }
            // Obtenemos venta de la sesion
            VentaDTO ventaDTO = (VentaDTO) session.getAttribute("miVenta");

            boolean existe = false;
            for (DetalleVentaDTO det : ventaDTO.getDetalles()) {
                if (det.getLote() != null && det.getLote().getMedicamento() != null &&
                        det.getLote().getMedicamento().getCodigoDeBarras() == med.get().getCodigoDeBarras()) {
                    existe = true;
                    if (det.getLote().getExistencia() > det.getCantidad()) {
                        det.setCantidad(det.getCantidad() + 1);
                        det.setSubtotal((double) det.getPrecio_unitario() * det.getCantidad());
                    } else {
                        return "No se puede agregar más de este producto, la cantidad excede la existencia.";
                    }
                    break;
                }
            }

            if (!existe) {
                DetalleVentaDTO detDTO = new DetalleVentaDTO();
                detDTO.setPrecio_unitario(med.get().getPrecio());
                detDTO.setCantidad(1);
                detDTO.setSubtotal((double) detDTO.getPrecio_unitario() * detDTO.getCantidad());

                List<LoteDTO> lotes = obtenerLoteConCaducidadProxima(med.get().getCodigoDeBarras());
                if (!lotes.isEmpty() && detDTO.getCantidad() < lotes.get(0).getExistencia()) {
                    detDTO.setLote(lotes.get(0));
                    detDTO.getLote().setMedicamento(mapper.map(med.get(), MedicamentoDTO.class));
                    detDTO.getLote().getMedicamento().setFabricanteDTO(mapper.map(med.get().getFabricante(), FabricanteDTO.class));
                    ventaDTO.getDetalles().add(detDTO);
                } else {
                    return "No hay suficiente existencia para agregar este producto.";
                }
            }

            double suma = 0;
            for (DetalleVentaDTO det : ventaDTO.getDetalles()) {
                suma += det.getSubtotal();
            }
            ventaDTO.setTotal(suma);
            return "Producto encontrado y agregado a la venta";
        } else {
            return "Producto no encontrado";
        }
    }


    public VentaDTO consultaVenta(HttpSession session) {
        if (session.getAttribute("miVenta") == null) {
            session.setAttribute("miVenta", new VentaDTO());
        }
        return (VentaDTO) session.getAttribute("miVenta");
    }

    public String limpiaVenta(HttpSession session) {
        session.setAttribute("miVenta", new VentaDTO());

        return "Ya no hay nada en la venta ";
    }

    public String eliminaDetalle(HttpSession session, int pos) {
        VentaDTO ventaDTO = (VentaDTO) session.getAttribute("miVenta");
        ventaDTO.getDetalles().remove(pos);
        ventaDTO.setTotal(ventaDTO.getDetalles().stream().mapToDouble(DetalleVentaDTO::getSubtotal).sum());
        return "Detalle Eliminado ";
    }

    public String realizarVenta(HttpSession session) {
        VentaDTO ventaDTO = (VentaDTO) session.getAttribute("miVenta");
        Ventas miVenta = new Ventas();
        miVenta.setTotal(ventaDTO.getTotal());
        Optional<Usuario> usu = usuarioRepository.findByUsuario(session.getAttribute("usuario").toString());
        miVenta.setUsuarios(usu.get());

        ventaRepository.save(miVenta);
        for (int i = 0; i < ventaDTO.getDetalles().size(); i++) {
            Detalle_ventas miDetalle = new Detalle_ventas();
            DetalleVentaDTO det = ventaDTO.getDetalles().get(i);
            miDetalle = mapper.map(det, Detalle_ventas.class);
            miDetalle.setVentas(miVenta);
            miDetalle.setLote(loteRepository.findByLote(det.getLote().getLote()));
            Lote lot = loteRepository.findByLote(miDetalle.getLote().getLote());
            lot.setExistencia(lot.getExistencia()-miDetalle.getCantidad());
            detalleVentaRepository.save(miDetalle);
        }
        ventaDTO.setVentaRealizada(true);
        return "Venta Realizada";

    }

    public String actualizarCantidad(HttpSession session, int pos, int cantidad) {
        VentaDTO ventaDTO = (VentaDTO) session.getAttribute("miVenta");
        DetalleVentaDTO det = ventaDTO.getDetalles().get(pos);
        if (det.getLote().getExistencia() <= pos) {
            if (det.getCantidad() + cantidad < 1) eliminaDetalle(session, pos);
            else det.setCantidad(det.getCantidad() + cantidad);
            det.setSubtotal((double) det.getPrecio_unitario() * det.getCantidad());
            Double suma = (double) 0;
            for (int i = 0; i < ventaDTO.getDetalles().size(); i++) suma += ventaDTO.getDetalles().get(i).getSubtotal();
            ventaDTO.setTotal(suma);
            return "Cantidad Actualizada";
        } else return "Cantidad Insifuciente";
    }

    public List<LoteDTO> obtenerLoteConCaducidadProxima(int codigo) {
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Calcular la fecha mínima con al menos 15 días restantes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DATE, 15);
        Date fechaMinima = calendar.getTime();

        // Buscar los lotes con la fecha de caducidad más próxima y al menos 15 días restantes antes de caducar
        List<Lote> lotes = loteRepository.findAllByCodigoDeBarrasOrderByFechaCaducidadDesc(codigo);

        // Crear una lista para almacenar los DTOs de los lotes encontrados
        List<LoteDTO> lotesDTO = new ArrayList<>();

        // Convertir cada lote encontrado a un DTO y agregarlo a la lista de DTOs si la fecha de caducidad es mayor a 15 días
        for (Lote lote : lotes) {
            if (lote.getFechaCaducidad().after(fechaMinima)) {
                lotesDTO.add(mapper.map(lote, LoteDTO.class));
            }
        }

        // Retornar la lista de lotes DTO
        return lotesDTO;
    }
}
