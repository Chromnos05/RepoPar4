package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.model.Sintoma;
import java.time.LocalDate;
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
    List<Sintoma> findSintomasByEnfermedadId(int idEnfermedad);
    List<Diagnostico> findDiagnosticosByEnfermedadId(int idEnfermedad);
    List<Enfermedad> findDiagnosticadasEnPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}
