package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Contraindicacion;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface ContraindicacionRepository {
    
    void guardar(Contraindicacion contra);
    Contraindicacion buscarPorId(int id);
    List<Contraindicacion> listarTodos();
    void actualizar(Contraindicacion contra);
    void eliminar(int id);
}
