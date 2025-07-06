package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Enfermedad;
import com.mycompany.hospital.domain.repository.EnfermedadRepository;
import java.sql.*;
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

    private Enfermedad mapResultSetToEnfermedad(ResultSet rs) throws SQLException {
        return new Enfermedad(
            rs.getInt("id_enfermedad"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}