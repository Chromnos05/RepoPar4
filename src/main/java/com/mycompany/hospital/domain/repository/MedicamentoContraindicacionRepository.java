package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.MedicamentoContraindicacion;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface MedicamentoContraindicacionRepository {
    
    void guardar(MedicamentoContraindicacion mc);
    List<MedicamentoContraindicacion> buscarPorIdMedicamento(int idMedicamento);
    void eliminarPorIdMedicamento(int idMedicamento);
}
