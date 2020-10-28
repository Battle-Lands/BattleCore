package com.github.battle.core.plugin.configuration;

import lombok.NonNull;
import org.bukkit.configuration.file.FileConfiguration;

public interface Configuration<T> {

    T getEntry(@NonNull String path);

    T getSectionEntry(@NonNull String path);

    T getEntry(@NonNull String path, String[] replaces);

    FileConfiguration getConfiguration();

}
