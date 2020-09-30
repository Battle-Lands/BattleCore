package org.battle_lands.github.com.plugin;

public class Main extends PluginCore {

    @Override
    public void onPluginEnable() {
        getLogger().info(getDescription().getDescription());
    }
}
