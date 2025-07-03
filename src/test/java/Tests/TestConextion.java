package Tests;

import com.mycompany.hospital.infrastructure.postgresql.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConextion {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.getConnection();

            String query = "SELECT id_paciente, nombre, fecha_nacimiento, direccion FROM Paciente";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Listado de pacientes:");
            while (rs.next()) {
                int id = rs.getInt("id_paciente");
                String nombre = rs.getString("nombre");
                String fechaNac = rs.getString("fecha_nacimiento");
                String direccion = rs.getString("direccion");

                System.out.println("ID: " + id + " | Nombre: " + nombre +
                                   " | Fecha Nac: " + fechaNac + " | Direccion: " + direccion);
            }

            rs.close();
            stmt.close();
            conn.close(); // opcional si estás usando conexión singleton

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

