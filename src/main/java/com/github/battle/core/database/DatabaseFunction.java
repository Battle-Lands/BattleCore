package com.github.battle.core.database;

import java.util.function.Function;

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

    U safetyApply(S instance) throws Exception;
}
