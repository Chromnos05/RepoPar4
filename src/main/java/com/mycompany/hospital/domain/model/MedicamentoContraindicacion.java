package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class MedicamentoContraindicacion {
    
    private int idMedicamento;
    private int idContraindicacion;

    public MedicamentoContraindicacion() {}

    public MedicamentoContraindicacion(int idMedicamento, int idContraindicacion) {
        this.idMedicamento = idMedicamento;
        this.idContraindicacion = idContraindicacion;
    }

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }

    public int getIdContraindicacion() { return idContraindicacion; }
    public void setIdContraindicacion(int idContraindicacion) { this.idContraindicacion = idContraindicacion; }
}
