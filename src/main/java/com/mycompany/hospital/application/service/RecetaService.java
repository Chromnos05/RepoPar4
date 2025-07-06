package com.mycompany.hospital.application.service;

import com.mycompany.hospital.domain.model.Receta;
import java.util.List;

public interface RecetaService {
    
    void crearReceta(Receta receta);
    Receta obtenerRecetaPorId(int id);
    List<Receta> listarRecetas();
    void actualizarReceta(Receta receta);
    void eliminarReceta(int id);
}