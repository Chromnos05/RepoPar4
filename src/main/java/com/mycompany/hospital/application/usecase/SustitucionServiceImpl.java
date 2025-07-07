package com.mycompany.hospital.application.usecase;

import com.mycompany.hospital.application.service.SustitucionService;
import com.mycompany.hospital.domain.model.Sustitucion;
import com.mycompany.hospital.domain.repository.SustitucionRepository;

import java.util.List;
/**
 *
 * @author Oscar M
 */
public class SustitucionServiceImpl implements SustitucionService {

    private final SustitucionRepository repository;

    public SustitucionServiceImpl(SustitucionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sustitucion> obtenerPorMedicoSustituto(int idMedico) {
        return repository.buscarPorMedicoSustituto(idMedico);
    }
}