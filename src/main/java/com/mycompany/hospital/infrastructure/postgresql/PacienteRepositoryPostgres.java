package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Paciente;
import com.mycompany.hospital.domain.repository.PacienteRepository;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class PacienteRepositoryPostgres implements PacienteRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Paciente paciente) {
        String sql = "INSERT INTO Paciente(nombre, fecha_nacimiento, direccion, id_medico_asignado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(3, paciente.getDireccion());
            stmt.setObject(4, paciente.getIdMedicoAsignado(), Types.INTEGER);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paciente buscarPorId(int id) {
        String sql = "SELECT * FROM Paciente WHERE id_paciente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    (Integer) rs.getObject("id_medico_asignado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    (Integer) rs.getObject("id_medico_asignado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Paciente paciente) {
        String sql = "UPDATE Paciente SET nombre = ?, fecha_nacimiento = ?, direccion = ?, id_medico_asignado = ? WHERE id_paciente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setDate(2, Date.valueOf(paciente.getFechaNacimiento()));
            stmt.setString(3, paciente.getDireccion());
            stmt.setObject(4, paciente.getIdMedicoAsignado(), Types.INTEGER);
            stmt.setInt(5, paciente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Paciente WHERE id_paciente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Paciente> buscarPorIdMedico(int idMedico) {
        List<Paciente> lista = new ArrayList<>();
        String sql = """
            SELECT DISTINCT p.* FROM paciente p
            INNER JOIN citamedica c ON p.id_paciente = c.id_paciente
            WHERE c.id_medico = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getObject("id_medico_asignado") != null ? rs.getInt("id_medico_asignado") : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Paciente> buscarPorIdConsultorio(int idConsultorio) {
        List<Paciente> lista = new ArrayList<>();
        String sql = """
            SELECT DISTINCT p.* 
            FROM paciente p
            INNER JOIN "citamedica" c ON p.id_paciente = c.id_paciente
            INNER JOIN "medico" m ON c.id_medico = m.id_medico
            WHERE m.id_consultorio = ?
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConsultorio);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getObject("id_medico_asignado") != null ? rs.getInt("id_medico_asignado") : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Paciente> buscarPacientesConEnfermedadCronica() {
        List<Paciente> lista = new ArrayList<>();
        String sql = """
            SELECT DISTINCT p.*
            FROM paciente p
            JOIN citamedica c ON p.id_paciente = c.id_paciente
            JOIN diagnostico d ON c.id_cita = d.id_cita
            JOIN enfermedad e ON d.id_enfermedad = e.id_enfermedad
            WHERE LOWER(e.nombre) LIKE '%diabetes%'
               OR LOWER(e.nombre) LIKE '%hipertensión%'
               OR LOWER(e.nombre) LIKE '%asma%'
               OR LOWER(e.nombre) LIKE '%epoc%'
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getInt("id_paciente"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento").toLocalDate(),
                    rs.getString("direccion"),
                    rs.getObject("id_medico_asignado") != null ? rs.getInt("id_medico_asignado") : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


}
