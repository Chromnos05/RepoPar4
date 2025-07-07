package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Medico;
import com.mycompany.hospital.domain.repository.MedicoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Oscar M
 */

public class MedicoRepositoryPostgres implements MedicoRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Medico medico) {
        String sql = "INSERT INTO Medico(nombre, especialidad, id_consultorio) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getEspecialidad());
            stmt.setObject(3, medico.getIdConsultorio(), Types.INTEGER);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Medico medico) {
        String sql = "UPDATE Medico SET nombre=?, especialidad=?, id_consultorio=? WHERE id_medico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getEspecialidad());
            stmt.setObject(3, medico.getIdConsultorio(), Types.INTEGER);
            stmt.setInt(4, medico.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Medico WHERE id_medico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Medico buscarPorId(int id) {
        String sql = "SELECT * FROM Medico WHERE id_medico=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medico(
                    rs.getInt("id_medico"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    (Integer) rs.getObject("id_consultorio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medico> listarTodos() {
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Medico";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Medico(
                    rs.getInt("id_medico"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    (Integer) rs.getObject("id_consultorio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Object[]> obtenerPacientesPorMedicoUltimoAnio() {
        List<Object[]> resultados = new ArrayList<>();
        String sql = """
            SELECT m.nombre, COUNT(c.id_paciente) AS total
            FROM medico m
            JOIN citamedica c ON m.id_medico = c.id_medico
            WHERE c.fecha >= current_date - interval '1 year'
            GROUP BY m.nombre
            ORDER BY total DESC
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total");
                resultados.add(new Object[]{nombre, total});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
