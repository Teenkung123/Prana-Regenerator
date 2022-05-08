package com.teenkung.pranaregenerator.utils;

import java.sql.*;
import java.time.Instant;

import static com.teenkung.pranaregenerator.PranaRegenerator.colorize;

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

    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PranaRegenerator (" +
                    "ID int NOT NULL AUTO_INCREMENT," +
                    "UUID VARCHAR(40)," +
                    "LOGOUT BIGINT(40)," +
                    "PRIMARY KEY (ID)" +
                    ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(colorize("&cCould not create table for some reason try check database connection!"));
        }
    }

    public void sendDummyData() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT LOGOUT FROM PranaRegenerator WHERE UUID = ?");
            statement.setString(1, "DUMMY_DATA");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                PreparedStatement statement1 = connection.prepareStatement("UPDATE PranaRegenerator SET LOGOUT = ? WHERE UUID = ?");
                statement1.setLong(1, Instant.now().getEpochSecond());
                statement1.setString(2, "DUMMY_DATA");
                statement1.executeUpdate();
                statement1.close();
            } else {
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO PranaRegenerator (ID, UUID, LOGOUT) VALUES (" +
                        "default," +
                        "'DUMMY_DATA'," +
                        Instant.now().getEpochSecond() + "," +
                        ");");
                statement1.executeUpdate();
                statement1.close();
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}