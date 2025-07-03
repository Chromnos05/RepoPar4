package com.mycompany.hospital.domain.model;

/**
 *
 * @author Oscar M
 */
public class EnfermedadSintoma {
    
    private int idEnfermedad;
    private int idSintoma;

    public EnfermedadSintoma() {}

    public EnfermedadSintoma(int idEnfermedad, int idSintoma) {
        this.idEnfermedad = idEnfermedad;
        this.idSintoma = idSintoma;
    }

    public int getIdEnfermedad() { return idEnfermedad; }
    public void setIdEnfermedad(int idEnfermedad) { this.idEnfermedad = idEnfermedad; }

    public int getIdSintoma() { return idSintoma; }
    public void setIdSintoma(int idSintoma) { this.idSintoma = idSintoma; }
}
