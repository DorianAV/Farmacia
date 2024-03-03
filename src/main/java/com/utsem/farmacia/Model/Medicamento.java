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
    private String precio;
    @Column(unique = true)
    private String codigo_de_barras;
    @ManyToOne
    private Fabricante fabricante;

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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCodigo_de_barras() {
        return codigo_de_barras;
    }

    public void setCodigo_de_barras(String codigo_de_barras) {
        this.codigo_de_barras = codigo_de_barras;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
