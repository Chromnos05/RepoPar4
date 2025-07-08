package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.MedicamentoService;
import com.mycompany.hospital.domain.model.*;
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
    @Override
    public List<Contraindicacion> listarContraindicaciones(int idMedicamento) {
        return medicamentoRepository.findContraindicacionesById(idMedicamento);
    }

    @Override
    public List<PrincipioActivo> listarPrincipiosActivos(int idMedicamento) {
        return medicamentoRepository.findPrincipiosActivosById(idMedicamento);
    }

    @Override
    public List<Receta> listarRecetasConMedicamento(int idMedicamento) {
        return medicamentoRepository.findRecetasById(idMedicamento);
    }
    
    @Override
    public Laboratorio obtenerLaboratorio(int idLaboratorio) {
        return medicamentoRepository.findLaboratorioById(idLaboratorio);
    }
    
    @Override
    public List<Medicamento> listarMedicamentosPorPaciente(int idPaciente) {
        return medicamentoRepository.findByPacienteId(idPaciente);
    }

    @Override
    public Laboratorio buscarLaboratorioPorNombre(String nombre) {
        return medicamentoRepository.findLaboratorioByNombre(nombre);
    }

    @Override
    public PrincipioActivo buscarPrincipioActivoPorNombre(String nombre) {
        return medicamentoRepository.findPrincipioActivoByNombre(nombre);
    }
}