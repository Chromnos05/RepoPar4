package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.MedicamentoReceta;
import com.mycompany.hospital.domain.model.Receta;
import com.mycompany.hospital.domain.repository.RecetaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetaRepositoryPostgres implements RecetaRepository {

    private final Connection conn = ConexionBD.getConnection();

    // --- MÉTODOS CRUD (CON CORRECCIÓN EN 'actualizar') ---

    @Override
    public void guardar(Receta receta) {
        String sql = "INSERT INTO Receta(id_diagnostico, fecha) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receta.getIdDiagnostico());
            stmt.setDate(2, Date.valueOf(receta.getFecha()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Receta buscarPorId(int id) {
        String sql = "SELECT * FROM Receta WHERE id_receta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToReceta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Receta> listarTodos() {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Receta ORDER BY id_receta ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Receta receta) {
        // CORREGIDO: 'fecha' en lugar de 'fecha_inicio'
        String sql = "UPDATE Receta SET id_diagnostico = ?, fecha = ? WHERE id_receta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receta.getIdDiagnostico());
            stmt.setDate(2, Date.valueOf(receta.getFecha()));
            stmt.setInt(3, receta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Receta WHERE id_receta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // --- MÉTODOS DE CONSULTA (CON CORRECCIONES) ---

    @Override
    public List<Receta> findByMedicoId(int idMedico) {
        List<Receta> lista = new ArrayList<>();
        // CORREGIDO: 'CitaMedica' en lugar de 'Cita_Medica'
        String sql = "SELECT r.* FROM Receta r " +
                     "JOIN Diagnostico d ON r.id_diagnostico = d.id_diagnostico " +
                     "JOIN CitaMedica cm ON d.id_cita = cm.id_cita " +
                     "WHERE cm.id_medico = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<MedicamentoReceta> findMedicamentosByRecetaId(int idReceta) {
        List<MedicamentoReceta> lista = new ArrayList<>();
        // Esta consulta estaba bien
        String sql = "SELECT * FROM Medicamento_Receta WHERE id_receta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReceta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToMedicamentoReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Receta> findByMonth(int year, int month) {
        List<Receta> lista = new ArrayList<>();
        // CORREGIDO: 'fecha' en lugar de 'fecha_inicio'
        String sql = "SELECT * FROM Receta WHERE EXTRACT(YEAR FROM fecha) = ? AND EXTRACT(MONTH FROM fecha) = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setInt(2, month);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(mapResultSetToReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Receta> findByMedicamentoId(int idMedicamento) {
        List<Receta> lista = new ArrayList<>();
        // Esta consulta estaba bien
        String sql = "SELECT r.* FROM Receta r " +
                     "JOIN Medicamento_Receta mr ON r.id_receta = mr.id_receta " +
                     "WHERE mr.id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Map<String, Integer> countRecetasByMedicoLastMonth() {
        Map<String, Integer> reporte = new HashMap<>();
        // CORREGIDO: 'CitaMedica' y 'r.fecha'
        String sql = "SELECT m.nombre, COUNT(r.id_receta) as total_recetas " +
                     "FROM Receta r " +
                     "JOIN Diagnostico d ON r.id_diagnostico = d.id_diagnostico " +
                     "JOIN CitaMedica cm ON d.id_cita = cm.id_cita " +
                     "JOIN Medico m ON cm.id_medico = m.id_medico " +
                     "WHERE r.fecha >= NOW() - INTERVAL '1 month' " +
                     "GROUP BY m.nombre " +
                     "ORDER BY total_recetas DESC";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reporte.put(rs.getString("nombre"), rs.getInt("total_recetas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reporte;
    }
    
    // --- MÉTODOS DE MAPEO (SIN CAMBIOS, YA ESTABAN BIEN) ---

    private MedicamentoReceta mapResultSetToMedicamentoReceta(ResultSet rs) throws SQLException {
        return new MedicamentoReceta(
            rs.getInt("id_receta"),
            rs.getInt("id_medicamento"),
            rs.getInt("cantidad"),
            rs.getString("dosis")
        );
    }

    private Receta mapResultSetToReceta(ResultSet rs) throws SQLException {
        return new Receta(
            rs.getInt("id_receta"),
            rs.getInt("id_diagnostico"),
            rs.getDate("fecha").toLocalDate()
        );
    }
}