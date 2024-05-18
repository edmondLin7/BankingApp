//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bankingapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection connection = null;

    private ConnectionFactory() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/banking_app";
            String username = "root";
            String password = "edmondlin";

            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException var4) {
                throw new RuntimeException(var4);
            }
        }

        return connection;
    }
}
