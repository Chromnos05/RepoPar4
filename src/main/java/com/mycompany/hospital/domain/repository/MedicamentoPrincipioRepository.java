package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.MedicamentoPrincipio;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface MedicamentoPrincipioRepository {
    
    void guardar(MedicamentoPrincipio mp);
    List<MedicamentoPrincipio> buscarPorIdMedicamento(int idMedicamento);
    void eliminarPorIdMedicamento(int idMedicamento);
}
