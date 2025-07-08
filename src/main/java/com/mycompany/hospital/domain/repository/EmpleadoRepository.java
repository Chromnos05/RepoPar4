package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Empleado;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface EmpleadoRepository {
    
    void guardar(Empleado empleado);
    Empleado buscarPorId(int id);
    List<Empleado> listarTodos();
    void actualizar(Empleado empleado);
    void eliminar(int id);
    List<Empleado> findConVacacionesUltimoAnio(); 
}
