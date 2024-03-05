package com.utsem.farmacia.DTO;

import com.utsem.farmacia.Model.Fabricante;

public class MedicamentoDTO {
    private String nombre;
    private String sustancia_activa;
    private int dosis;
    private String via_de_administracion;
    private String precio;
    private int codigo_de_barras;
    private Fabricante fabricante;

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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getCodigo_de_barras() {
        return codigo_de_barras;
    }

    public void setCodigo_de_barras(int codigo_de_barras) {
        this.codigo_de_barras = codigo_de_barras;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
