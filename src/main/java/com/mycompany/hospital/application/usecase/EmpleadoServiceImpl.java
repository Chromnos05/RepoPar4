package com.mycompany.hospital.application.usecase;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.mycompany.hospital.application.service.EmpleadoService;
import com.mycompany.hospital.domain.model.Empleado;
import com.mycompany.hospital.domain.repository.EmpleadoRepository;
import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public void crearEmpleado(Empleado empleado) {
        empleadoRepository.guardar(empleado);
    }

    @Override
    public Empleado obtenerEmpleadoPorId(int id) {
        return empleadoRepository.buscarPorId(id);
    }

    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.listarTodos();
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {
        empleadoRepository.actualizar(empleado);
    }

    @Override
    public void eliminarEmpleado(int id) {
        empleadoRepository.eliminar(id);
    }
    
    @Override
    public List<Empleado> listarEmpleadosConVacaciones() {
        return empleadoRepository.findConVacacionesUltimoAnio();
    }
}