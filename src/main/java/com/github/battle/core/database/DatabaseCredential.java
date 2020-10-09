package com.github.battle.core.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class DatabaseCredential {

    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final String database;
}
