package com.mycompany.hospital.infrastructure.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

/**
 *
 * @author Oscar M
 */
public class ConexionBD {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("application.properties");
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
