package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Vacacion;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface VacacionRepository {
    
    void guardar(Vacacion vacacion);
    Vacacion buscarPorId(int id);
    List<Vacacion> listarTodos();
    void actualizar(Vacacion vacacion);
    void eliminar(int id);
}
