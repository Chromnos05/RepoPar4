package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Medico {
    
    private int id;
    private String nombre;
    private String especialidad;
    private Integer idConsultorio;

    public Medico() {}

    public Medico(int id, String nombre, String especialidad, Integer idConsultorio) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.idConsultorio = idConsultorio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public Integer getIdConsultorio() { return idConsultorio; }
    public void setIdConsultorio(Integer idConsultorio) { this.idConsultorio = idConsultorio; }
}
