package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.CitaMedica;
import com.mycompany.hospital.domain.repository.CitaMedicaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class CitaMedicaRepositoryPostgres implements CitaMedicaRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(CitaMedica cita) {
        String sql = "INSERT INTO CitaMedica(id_paciente, id_medico, fecha, hora) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getIdPaciente());
            stmt.setInt(2, cita.getIdMedico());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));
            stmt.setString(4, cita.getHora());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(CitaMedica cita) {
        String sql = "UPDATE CitaMedica SET id_paciente=?, id_medico=?, fecha=?, hora=? WHERE id_cita=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cita.getIdPaciente());
            stmt.setInt(2, cita.getIdMedico());
            stmt.setDate(3, Date.valueOf(cita.getFecha()));
            stmt.setString(4, cita.getHora());
            stmt.setInt(5, cita.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM CitaMedica WHERE id_cita=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CitaMedica buscarPorId(int id) {
        String sql = "SELECT * FROM CitaMedica WHERE id_cita=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new CitaMedica(
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("hora")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CitaMedica> listarTodas() {
        List<CitaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM CitaMedica";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new CitaMedica(
                    rs.getInt("id_cita"),
                    rs.getInt("id_paciente"),
                    rs.getInt("id_medico"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getString("hora")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
