package com.mycompany.hospital.domain.model;

import java.time.LocalDate;

/**
 *
 * @author Oscar M
 */
public class Paciente {
    
    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String direccion;
    private Integer idMedicoAsignado;

    public Paciente() {
    }

    public Paciente(int id, String nombre, LocalDate fechaNacimiento, String direccion, Integer idMedicoAsignado) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.idMedicoAsignado = idMedicoAsignado;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public String getDireccion() {return direccion;}
    public void setDireccion(String direccion) {this.direccion = direccion;}

    public Integer getIdMedicoAsignado() {return idMedicoAsignado;}
    public void setIdMedicoAsignado(Integer idMedicoAsignado) {this.idMedicoAsignado = idMedicoAsignado;}
}
