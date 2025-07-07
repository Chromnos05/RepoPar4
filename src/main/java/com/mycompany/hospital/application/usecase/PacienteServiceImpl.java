package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.PacienteService;
import com.mycompany.hospital.domain.model.Paciente;
import com.mycompany.hospital.domain.repository.PacienteRepository;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void crearPaciente(Paciente paciente) {
        pacienteRepository.guardar(paciente);
    }

    @Override
    public Paciente obtenerPacientePorId(int id) {
        return pacienteRepository.buscarPorId(id);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.listarTodos();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.actualizar(paciente);
    }

    @Override
    public void eliminarPaciente(int id) {
        pacienteRepository.eliminar(id);
    }
    
    @Override
    public List<Paciente> obtenerPorIdMedico(int idMedico) {
        return pacienteRepository.buscarPorIdMedico(idMedico);
    }
}

