package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class HorarioConsulta {
    
    private int id;
    private int idMedico;
    private String dia;
    private String horaInicio;
    private String horaFin;

    public HorarioConsulta() {}

    public HorarioConsulta(int id, int idMedico, String dia, String horaInicio, String horaFin) {
        this.id = id;
        this.idMedico = idMedico;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdMedico() { return idMedico; }
    public void setIdMedico(int idMedico) { this.idMedico = idMedico; }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
}
