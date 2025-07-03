package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.EnfermedadSintoma;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface EnfermedadSintomaRepository {
    
    void guardar(EnfermedadSintoma es);
    List<EnfermedadSintoma> buscarPorIdEnfermedad(int idEnfermedad);
    void eliminarPorIdEnfermedad(int idEnfermedad);
}
