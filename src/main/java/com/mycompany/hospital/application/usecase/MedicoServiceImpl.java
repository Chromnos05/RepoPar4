package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.MedicoService;
import com.mycompany.hospital.domain.model.Medico;
import com.mycompany.hospital.domain.repository.MedicoRepository;

import java.util.List;

/**
 *
 * @author Oscar M
 */

public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void crearMedico(Medico medico) {
        medicoRepository.guardar(medico);
    }

    @Override
    public void actualizarMedico(Medico medico) {
        medicoRepository.actualizar(medico);
    }

    @Override
    public void eliminarMedico(int id) {
        medicoRepository.eliminar(id);
    }

    @Override
    public Medico obtenerMedicoPorId(int id) {
        return medicoRepository.buscarPorId(id);
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.listarTodos();
    }
}
