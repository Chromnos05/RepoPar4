/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Medico;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface MedicoService {
    
    void crearMedico(Medico medico);
    void actualizarMedico(Medico medico);
    void eliminarMedico(int id);
    Medico obtenerMedicoPorId(int id);
    List<Medico> listarMedicos();
    List<Object[]> obtenerPacientesPorMedicoUltimoAnio();   
}
