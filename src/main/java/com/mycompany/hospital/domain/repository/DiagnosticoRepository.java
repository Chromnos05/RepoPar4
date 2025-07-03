package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Diagnostico;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface DiagnosticoRepository {
    
    void guardar(Diagnostico diagnostico);
    Diagnostico buscarPorId(int id);
    List<Diagnostico> listarTodos();
    void actualizar(Diagnostico diagnostico);
    void eliminar(int id);
}
