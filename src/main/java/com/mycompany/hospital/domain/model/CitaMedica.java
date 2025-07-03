package com.mycompany.hospital.domain.model;

import java.time.LocalDate;

/**
 *
 * @author Oscar M
 */
public class CitaMedica {
    
    private int id;
    private int idPaciente;
    private int idMedico;
    private LocalDate fecha;
    private String hora;

    public CitaMedica() {}

    public CitaMedica(int id, int idPaciente, int idMedico, LocalDate fecha, String hora) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPaciente() { return idPaciente; }
    public void setIdPaciente(int idPaciente) { this.idPaciente = idPaciente; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
}
