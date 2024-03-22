package com.utsem.farmacia.DTO;

import com.utsem.farmacia.Model.Usuario;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class VentaDTO {
    private LocalDateTime fecha;
    private Double total=0d;
    private Usuario usuarios;
    private UUID uuid;
    private Boolean ventaRealizada=false;

    ArrayList<DetalleVentaDTO> detalles = new ArrayList<>();

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Boolean getVentaRealizada() {
        return ventaRealizada;
    }

    public void setVentaRealizada(Boolean ventaRealizada) {
        this.ventaRealizada = ventaRealizada;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Usuario getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario usuarios) {
        this.usuarios = usuarios;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ArrayList<DetalleVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleVentaDTO> detalles) {
        this.detalles = detalles;
    }
}
