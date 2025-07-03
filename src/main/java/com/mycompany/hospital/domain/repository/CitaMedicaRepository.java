package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.CitaMedica;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface CitaMedicaRepository {
    
    void guardar(CitaMedica cita);
    CitaMedica buscarPorId(int id);
    List<CitaMedica> listarTodos();
    void actualizar(CitaMedica cita);
    void eliminar(int id);
}
