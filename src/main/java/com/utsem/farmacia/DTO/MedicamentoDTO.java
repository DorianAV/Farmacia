package com.utsem.farmacia.DTO;

import com.utsem.farmacia.Model.Fabricante;

import java.util.UUID;

public class MedicamentoDTO {
    private String nombre;
    private String sustancia_activa;
    private int dosis;
    private String via_de_administracion;
    private double precio;
    private int codigoDeBarras;
    private FabricanteDTO fabricanteDTO;



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSustancia_activa() {
        return sustancia_activa;
    }

    public void setSustancia_activa(String sustancia_activa) {
        this.sustancia_activa = sustancia_activa;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getVia_de_administracion() {
        return via_de_administracion;
    }

    public void setVia_de_administracion(String via_de_administracion) {
        this.via_de_administracion = via_de_administracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(int codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }


    public FabricanteDTO getFabricanteDTO() {
        return fabricanteDTO;
    }

    public void setFabricanteDTO(FabricanteDTO fabricanteDTO) {
        this.fabricanteDTO = fabricanteDTO;
    }
}
