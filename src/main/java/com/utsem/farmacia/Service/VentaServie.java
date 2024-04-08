package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.*;
import com.utsem.farmacia.Model.*;
import com.utsem.farmacia.Repository.*;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

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


    public boolean loteExiste(List<DetalleVentaDTO> detalleslotesMedicamento, LoteDTO lote) {
        boolean a = false;
        for (int x = 0; x < detalleslotesMedicamento.size(); x++) {
            if (Objects.equals(detalleslotesMedicamento.get(x).getLote().getLote(), lote.getLote())) {
                a = true;
            }
        }
        return a;
    }

    public String buscaMedicamentoPorCodigo(HttpSession session, @RequestBody MedicamentoDTO medicamentoDTO) {
        Optional<Medicamento> med = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());
        if (med.isPresent()) {
            if (session.getAttribute("miVenta") == null) {
                session.setAttribute("miVenta", new VentaDTO());
            }
            Boolean existe = false;

            VentaDTO ventaDTO = (VentaDTO) session.getAttribute("miVenta");
            List<DetalleVentaDTO> detalleslotesMedicamento = new ArrayList<>();
            for (int i = 0; i < ventaDTO.getDetalles().size(); i++) {
                if (ventaDTO.getDetalles().get(i).getLote().getMedicamento().getCodigoDeBarras() == med.get().getCodigoDeBarras()) {
                    detalleslotesMedicamento.add(ventaDTO.getDetalles().get(i));
                    existe = true;
                }
            }
            List<LoteDTO> listLotes = obtenerLoteConCaducidadProxima(medicamentoDTO.getCodigoDeBarras());
            if (existe) {
                for (int n = 0; n < detalleslotesMedicamento.size(); n++) {

                    if (detalleslotesMedicamento.get(n).getLote().getExistencia() > detalleslotesMedicamento.get(n).getCantidad()) {
                        for (int i = 0; i < ventaDTO.getDetalles().size(); i++) {
                            DetalleVentaDTO det = ventaDTO.getDetalles().get(i);
                            if (det.getLote().getLote() == detalleslotesMedicamento.get(n).getLote().getLote()) {
                                det.setCantidad(detalleslotesMedicamento.get(n).getCantidad() + 1);
                                break;
                            }
                        }
                    } else {

                        if (listLotes.size()==n+1||!(listLotes.get(n).getExistencia()>0)) return "Ya no hay mas producto";
                        if (!loteExiste(detalleslotesMedicamento, listLotes.get(n + 1)) && loteExiste(detalleslotesMedicamento, listLotes.get(n))) {
                            DetalleVentaDTO detDTO = new DetalleVentaDTO();
                            detDTO.setPrecio_unitario(med.get().getPrecio());
                            detDTO.setCantidad(1);
                            detDTO.setSubtotal((double) detDTO.getPrecio_unitario() * detDTO.getCantidad());
                            detDTO.setLote(listLotes.get(n + 1));
                            detDTO.getLote().setMedicamento(listLotes.get(n + 1).getMedicamento());
                            ventaDTO.getDetalles().add(detDTO);
                            System.out.println("1 coso");
                            break;

                        }

                    }
                }
            }
            if (!existe) {
                if (!(obtenerLoteConCaducidadProxima(medicamentoDTO.getCodigoDeBarras()).get(0).getExistencia()>0)) return "Producto no encontrado";
                DetalleVentaDTO detDTO = new DetalleVentaDTO();
                detDTO.setPrecio_unitario(med.get().getPrecio());
                detDTO.setCantidad(1);
                detDTO.setSubtotal((double) detDTO.getPrecio_unitario() * detDTO.getCantidad());
                detDTO.setLote(obtenerLoteConCaducidadProxima(medicamentoDTO.getCodigoDeBarras()).get(0));
                detDTO.getLote().setMedicamento(obtenerLoteConCaducidadProxima(medicamentoDTO.getCodigoDeBarras()).get(0).getMedicamento());
                ventaDTO.getDetalles().add(detDTO);
            }

            Double suma = (double) 0;
            for (int i = 0; i < ventaDTO.getDetalles().size(); i++) suma += ventaDTO.getDetalles().get(i).getSubtotal();
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
            lot.setExistencia(lot.getExistencia() - miDetalle.getCantidad());
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
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DATE, 15);
        Date fechaMinima = calendar.getTime();
        List<Lote> lotes = loteRepository.findAllByCodigoDeBarrasOrderByFechaCaducidadDesc(codigo);
        List<LoteDTO> lotesDTO = new ArrayList<>();
        for (Lote lote : lotes) {
            if (lote.getFechaCaducidad() != null && lote.getFechaCaducidad().after(fechaMinima) && lote.isEstatus()) {
                lotesDTO.add(mapper.map(lote, LoteDTO.class));
            }
        }
        return lotesDTO;
    }

    public List<VentaDTO> listar() {
        return ventaRepository.findAll().stream().map(venta -> mapper.map(venta, VentaDTO.class)).collect(Collectors.toList());
    }
}
