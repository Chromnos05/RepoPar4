package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.Sustitucion;
import java.util.List;


/**
 *
 * @author Oscar M
 */
public interface SustitucionRepository {
    List<Sustitucion> buscarPorMedicoSustituto(int idMedico);
}
