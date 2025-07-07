package com.mycompany.hospital.domain.repository;

import com.mycompany.hospital.domain.model.CitaMedica;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Oscar M
 */
public interface CitaMedicaRepository {
    
    void guardar(CitaMedica cita);
    void actualizar(CitaMedica cita);
    void eliminar(int id);
    CitaMedica buscarPorId(int id);
    List<CitaMedica> listarTodas();
    
    List<CitaMedica> buscarCitasDeHoy();
    List<String> obtenerHorasOcupadas(int idMedico, LocalDate fecha);
    Optional<CitaMedica> buscarInfoPorId(int idCita);
}
