package com.github.battle.core.database;

import java.sql.Connection;
import java.sql.ResultSet;

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
    <T> T result(String query, DatabaseFunction<ResultSet, T> databaseFunction, Object... objects);

    /**
     * Close connection bridge
     */
    void close();


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
     * @return
     */
    boolean hasConnection();

    /**
     * @param query   execute query, update or some think like that
     * @param objects array to sync with result set
     */
    void execute(String query, Object... objects);
}
