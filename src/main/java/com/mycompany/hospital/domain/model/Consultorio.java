package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Consultorio {
    
    private int id;
    private String nombre;

    public Consultorio() {}

    public Consultorio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
