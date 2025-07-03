package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class MedicamentoPrincipio {
    
    private int idMedicamento;
    private int idPrincipio;

    public MedicamentoPrincipio() {}

    public MedicamentoPrincipio(int idMedicamento, int idPrincipio) {
        this.idMedicamento = idMedicamento;
        this.idPrincipio = idPrincipio;
    }

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }

    public int getIdPrincipio() { return idPrincipio; }
    public void setIdPrincipio(int idPrincipio) { this.idPrincipio = idPrincipio; }
}
