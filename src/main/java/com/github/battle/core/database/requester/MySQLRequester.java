package com.github.battle.core.database.requester;

import com.github.battle.core.database.DatabaseCredential;
import com.github.battle.core.database.DatabaseFunction;
import com.github.battle.core.database.DatabaseProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.*;

@RequiredArgsConstructor
public final class MySQLRequester implements DatabaseProvider {

    private final static String MYSQL_URI_CONNECTION = "jdbc:mysql://%s:%s/%s" +
      "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useTimezone=true&serverTimezone=America/Sao_Paulo";

    private final DatabaseCredential credential;
    private Connection connection;

    @Override
    public <T> T result(@NonNull String query, DatabaseFunction<ResultSet, T> databaseFunction, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementObjects(statement, objects);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet != null && resultSet.next()
                  ? databaseFunction.apply(resultSet)
                  : databaseFunction.apply(null);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void executeQueries(@NonNull String... queries) {
        for (String query : queries) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(query);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void execute(@NonNull String query, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementObjects(
              statement,
              objects
            ).execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int executeUpdate(@NonNull String query, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return setStatementObjects(
              statement,
              objects
            ).executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    @Override
    public MySQLRequester connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String mysqlConnectionUri = String.format(
              MYSQL_URI_CONNECTION,
              credential.getHost(),
              credential.getPort(),
              credential.getDatabase()
            );

            this.connection = DriverManager.getConnection(
              mysqlConnectionUri,
              credential.getUser(),
              credential.getPassword()
            );
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean hasConnection() {
        return getConnection() != null;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) return connection;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
