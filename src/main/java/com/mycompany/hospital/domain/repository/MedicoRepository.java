package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Medico;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface MedicoRepository {
    
    void guardar(Medico medico);
    Medico buscarPorId(int id);
    List<Medico> listarTodos();
    void actualizar(Medico medico);
    void eliminar(int id);
    
    List<Object[]> obtenerPacientesPorMedicoUltimoAnio();
    List<Medico> buscarPorEspecialidad(String especialidad);

}
