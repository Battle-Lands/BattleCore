package com.github.battle.core.database;

import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kewilleen Gomes
 * @website https://github.com/kewilleen
 */
public interface DatabaseProvider {

    /**
     * Get current connection
     *
     * @return MySQL or SQL connection
     */
    Connection getConnection();

    /**
     * Return result from connection query
     *
     * @param query            line structure to execute in sql
     * @param databaseFunction executes an functional function from java 8
     * @param objects          array to sync with result set
     * @param <T>              type of return object
     * @return result from searched
     */
    <T> T result(@NonNull String query, DatabaseFunction<ResultSet, T> databaseFunction, Object... objects);

    /**
     * Close connection bridge
     */
    void close();

    /**
     * Execute updates queries with return update state
     *
     * @param query   mysql query
     * @param objects objects to sync with statement
     * @return state (0 for nothing, 1 for any, -1 for error)
     */
    int executeUpdate(@NonNull String query, Object... objects);

    /**
     * Use to bridge connection between your plugin and mysql server
     *
     * @return actually instance from provider
     */
    DatabaseProvider connect();

    /**
     * Verify if jdbc has connection bridge
     * You need to use {@link #connect() connect} method at least once;
     *
     * @return if has any connection
     */
    boolean hasConnection();

    /**
     * @param query   execute query, update or some think like that
     * @param objects array to sync with result set
     */
    void execute(@NonNull String query, Object... objects);

    /**
     * Sync objects with statement
     *
     * @param statement instance of statement
     * @param objects   array of objects to sync within
     * @return instance of statement
     * @throws SQLException {@link PreparedStatement#setObject(int, Object) setObject} can throws errors
     */
    default PreparedStatement setStatementObjects(PreparedStatement statement, Object[] objects) throws SQLException {
        for (int index = 0; index < objects.length; index++) {
            statement.setObject(index + 1, objects[index]);
        }
        return statement;
    }
}
