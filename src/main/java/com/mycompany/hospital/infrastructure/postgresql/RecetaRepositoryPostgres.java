package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Receta;
import com.mycompany.hospital.domain.repository.RecetaRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetaRepositoryPostgres implements RecetaRepository {

    private final Connection conn = ConexionBD.getConnection();

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
        String sql = "UPDATE Receta SET id_diagnostico = ?, fecha_inicio = ? WHERE id_receta = ?";
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

    private Receta mapResultSetToReceta(ResultSet rs) throws SQLException {
        return new Receta(
            rs.getInt("id_receta"),
            rs.getInt("id_diagnostico"),
            rs.getDate("fecha").toLocalDate()
        );
    }

   
}