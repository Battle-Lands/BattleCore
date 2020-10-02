package com.github.battle_lands.database.requester;

import com.github.battle_lands.database.DatabaseProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLRequester implements DatabaseProvider {

    private final Credentials credentials;
    private Connection connection;

    public MySQLRequester(Credentials credentials) {
        this.credentials = credentials;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + credentials.host + ":" + credentials.port + "/"
                            + credentials.database + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true",
                    credentials.user, credentials.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (this.connection == null || this.connection.isClosed())
                connect();
        } catch (SQLException sqlException) {
            connect();
        }
        return this.connection;
    }

    @Override
    public ResultSet result(String query) {
        try {
            return getConnection().createStatement().executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        try {
            if (this.connection != null)
                this.connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void execute(String query) {
        try {
            if (this.connection != null && !this.connection.isClosed())
                this.connection.createStatement().executeUpdate(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static class Credentials {
        private final String host;
        private final int port;
        private final String user;
        private final String password;
        private final String database;

        public Credentials(String host, int port, String user, String password, String database) {
            this.host = host;
            this.port = port;
            this.user = user;
            this.password = password;
            this.database = database;
        }
    }
}
