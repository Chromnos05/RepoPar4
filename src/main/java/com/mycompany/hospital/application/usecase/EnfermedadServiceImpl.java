package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.EnfermedadService;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.repository.EnfermedadRepository;
import java.util.List;

public class EnfermedadServiceImpl implements EnfermedadService {

    private final EnfermedadRepository enfermedadRepository;

    public EnfermedadServiceImpl(EnfermedadRepository enfermedadRepository) {
        this.enfermedadRepository = enfermedadRepository;
    }

    @Override
    public void crearEnfermedad(Enfermedad enfermedad) {
        enfermedadRepository.guardar(enfermedad);
    }

    @Override
    public Enfermedad obtenerEnfermedadPorId(int id) {
        return enfermedadRepository.buscarPorId(id);
    }

    @Override
    public List<Enfermedad> listarEnfermedades() {
        return enfermedadRepository.listarTodas();
    }

    @Override
    public void actualizarEnfermedad(Enfermedad enfermedad) {
        enfermedadRepository.actualizar(enfermedad);
    }

    @Override
    public void eliminarEnfermedad(int id) {
        enfermedadRepository.eliminar(id);
    }
}