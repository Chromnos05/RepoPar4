package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.model.Sintoma;
import java.time.LocalDate;
import java.util.List;

public interface EnfermedadService {
    
    void crearEnfermedad(Enfermedad enfermedad);
    Enfermedad obtenerEnfermedadPorId(int id);
    List<Enfermedad> listarEnfermedades();
    void actualizarEnfermedad(Enfermedad enfermedad);
    void eliminarEnfermedad(int id);
    List<Sintoma> listarSintomasDeEnfermedad(int idEnfermedad);
    List<Diagnostico> listarDiagnosticosDeEnfermedad(int idEnfermedad);
      List<Enfermedad> listarDiagnosticadasEnPeriodo(LocalDate fechaInicio, LocalDate fechaFin);
}