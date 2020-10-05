package com.github.battle.core.database.requester;

import com.github.battle.core.database.DatabaseProvider;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLRequester implements DatabaseProvider {

    private final File file;
    private Connection connection;

    public SQLRequester(File file) {
        this.file = file;
    }

    public Connection getConnection() {
        if (connection == null)
            try {
                Class.forName("org.sqlite.JDBC");
                this.connection = DriverManager.getConnection("jdbc:sqlite" + this.file.getAbsolutePath());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        return connection;
    }

    public ResultSet result(String query) {
        return null;
    }

    public void close() {
        try {
            if (this.connection != null)
                this.connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void execute(String query) {
        try {
            if (this.connection != null && !this.connection.isClosed())
                this.connection.createStatement().executeUpdate(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
