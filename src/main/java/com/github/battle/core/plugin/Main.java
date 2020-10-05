package com.github.battle.core.plugin;

public class Main extends PluginCore {

    private static Main instance;

    @Override
    public void onPluginEnable() {
        instance = this;
        getLogger().info(getDescription().getDescription());
    }

    public static Main getInstance() {
        return instance;
    }
}
