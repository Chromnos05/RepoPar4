package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Diagnostico {
    
    private int id;
    private int idCita;
    private int idEnfermedad;
    private String observaciones;

    public Diagnostico() {}

    public Diagnostico(int id, int idCita, int idEnfermedad, String observaciones) {
        this.id = id;
        this.idCita = idCita;
        this.idEnfermedad = idEnfermedad;
        this.observaciones = observaciones;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCita() { return idCita; }
    public void setIdCita(int idCita) { this.idCita = idCita; }

    public int getIdEnfermedad() { return idEnfermedad; }
    public void setIdEnfermedad(int idEnfermedad) { this.idEnfermedad = idEnfermedad; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
