package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.MedicamentoReceta;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface MedicamentoRecetaRepository {
    
    void guardar(MedicamentoReceta mr);
    List<MedicamentoReceta> buscarPorIdReceta(int idReceta);
    void eliminarPorIdReceta(int idReceta);
}
