package com.utsem.farmacia.DTO;


import java.util.Date;

public class LoteDTO {

    private String lote;
    private Date fecha_fabricacion;
    private Date fechaCaducidad;
    private int existencia;
    private MedicamentoDTO medicamento;

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha_fabricacion() {
        return fecha_fabricacion;
    }

    public void setFecha_fabricacion(Date fecha_fabricacion) {
        this.fecha_fabricacion = fecha_fabricacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public MedicamentoDTO getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoDTO medicamento) {
        this.medicamento = medicamento;
    }
}
