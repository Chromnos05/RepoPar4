package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.CitaMedicaService;
import com.mycompany.hospital.domain.model.CitaMedica;
import com.mycompany.hospital.domain.repository.CitaMedicaRepository;

import java.util.List;

/**
 *
 * @author Oscar M
 */

public class CitaMedicaServiceImpl implements CitaMedicaService {

    private final CitaMedicaRepository repository;

    public CitaMedicaServiceImpl(CitaMedicaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void crearCita(CitaMedica cita) {
        repository.guardar(cita);
    }

    @Override
    public void actualizarCita(CitaMedica cita) {
        repository.actualizar(cita);
    }

    @Override
    public void eliminarCita(int id) {
        repository.eliminar(id);
    }

    @Override
    public CitaMedica obtenerCitaPorId(int id) {
        return repository.buscarPorId(id);
    }

    @Override
    public List<CitaMedica> listarCitas() {
        return repository.listarTodas();
    }
    
    @Override
    public List<CitaMedica> obtenerCitasDeHoy() {
        return repository.buscarCitasDeHoy();
    }
}

