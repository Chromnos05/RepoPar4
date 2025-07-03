package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Laboratorio;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface LaboratorioRepository {
    
    void guardar(Laboratorio laboratorio);
    Laboratorio buscarPorId(int id);
    List<Laboratorio> listarTodos();
    void actualizar(Laboratorio laboratorio);
    void eliminar(int id);
}
