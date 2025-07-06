package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Medicamento;
import com.mycompany.hospital.domain.repository.MedicamentoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepositoryPostgres implements MedicamentoRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Medicamento medicamento) {
        String sql = "INSERT INTO Medicamento(nombre, id_laboratorio, unidades_disponibles, necesita_receta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento.getNombre());
            stmt.setObject(2, medicamento.getIdLaboratorio(), Types.INTEGER); // Para manejar nulos
            stmt.setInt(3, medicamento.getUnidadesDisponibles());
            stmt.setBoolean(4, medicamento.isNecesitaReceta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Medicamento buscarPorId(int id) {
        String sql = "SELECT * FROM Medicamento WHERE id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToMedicamento(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicamento> listarTodos() {
        List<Medicamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medicamento ORDER BY id_medicamento ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Medicamento medicamento) {
        String sql = "UPDATE Medicamento SET nombre = ?, id_laboratorio = ?, unidades_disponibles = ?, necesita_receta = ? WHERE id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento.getNombre());
            stmt.setObject(2, medicamento.getIdLaboratorio(), Types.INTEGER);
            stmt.setInt(3, medicamento.getUnidadesDisponibles());
            stmt.setBoolean(4, medicamento.isNecesitaReceta());
            stmt.setInt(5, medicamento.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Medicamento WHERE id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Medicamento mapResultSetToMedicamento(ResultSet rs) throws SQLException {
        return new Medicamento(
            rs.getInt("id_medicamento"),
            rs.getString("nombre"),
            (Integer) rs.getObject("id_laboratorio"), // Para manejar nulos
            rs.getInt("unidades_disponibles"),
            rs.getBoolean("necesita_receta")
        );
    }
}