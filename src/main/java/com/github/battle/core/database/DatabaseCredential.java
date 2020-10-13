package com.github.battle.core.database;

import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.configuration.ConfigurationSection;

@Data
@Builder
@Accessors(chain = true)
public final class DatabaseCredential implements Cloneable {

    public static DatabaseCredential fromConfigurationSection(@NonNull ConfigurationSection section) {
        return DatabaseCredential.builder()
          .host(section.getString("host"))
          .port(section.getInt("port"))
          .user(section.getString("user"))
          .password(section.getString("password"))
          .database(section.getString("database"))
          .build();
    }

    private String host;
    private int port;
    private String user;
    private String password;
    private String database;

    @Override
    public DatabaseCredential clone(){
        try {
            return ((DatabaseCredential) super.clone());
        } catch (CloneNotSupportedException ignored) {
            return null;
        }
    }
}
