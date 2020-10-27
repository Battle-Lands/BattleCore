package com.github.battle.core.plugin.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageProvider implements Configuration<String> {

    private final FileConfiguration configuration;

    public MessageProvider(File file) {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public String getEntry(String path) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
    }

    @Override
    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
