package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Consultorio;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface ConsultorioRepository {
    
    void guardar(Consultorio consultorio);
    Consultorio buscarPorId(int id);
    List<Consultorio> listarTodos();
    void actualizar(Consultorio consultorio);
    void eliminar(int id);
}
