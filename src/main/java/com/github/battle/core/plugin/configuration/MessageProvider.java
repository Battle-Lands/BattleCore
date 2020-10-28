package com.github.battle.core.plugin.configuration;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageProvider implements Configuration<String> {

    @Getter
    private final FileConfiguration configuration;
    public MessageProvider(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public String getEntry(String path) {
        return configuration.getString(path).replace("&", "§");
    }

    @Override
    public String getSectionEntry(@NonNull String path) {
        final String[] args = path.split("\\.");
        return configuration
          .getConfigurationSection(args[0])
          .getString(args[1]);
    }
}
