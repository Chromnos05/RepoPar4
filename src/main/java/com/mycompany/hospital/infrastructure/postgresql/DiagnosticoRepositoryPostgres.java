package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Diagnostico;
import com.mycompany.hospital.domain.repository.DiagnosticoRepository;

import java.sql.*;
import java.time.LocalDate;
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
    
    @Override
    public List<Diagnostico> buscarPorIdPaciente(int idPaciente) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = """
            SELECT d.* FROM "diagnostico" d
            INNER JOIN "citamedica" c ON d.id_cita = c.id_cita
            WHERE c.id_paciente = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
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
    
    @Override
    public List<Diagnostico> buscarPorRangoDeFechas(LocalDate desde, LocalDate hasta) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = """
            SELECT d.* FROM "diagnostico" d
            INNER JOIN "citamedica" c ON d.id_cita = c.id_cita
            WHERE c.fecha BETWEEN ? AND ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(desde));
            stmt.setDate(2, Date.valueOf(hasta));
            ResultSet rs = stmt.executeQuery();
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
    
    @Override
    public List<Diagnostico> buscarPorMedicoYFecha(int idMedico, LocalDate fecha) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = """
            SELECT d.*
            FROM diagnostico d
            JOIN citamedica c ON d.id_cita = c.id_cita
            WHERE c.id_medico = ? AND c.fecha = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);
            stmt.setDate(2, Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
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
