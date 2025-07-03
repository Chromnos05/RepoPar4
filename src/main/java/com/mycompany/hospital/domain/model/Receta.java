package com.mycompany.hospital.domain.model;

import java.time.LocalDate;

/**
 *
 * @author Oscar M
 */
public class Receta {
    
    private int id;
    private int idDiagnostico;
    private LocalDate fecha;

    public Receta() {}

    public Receta(int id, int idDiagnostico, LocalDate fecha) {
        this.id = id;
        this.idDiagnostico = idDiagnostico;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
