package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.RecetaService;
import com.mycompany.hospital.domain.model.MedicamentoReceta;
import com.mycompany.hospital.domain.model.Receta;
import com.mycompany.hospital.domain.repository.RecetaRepository;
import java.util.List;
import java.util.Map;

public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @Override
    public void crearReceta(Receta receta) {
        recetaRepository.guardar(receta);
    }

    @Override
    public Receta obtenerRecetaPorId(int id) {
        return recetaRepository.buscarPorId(id);
    }

    @Override
    public List<Receta> listarRecetas() {
        return recetaRepository.listarTodos();
    }

    @Override
    public void actualizarReceta(Receta receta) {
        recetaRepository.actualizar(receta);
    }

    @Override
    public void eliminarReceta(int id) {
        recetaRepository.eliminar(id);
    }

    @Override
    public List<Receta> listarRecetasPorMedico(int idMedico) {
        return recetaRepository.findByMedicoId(idMedico);
    }

    @Override
    public List<MedicamentoReceta> listarMedicamentosDeReceta(int idReceta) {
        return recetaRepository.findMedicamentosByRecetaId(idReceta);
    }

    @Override
    public List<Receta> listarRecetasPorMes(int year, int month) {
        return recetaRepository.findByMonth(year, month);
    }

    @Override
    public List<Receta> listarRecetasPorMedicamento(int idMedicamento) {
        return recetaRepository.findByMedicamentoId(idMedicamento);
    }
    
    @Override
    public Map<String, Integer> contarRecetasPorMedicoUltimoMes() {
        return recetaRepository.countRecetasByMedicoLastMonth();
    }
}