package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Receta;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface RecetaRepository {
    
    void guardar(Receta receta);
    Receta buscarPorId(int id);
    List<Receta> listarTodos();
    void actualizar(Receta receta);
    void eliminar(int id);
}
