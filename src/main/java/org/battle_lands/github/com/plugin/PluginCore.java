package org.battle_lands.github.com.plugin;

import org.battle_lands.github.com.reflection.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginCore extends JavaPlugin {

    private CommandMap commandMap;

    @Override
    public void onEnable() {
        super.onEnable();
        this.onPluginEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.onPluginDisable();
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerCommands(Command... commands) {
        for (Command command : commands)
            getCommandMap().register("battlelands", command);
    }

    public CommandMap getCommandMap() {
        if (commandMap == null)
            commandMap = Reflections.getField(SimplePluginManager.class, "commandMap");
        return commandMap;
    }

    public abstract void onPluginEnable();

    public abstract void onPluginDisable();
}
