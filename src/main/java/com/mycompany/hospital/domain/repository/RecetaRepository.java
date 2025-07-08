package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.MedicamentoReceta;
import com.mycompany.hospital.domain.model.Receta;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Oscar M
 */
public interface RecetaRepository {
    
    void guardar(Receta receta);
    Receta buscarPorId(int id);
    List<Receta> listarTodos();
    void actualizar(Receta receta);
    void eliminar(int id);
    
     List<Receta> findByMedicoId(int idMedico);
    List<MedicamentoReceta> findMedicamentosByRecetaId(int idReceta);
    List<Receta> findByMonth(int year, int month);
    List<Receta> findByMedicamentoId(int idMedicamento); // El que faltaba
    Map<String, Integer> countRecetasByMedicoLastMonth();
}
