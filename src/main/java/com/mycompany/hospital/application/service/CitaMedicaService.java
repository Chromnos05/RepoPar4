/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.CitaMedica;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar M
 */
public interface CitaMedicaService {
    
    void crearCita(CitaMedica cita);
    void actualizarCita(CitaMedica cita);
    void eliminarCita(int id);
    CitaMedica obtenerCitaPorId(int id);
    List<CitaMedica> listarCitas();
    
    List<CitaMedica> obtenerCitasDeHoy();
    List<String> obtenerHorasDisponibles(int idMedico, LocalDate fecha);
    Optional<CitaMedica> obtenerInfoPorId(int idCita);
}
