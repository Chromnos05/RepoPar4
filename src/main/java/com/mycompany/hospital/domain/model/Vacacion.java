package com.mycompany.hospital.domain.model;

import java.time.LocalDate;

/**
 *
 * @author Oscar M
 */
public class Vacacion {
    
    private int id;
    private Integer idMedico;
    private Integer idEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo;

    public Vacacion() {}

    public Vacacion(int id, Integer idMedico, Integer idEmpleado, LocalDate fechaInicio, LocalDate fechaFin, String tipo) {
        this.id = id;
        this.idMedico = idMedico;
        this.idEmpleado = idEmpleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getIdMedico() { return idMedico; }
    public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }

    public Integer getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Integer idEmpleado) { this.idEmpleado = idEmpleado; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
