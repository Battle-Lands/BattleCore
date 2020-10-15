package com.github.battle.core.database.requester;

import com.github.battle.core.database.DatabaseCredential;
import com.github.battle.core.database.DatabaseFunction;
import com.github.battle.core.database.DatabaseProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;

@RequiredArgsConstructor
public class MySQLRequester implements DatabaseProvider {

    private final static String MYSQL_URI_CONNECTION = "jdbc:mysql://%s:%s/%s" +
      "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";

    private final DatabaseCredential credential;
    private Connection connection;

    @SneakyThrows
    public MySQLRequester connect() {
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
    public <T> T result(String query, DatabaseFunction<ResultSet, T> databaseFunction, Object... objects) {
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
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void execute(String query, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementObjects(
              statement,
              objects
            ).execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public PreparedStatement setStatementObjects(PreparedStatement statement, Object[] objects) throws SQLException {
        for (int index = 0; index < objects.length; index++) {
            statement.setObject(index+1, objects[index]);
        }
        return statement;
    }
}
