package com.teenkung.pranaregenerator.utils;

import java.sql.*;

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
        Database database = new Database();
        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PranaRegenerator (" +
                    "ID int NOT NULL AUTO_INCREMENT" +
                    "UUID VARCHAR(40)" +
                    "LOGOUT BIGINT(40)" +
                    ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(colorize("&cCould not create table for some reason try check database connection!"));
        }
    }


}