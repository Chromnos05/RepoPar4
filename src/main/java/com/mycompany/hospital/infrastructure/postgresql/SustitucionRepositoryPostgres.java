package com.mycompany.hospital.infrastructure.postgresql;

import com.mycompany.hospital.domain.model.Sustitucion;
import com.mycompany.hospital.domain.repository.SustitucionRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Oscar M
 */
public class SustitucionRepositoryPostgres implements SustitucionRepository {

    private final Connection conn = ConexionBD.getConnection();

    @Override
    public List<Sustitucion> buscarPorMedicoSustituto(int idMedico) {
        List<Sustitucion> lista = new ArrayList<>();
        String sql = "SELECT * FROM \"Sustitucion\" WHERE id_medico_sustituto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMedico);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Sustitucion(
                    rs.getInt("id_sustitucion"),
                    rs.getInt("id_medico_titular"),
                    rs.getInt("id_medico_sustituto"),
                    rs.getDate("fecha").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
