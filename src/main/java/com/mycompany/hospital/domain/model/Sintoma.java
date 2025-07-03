package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Sintoma {
    
    private int id;
    private String descripcion;

    public Sintoma() {}

    public Sintoma(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
