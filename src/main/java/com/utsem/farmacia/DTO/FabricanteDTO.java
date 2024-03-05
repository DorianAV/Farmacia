package com.utsem.farmacia.DTO;


import java.util.UUID;

public class FabricanteDTO {
    private String nombre;
    private String telefono;
    private UUID uuid=UUID.randomUUID();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
