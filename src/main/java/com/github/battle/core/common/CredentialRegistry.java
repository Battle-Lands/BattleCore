package com.github.battle.core.common;

import com.github.battle.core.database.DatabaseCredential;
import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;

public final class CredentialRegistry {

    private final FileConfiguration configuration;
    private final DatabaseCredential mysqlCredential;

    public CredentialRegistry(FileConfiguration configuration) {
        this.configuration = configuration;
        this.mysqlCredential = loadFromSection("mysql_database");
    }

    public DatabaseCredential loadFromSection(@NonNull String section) {
        return DatabaseCredential.fromConfigurationSection(
          configuration.getConfigurationSection(section)
        );
    }

    public DatabaseCredential getMysqlCredential() {
        return mysqlCredential.clone();
    }
}
