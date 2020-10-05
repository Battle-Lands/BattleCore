package com.github.battle.core.plugin;

public class Main extends PluginCore {

    @Override
    public void onPluginEnable() {
        getLogger().info(getDescription().getDescription());
    }

}
