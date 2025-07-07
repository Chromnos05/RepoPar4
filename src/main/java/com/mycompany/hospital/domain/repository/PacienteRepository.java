package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Paciente;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface PacienteRepository {
    
    void guardar(Paciente paciente);
    Paciente buscarPorId(int id);
    List<Paciente> listarTodos();
    void actualizar(Paciente paciente);
    void eliminar(int id);
    
    List<Paciente> buscarPorIdMedico(int idMedico);
    List<Paciente> buscarPorIdConsultorio(int idConsultorio);
    List<Paciente> buscarPacientesConEnfermedadCronica();
    List<Paciente> buscarPorConsultorioYFecha(int idConsultorio, LocalDate fecha);

}
