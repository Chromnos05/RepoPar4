package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.repository.DiagnosticoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Oscar M
 */
public class DiagnosticoRepositoryPostgres implements DiagnosticoRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Diagnostico diagnostico) {
        String sql = "INSERT INTO Diagnostico(id_cita, id_enfermedad, observaciones) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, diagnostico.getIdCita());
            stmt.setInt(2, diagnostico.getIdEnfermedad());
            stmt.setString(3, diagnostico.getObservaciones());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Diagnostico diagnostico) {
        String sql = "UPDATE Diagnostico SET id_cita=?, id_enfermedad=?, observaciones=? WHERE id_diagnostico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, diagnostico.getIdCita());
            stmt.setInt(2, diagnostico.getIdEnfermedad());
            stmt.setString(3, diagnostico.getObservaciones());
            stmt.setInt(4, diagnostico.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Diagnostico WHERE id_diagnostico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Diagnostico buscarPorId(int id) {
        String sql = "SELECT * FROM Diagnostico WHERE id_diagnostico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Diagnostico(
                    rs.getInt("id_diagnostico"),
                    rs.getInt("id_cita"),
                    rs.getInt("id_enfermedad"),
                    rs.getString("observaciones")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Diagnostico> listarTodos() {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Diagnostico";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Diagnostico(
                    rs.getInt("id_diagnostico"),
                    rs.getInt("id_cita"),
                    rs.getInt("id_enfermedad"),
                    rs.getString("observaciones")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
