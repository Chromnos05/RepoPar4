package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Laboratorio {
    
    private int id;
    private String nombre;
    private String direccion;

    public Laboratorio() {}

    public Laboratorio(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
