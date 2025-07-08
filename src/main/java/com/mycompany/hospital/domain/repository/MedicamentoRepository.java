package com.mycompany.hospital.domain.repository;

import java.util.List;
import com.mycompany.hospital.domain.model.*; // Import all models

/**
 *
 * @author Oscar M
 */
public interface MedicamentoRepository {
    
    void guardar(Medicamento medicamento);
    Medicamento buscarPorId(int id);
    List<Medicamento> listarTodos();
    void actualizar(Medicamento medicamento);
    void eliminar(int id);
    List<Contraindicacion> findContraindicacionesById(int idMedicamento);
    List<PrincipioActivo> findPrincipiosActivosById(int idMedicamento);
    List<Receta> findRecetasById(int idMedicamento);
    Laboratorio findLaboratorioById(int idLaboratorio);
     List<Medicamento> findByPacienteId(int idPaciente);
    Laboratorio findLaboratorioByNombre(String nombre);
    PrincipioActivo findPrincipioActivoByNombre(String nombre);
}
