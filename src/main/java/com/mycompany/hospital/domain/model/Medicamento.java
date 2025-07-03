package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class Medicamento {
    
    private int id;
    private String nombre;
    private Integer idLaboratorio;
    private int unidadesDisponibles;
    private boolean necesitaReceta;

    public Medicamento() {}

    public Medicamento(int id, String nombre, Integer idLaboratorio, int unidadesDisponibles, boolean necesitaReceta) {
        this.id = id;
        this.nombre = nombre;
        this.idLaboratorio = idLaboratorio;
        this.unidadesDisponibles = unidadesDisponibles;
        this.necesitaReceta = necesitaReceta;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getIdLaboratorio() { return idLaboratorio; }
    public void setIdLaboratorio(Integer idLaboratorio) { this.idLaboratorio = idLaboratorio; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }

    public boolean isNecesitaReceta() { return necesitaReceta; }
    public void setNecesitaReceta(boolean necesitaReceta) { this.necesitaReceta = necesitaReceta; }
}
