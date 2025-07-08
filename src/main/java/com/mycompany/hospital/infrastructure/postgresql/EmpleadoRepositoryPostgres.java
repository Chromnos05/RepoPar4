package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Empleado;
import com.mycompany.hospital.domain.repository.EmpleadoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar M
 */
public class EmpleadoRepositoryPostgres implements EmpleadoRepository {
    private final Connection conn = ConexionBD.getConnection();

    @Override
    public void guardar(Empleado empleado) {
        // Asumiendo que la tabla Empleado tiene las columnas: nombre, cargo
        String sql = "INSERT INTO Empleado(nombre, cargo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getCargo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM Empleado WHERE id_empleado = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmpleado(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Empleado ORDER BY id_empleado ASC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToEmpleado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE Empleado SET nombre = ?, cargo = ? WHERE id_empleado = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getCargo());
            stmt.setInt(3, empleado.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM Empleado WHERE id_empleado = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Empleado mapResultSetToEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
            rs.getInt("id_empleado"),
            rs.getString("nombre"),
            rs.getString("cargo")
        );
    }
    @Override
    public List<Empleado> findConVacacionesUltimoAnio() {
        List<Empleado> lista = new ArrayList<>();
        // Esta consulta selecciona empleados únicos que tienen un registro de vacaciones
        // cuya fecha de inicio es dentro del último año.
        String sql = "SELECT DISTINCT e.* FROM Empleado e " +
                     "JOIN Vacacion v ON e.id_empleado = v.id_empleado " +
                     "WHERE v.fecha_inicio >= NOW() - INTERVAL '1 year'";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSetToEmpleado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

   
}
