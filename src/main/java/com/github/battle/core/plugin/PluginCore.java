package com.github.battle.core.plugin;

import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.InventoryFrame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public abstract class PluginCore extends JavaPlugin {

    private final BukkitFrame bukkitFrame = new BukkitFrame(this);

    @Override
    public void onLoad() {
        this.onPluginLoad();
    }

    @Override
    public void onEnable() {
        this.onPluginEnable();
    }

    @Override
    public void onDisable() {
        this.onPluginDisable();
        Bukkit.getScheduler().cancelTasks(this);
        HandlerList.unregisterAll(this);
    }

    public void onPluginLoad() {
        //TODO: Override this method
    }

    public void onPluginEnable() {
        //TODO: Override this method
    }

    public void onPluginDisable() {
        //TODO: Override this method
    }

    public void registerListeners(Listener... listeners) {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    public void registerCommands(Object... commands) {
        bukkitFrame.registerCommands(commands);
    }

    public void registerListenerFromInventory(Plugin plugin) {
        new InventoryFrame(plugin).registerListener();
    }

    public void info(String... messages) {
        final Logger logger = getLogger();
        for (String message : messages) {
            logger.info(message);
        }
    }
}
