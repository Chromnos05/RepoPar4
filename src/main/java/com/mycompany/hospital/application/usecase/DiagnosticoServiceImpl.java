package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.DiagnosticoService;
import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.repository.DiagnosticoRepository;
import java.time.LocalDate;

import java.util.List;

/**
 *
 * @author Oscar M
 */

public class DiagnosticoServiceImpl implements DiagnosticoService {

    private final DiagnosticoRepository repository;

    public DiagnosticoServiceImpl(DiagnosticoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void crearDiagnostico(Diagnostico diagnostico) {
        repository.guardar(diagnostico);
    }

    @Override
    public void actualizarDiagnostico(Diagnostico diagnostico) {
        repository.actualizar(diagnostico);
    }

    @Override
    public void eliminarDiagnostico(int id) {
        repository.eliminar(id);
    }

    @Override
    public Diagnostico obtenerDiagnosticoPorId(int id) {
        return repository.buscarPorId(id);
    }

    @Override
    public List<Diagnostico> listarDiagnosticos() {
        return repository.listarTodos();
    }
    
    @Override
    public List<Diagnostico> obtenerPorIdPaciente(int idPaciente) {
        return repository.buscarPorIdPaciente(idPaciente);
    }
    
    @Override
    public List<Diagnostico> obtenerPorRangoDeFechas(LocalDate desde, LocalDate hasta) {
        return repository.buscarPorRangoDeFechas(desde, hasta);
    }
    
    @Override
    public List<Diagnostico> obtenerPorMedicoYFecha(int idMedico, LocalDate fecha) {
        return repository.buscarPorMedicoYFecha(idMedico, fecha);
    }
}

