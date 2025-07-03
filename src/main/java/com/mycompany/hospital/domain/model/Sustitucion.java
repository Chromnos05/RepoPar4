package com.mycompany.hospital.domain.model;

import java.time.LocalDate;

/**
 *
 * @author Oscar M
 */
public class Sustitucion {
    
    private int id;
    private int idMedicoTitular;
    private int idMedicoSustituto;
    private LocalDate fecha;

    public Sustitucion() {}

    public Sustitucion(int id, int idMedicoTitular, int idMedicoSustituto, LocalDate fecha) {
        this.id = id;
        this.idMedicoTitular = idMedicoTitular;
        this.idMedicoSustituto = idMedicoSustituto;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdMedicoTitular() { return idMedicoTitular; }
    public void setIdMedicoTitular(int idMedicoTitular) { this.idMedicoTitular = idMedicoTitular; }

    public int getIdMedicoSustituto() { return idMedicoSustituto; }
    public void setIdMedicoSustituto(int idMedicoSustituto) { this.idMedicoSustituto = idMedicoSustituto; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
