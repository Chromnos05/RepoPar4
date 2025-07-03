package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class MedicamentoReceta {
    
    private int idReceta;
    private int idMedicamento;
    private int cantidad;
    private String dosis;

    public MedicamentoReceta() {}

    public MedicamentoReceta(int idReceta, int idMedicamento, int cantidad, String dosis) {
        this.idReceta = idReceta;
        this.idMedicamento = idMedicamento;
        this.cantidad = cantidad;
        this.dosis = dosis;
    }

    public int getIdReceta() { return idReceta; }
    public void setIdReceta(int idReceta) { this.idReceta = idReceta; }

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
}
