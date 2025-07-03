package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.HorarioConsulta;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface HorarioConsultaRepository {
    
    void guardar(HorarioConsulta horario);
    HorarioConsulta buscarPorId(int id);
    List<HorarioConsulta> listarTodos();
    void actualizar(HorarioConsulta horario);
    void eliminar(int id);
}
