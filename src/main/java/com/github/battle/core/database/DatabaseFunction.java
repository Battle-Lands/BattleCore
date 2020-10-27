package com.github.battle.core.database;

import java.util.function.Function;

@FunctionalInterface
public interface DatabaseFunction<S, U> extends Function<S, U> {

    @Override
    default U apply(S instance) {
        try {
            return safetyApply(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gives safety throws block for java streams (lambda)
     *
     * @param instance type generic instance
     * @return function object result
     * @throws Exception may something can't work
     */
    U safetyApply(S instance) throws Exception;
}
