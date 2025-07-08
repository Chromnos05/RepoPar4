package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.*;
import java.util.List;

public interface MedicamentoService {
    
    void crearMedicamento(Medicamento medicamento);
    Medicamento obtenerMedicamentoPorId(int id);
    List<Medicamento> listarMedicamentos();
    void actualizarMedicamento(Medicamento medicamento);
    void eliminarMedicamento(int id);
    List<Contraindicacion> listarContraindicaciones(int idMedicamento);
    List<PrincipioActivo> listarPrincipiosActivos(int idMedicamento);
    List<Receta> listarRecetasConMedicamento(int idMedicamento);
    Laboratorio obtenerLaboratorio(int idLaboratorio);
     List<Medicamento> listarMedicamentosPorPaciente(int idPaciente);
    Laboratorio buscarLaboratorioPorNombre(String nombre);
    PrincipioActivo buscarPrincipioActivoPorNombre(String nombre);
}