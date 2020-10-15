package com.github.battle.core.common;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface ModelAdapter<T, S> {

    /**
     * Provides base for adapters
     *
     * @param instance to adapt instance
     * @return instance of adapted object
     * @exception Exception safety block
     */
    T adaptModel(@Nullable S instance) throws Exception;
}
