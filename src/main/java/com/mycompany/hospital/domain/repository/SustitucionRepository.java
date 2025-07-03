package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Sustitucion;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface SustitucionRepository {
    
    void guardar(Sustitucion sustitucion);
    Sustitucion buscarPorId(int id);
    List<Sustitucion> listarTodos();
    void actualizar(Sustitucion sustitucion);
    void eliminar(int id);
}
