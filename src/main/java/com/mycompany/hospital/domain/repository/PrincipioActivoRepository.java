package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.PrincipioActivo;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface PrincipioActivoRepository {
    
    void guardar(PrincipioActivo principio);
    PrincipioActivo buscarPorId(int id);
    List<PrincipioActivo> listarTodos();
    void actualizar(PrincipioActivo principio);
    void eliminar(int id);
}
