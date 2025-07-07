package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Paciente;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface PacienteService {
    
    void crearPaciente(Paciente paciente);
    Paciente obtenerPacientePorId(int id);
    List<Paciente> listarPacientes();
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(int id);
    
    List<Paciente> obtenerPorIdMedico(int idMedico);
    List<Paciente> obtenerPorIdConsultorio(int idConsultorio);
    List<Paciente> obtenerPacientesConEnfermedadCronica();
    List<Paciente> obtenerPorConsultorioYFecha(int idConsultorio, LocalDate fecha);

}
