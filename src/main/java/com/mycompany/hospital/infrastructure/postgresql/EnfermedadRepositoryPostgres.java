package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.model.Sintoma;
import com.mycompany.hospital.domain.repository.EnfermedadRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadRepositoryPostgres implements EnfermedadRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Enfermedad enfermedad) {
        String sql = "INSERT INTO Enfermedad(nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enfermedad.getNombre());
            stmt.setString(2, enfermedad.getDescripcion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Enfermedad buscarPorId(int id) {
        String sql = "SELECT * FROM Enfermedad WHERE id_enfermedad = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEnfermedad(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Enfermedad> listarTodas() {
        List<Enfermedad> lista = new ArrayList<>();
        String sql = "SELECT * FROM Enfermedad ORDER BY id_enfermedad ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToEnfermedad(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Enfermedad enfermedad) {
        String sql = "UPDATE Enfermedad SET nombre = ?, descripcion = ? WHERE id_enfermedad = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enfermedad.getNombre());
            stmt.setString(2, enfermedad.getDescripcion());
            stmt.setInt(3, enfermedad.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Enfermedad WHERE id_enfermedad = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Sintoma> findSintomasByEnfermedadId(int idEnfermedad) {
        List<Sintoma> lista = new ArrayList<>();
        String sql = "SELECT s.* FROM Sintoma s " +
                     "JOIN Enfermedad_Sintoma es ON s.id_sintoma = es.id_sintoma " +
                     "WHERE es.id_enfermedad = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToSintoma(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Diagnostico> findDiagnosticosByEnfermedadId(int idEnfermedad) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Diagnostico WHERE id_enfermedad = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEnfermedad);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToDiagnostico(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Enfermedad> findDiagnosticadasEnPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Enfermedad> lista = new ArrayList<>();
        // Unimos Enfermedad -> Diagnostico -> Cita_Medica para acceder a la fecha
        String sql = "SELECT DISTINCT e.* FROM Enfermedad e " +
                     "JOIN Diagnostico d ON e.id_enfermedad = d.id_enfermedad " +
                     "JOIN Cita_Medica cm ON d.id_cita = cm.id_cita " +
                     "WHERE cm.fecha BETWEEN ? AND ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Reutilizamos el método de mapeo que ya teníamos
                lista.add(mapResultSetToEnfermedad(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // NUEVOS MÉTODOS AUXILIARES PARA MAPEAR RESULTADOS
    private Sintoma mapResultSetToSintoma(ResultSet rs) throws SQLException {
        return new Sintoma(
            rs.getInt("id_sintoma"),
            rs.getString("descripcion")
        );
    }

    private Diagnostico mapResultSetToDiagnostico(ResultSet rs) throws SQLException {
        return new Diagnostico(
            rs.getInt("id_diagnostico"),
            rs.getInt("id_cita"),
            rs.getInt("id_enfermedad"),
            rs.getString("observaciones")
        );
    }

    private Enfermedad mapResultSetToEnfermedad(ResultSet rs) throws SQLException {
        return new Enfermedad(
            rs.getInt("id_enfermedad"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}