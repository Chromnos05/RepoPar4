package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Medicamento;
import java.util.List;

public interface MedicamentoService {
    
    void crearMedicamento(Medicamento medicamento);
    Medicamento obtenerMedicamentoPorId(int id);
    List<Medicamento> listarMedicamentos();
    void actualizarMedicamento(Medicamento medicamento);
    void eliminarMedicamento(int id);
}