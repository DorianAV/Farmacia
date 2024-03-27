package com.utsem.farmacia.Model;

import jakarta.persistence.*;

@Entity
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String sustancia_activa;
    @Column
    private int dosis;
    @Column
    private String via_de_administracion;
    @Column
    private double precio;

    @Column(unique = true)
    private int codigoDeBarras;
    @Column
    private boolean estatus;



    @ManyToOne
    private Fabricante fabricante;

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setCodigoDeBarras(int codigo_de_barras) {
        this.codigoDeBarras = codigo_de_barras;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
