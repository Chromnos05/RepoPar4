/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Sustitucion;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface SustitucionService {
    List<Sustitucion> obtenerPorMedicoSustituto(int idMedico);
}
