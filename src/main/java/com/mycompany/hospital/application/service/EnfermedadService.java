package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Enfermedad;
import java.util.List;

public interface EnfermedadService {
    
    void crearEnfermedad(Enfermedad enfermedad);
    Enfermedad obtenerEnfermedadPorId(int id);
    List<Enfermedad> listarEnfermedades();
    void actualizarEnfermedad(Enfermedad enfermedad);
    void eliminarEnfermedad(int id);
}