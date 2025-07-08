package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.*;
import com.mycompany.hospital.domain.repository.MedicamentoRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepositoryPostgres implements MedicamentoRepository {

    private final Connection conn = ConexionBD.getConnection();

    // --- MÉTODOS CRUD (SIN CAMBIOS) ---
    @Override
    public void guardar(Medicamento medicamento) {
        String sql = "INSERT INTO Medicamento(nombre, id_laboratorio, unidades_disponibles, necesita_receta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento.getNombre());
            stmt.setObject(2, medicamento.getIdLaboratorio(), Types.INTEGER);
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
    
    // ... (El resto de los métodos CRUD: listarTodos, actualizar, eliminar no cambian)
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


    // --- MÉTODOS DE CONSULTA (CON CORRECCIONES) ---

    @Override
    public List<Contraindicacion> findContraindicacionesById(int idMedicamento) {
        List<Contraindicacion> lista = new ArrayList<>();
        // CORREGIDO: c.id_contraindicacion en lugar de c.id_contra
        String sql = "SELECT c.* FROM Contraindicacion c JOIN Medicamento_Contraindicacion mc ON c.id_contraindicacion = mc.id_contraindicacion WHERE mc.id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToContraindicacion(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<PrincipioActivo> findPrincipiosActivosById(int idMedicamento) {
        List<PrincipioActivo> lista = new ArrayList<>();
        // CORREGIDO: PrincipioActivo en lugar de Principio_Activo
        String sql = "SELECT pa.* FROM PrincipioActivo pa JOIN Medicamento_Principio mp ON pa.id_principio = mp.id_principio WHERE mp.id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToPrincipioActivo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Receta> findRecetasById(int idMedicamento) {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT r.* FROM Receta r JOIN Medicamento_Receta mr ON r.id_receta = mr.id_receta WHERE mr.id_medicamento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedicamento);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                lista.add(mapResultSetToReceta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Laboratorio findLaboratorioById(int idLaboratorio) {
        String sql = "SELECT * FROM Laboratorio WHERE id_laboratorio = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLaboratorio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLaboratorio(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Medicamento> findByPacienteId(int idPaciente) {
        List<Medicamento> lista = new ArrayList<>();
        // CORREGIDO: CitaMedica en lugar de Cita_Medica
        String sql = "SELECT DISTINCT m.* FROM Medicamento m " +
                     "JOIN Medicamento_Receta mr ON m.id_medicamento = mr.id_medicamento " +
                     "JOIN Receta r ON mr.id_receta = r.id_receta " +
                     "JOIN Diagnostico d ON r.id_diagnostico = d.id_diagnostico " +
                     "JOIN CitaMedica cm ON d.id_cita = cm.id_cita " +
                     "WHERE cm.id_paciente = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToMedicamento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Laboratorio findLaboratorioByNombre(String nombre) {
        String sql = "SELECT * FROM Laboratorio WHERE nombre ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLaboratorio(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PrincipioActivo findPrincipioActivoByNombre(String nombre) {
        // CORREGIDO: PrincipioActivo en lugar de Principio_Activo
        String sql = "SELECT * FROM PrincipioActivo WHERE nombre ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToPrincipioActivo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    // --- MÉTODOS DE MAPEO (CON CORRECCIONES) ---

    private Medicamento mapResultSetToMedicamento(ResultSet rs) throws SQLException {
        return new Medicamento(
            rs.getInt("id_medicamento"),
            rs.getString("nombre"),
            (Integer) rs.getObject("id_laboratorio"),
            rs.getInt("unidades_disponibles"),
            rs.getBoolean("necesita_receta")
        );
    }

    private Contraindicacion mapResultSetToContraindicacion(ResultSet rs) throws SQLException {
        // CORREGIDO: id_contraindicacion en lugar de id_contra
        return new Contraindicacion(rs.getInt("id_contraindicacion"), rs.getString("descripcion"));
    }

    private PrincipioActivo mapResultSetToPrincipioActivo(ResultSet rs) throws SQLException {
        return new PrincipioActivo(rs.getInt("id_principio"), rs.getString("nombre"));
    }
    
    private Receta mapResultSetToReceta(ResultSet rs) throws SQLException {
        // CORREGIDO: fecha en lugar de fecha_inicio
        return new Receta(
            rs.getInt("id_receta"),
            rs.getInt("id_diagnostico"),
            rs.getDate("fecha").toLocalDate()
        );
    }

    private Laboratorio mapResultSetToLaboratorio(ResultSet rs) throws SQLException {
        return new Laboratorio(
            rs.getInt("id_laboratorio"),
            rs.getString("nombre"),
            rs.getString("direccion")
        );
    }
}