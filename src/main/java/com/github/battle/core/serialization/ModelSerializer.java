package com.github.battle.core.serialization;

import javax.annotation.Nullable;

public interface ModelSerializer<T> {

    /**
     * Provides base for serializers
     *
     * @param instance to serialize
     * @throws Exception safety block
     */
    void serializeModel(@Nullable T instance) throws Exception;
}
