package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Medicamento;
import java.util.List;

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
}
