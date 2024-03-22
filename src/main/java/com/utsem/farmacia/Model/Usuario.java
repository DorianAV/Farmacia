package com.utsem.farmacia.Model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String usuario;
    @Column
    private String nombre;
    @Column
    private String apellido_pat;
    @Column
    private String apellido_mat;
    @Column
    private String correo;
    @Column
    private String contraseña;
    @Column
    private String telefono;
    @Column
    private String rol;
    private byte estatus;

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_pat() {
        return apellido_pat;
    }

    public void setApellido_pat(String apellido_pat) {
        this.apellido_pat = apellido_pat;
    }

    public String getApellido_mat() {
        return apellido_mat;
    }

    public void setApellido_mat(String apellido_mat) {
        this.apellido_mat = apellido_mat;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
