package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Diagnostico;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface DiagnosticoService {
    
    void crearDiagnostico(Diagnostico diagnostico);
    void actualizarDiagnostico(Diagnostico diagnostico);
    void eliminarDiagnostico(int id);
    Diagnostico obtenerDiagnosticoPorId(int id);
    List<Diagnostico> listarDiagnosticos();
    
    List<Diagnostico> obtenerPorIdPaciente(int idPaciente);
    List<Diagnostico> obtenerPorRangoDeFechas(LocalDate desde, LocalDate hasta);
    List<Diagnostico> obtenerPorMedicoYFecha(int idMedico, LocalDate fecha);

}
