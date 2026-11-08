
package ar.edu.sigdb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    private Conexion() {}

    public static Connection abrir() throws SQLException {

        String url = System.getenv().getOrDefault(
            "SIGDB_URL",
            "jdbc:mysql://localhost:3306/sigdb"
        );

        String usuario = System.getenv().getOrDefault(
            "SIGDB_USER",
            "root"
        );

        String password = System.getenv("SIGDB_PASS");

        if (password == null || password.isEmpty()) {
            throw new SQLException(
                "No se configuro la variable SIGDB_PASS"
            );
        }

        return DriverManager.getConnection(
            url,
            usuario,
            password
        );
    }
}
