package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Diagnostico;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface DiagnosticoRepository {
    
    void guardar(Diagnostico diagnostico);
    void actualizar(Diagnostico diagnostico);
    void eliminar(int id);
    Diagnostico buscarPorId(int id);
    List<Diagnostico> listarTodos();
    
    List<Diagnostico> buscarPorIdPaciente(int idPaciente);
    List<Diagnostico> buscarPorRangoDeFechas(LocalDate desde, LocalDate hasta);
}
