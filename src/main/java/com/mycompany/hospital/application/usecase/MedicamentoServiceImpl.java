package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.MedicamentoService;
import com.mycompany.hospital.domain.model.Medicamento;
import com.mycompany.hospital.domain.repository.MedicamentoRepository;
import java.util.List;

public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public void crearMedicamento(Medicamento medicamento) {
        medicamentoRepository.guardar(medicamento);
    }

    @Override
    public Medicamento obtenerMedicamentoPorId(int id) {
        return medicamentoRepository.buscarPorId(id);
    }

    @Override
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.listarTodos();
    }

    @Override
    public void actualizarMedicamento(Medicamento medicamento) {
        medicamentoRepository.actualizar(medicamento);
    }

    @Override
    public void eliminarMedicamento(int id) {
        medicamentoRepository.eliminar(id);
    }
}