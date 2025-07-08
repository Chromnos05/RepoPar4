package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.MedicamentoReceta;
import com.mycompany.hospital.domain.model.Receta;
import java.util.List;
import java.util.Map;

public interface RecetaService {
    
    void crearReceta(Receta receta);
    Receta obtenerRecetaPorId(int id);
    List<Receta> listarRecetas();
    void actualizarReceta(Receta receta);
    void eliminarReceta(int id);
    
     List<Receta> listarRecetasPorMedico(int idMedico);
    List<MedicamentoReceta> listarMedicamentosDeReceta(int idReceta);
    List<Receta> listarRecetasPorMes(int year, int month);
    List<Receta> listarRecetasPorMedicamento(int idMedicamento); // El que faltaba
    Map<String, Integer> contarRecetasPorMedicoUltimoMes();
}