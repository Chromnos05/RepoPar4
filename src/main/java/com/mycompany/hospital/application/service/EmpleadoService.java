/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Empleado;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public interface EmpleadoService {
    void crearEmpleado(Empleado empleado);
    Empleado obtenerEmpleadoPorId(int id);
    List<Empleado> listarEmpleados();
    void actualizarEmpleado(Empleado empleado);
    void eliminarEmpleado(int id);
    List<Empleado> listarEmpleadosConVacaciones();
}
