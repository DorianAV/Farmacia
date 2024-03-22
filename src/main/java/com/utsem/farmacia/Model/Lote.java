package com.utsem.farmacia.Model;


import jakarta.persistence.*;

import java.sql.Date;


@Entity
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String lote;
    @Column
    private Date fecha_fabricacion;
    @Column
    private Date fechaCaducidad;
    @Column
    private int existencia;
    @ManyToOne
    private Medicamento medicamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
}
