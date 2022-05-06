package com.teenkung.pranaregenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    public void Connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + ConfigLoader.getDatabaseHost() + ":" + ConfigLoader.getDatabasePort() + "/" + ConfigLoader.getDatabaseDatabase() + "?useSSL=false",
                ConfigLoader.getDatabaseUsername(),
                ConfigLoader.getDatabasePassword()
        );
    }

    public Connection getConnection() { return connection; }

    public void Disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return connection != null;
    }


}