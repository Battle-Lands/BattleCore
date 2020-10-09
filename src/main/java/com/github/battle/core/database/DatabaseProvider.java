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
     * @param query line structure to execute in sql
     * @return result from searched
     */
    <T> T result(String query, DatabaseFunction<ResultSet, T> databaseFunction, Object... objects);

    /**
     * Close connection
     */
    void close();

    /**
     * @param query execute query, update or some think like that
     */
    void execute(String query, Object... objects);
}
