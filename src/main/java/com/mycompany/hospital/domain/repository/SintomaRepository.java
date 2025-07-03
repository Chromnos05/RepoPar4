package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Sintoma;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface SintomaRepository {
    
    void guardar(Sintoma sintoma);
    Sintoma buscarPorId(int id);
    List<Sintoma> listarTodos();
    void actualizar(Sintoma sintoma);
    void eliminar(int id);
}
