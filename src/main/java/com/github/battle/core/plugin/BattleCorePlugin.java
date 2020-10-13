package com.github.battle.core.plugin;

import com.github.battle.core.database.DatabaseCredential;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public class BattleCorePlugin extends PluginCore {

    private DatabaseCredential baseMysqlCredential;

    @Override
    public void onPluginEnable() {
        saveDefaultConfig();
        info(getDescription().getDescription());

        final FileConfiguration config = getConfig();
        baseMysqlCredential = DatabaseCredential.fromConfigurationSection(
          config.getConfigurationSection("mysql_database")
        );
    }
}
