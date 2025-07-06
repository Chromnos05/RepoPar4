package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Enfermedad;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface EnfermedadRepository {
    
    void guardar(Enfermedad enfermedad);
    Enfermedad buscarPorId(int id);
    List<Enfermedad> listarTodas();
    void actualizar(Enfermedad enfermedad);
    void eliminar(int id);
}
