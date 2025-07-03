package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class PrincipioActivo {
    
    private int id;
    private String nombre;

    public PrincipioActivo() {}

    public PrincipioActivo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
