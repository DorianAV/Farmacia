package com.utsem.farmacia.DTO;

import com.utsem.farmacia.Model.Lote;
import com.utsem.farmacia.Model.Ventas;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

public class DetalleVentaDTO {
    private int cantidad;
    private double precio_unitario;
    private double subtotal;
    private UUID uuid;
    private LoteDTO lote = new LoteDTO();

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LoteDTO getLote() {
        return lote;
    }

    public void setLote(LoteDTO lote) {
        this.lote = lote;
    }
}
