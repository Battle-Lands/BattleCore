package com.github.battle.core.plugin;

import com.github.battle.core.common.CredentialRegistry;
import lombok.Getter;
import lombok.NonNull;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.View;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public abstract class PluginCore extends JavaPlugin {

    private final ServicesManager servicesManager = Bukkit.getServicesManager();
    private final BukkitFrame bukkitFrame = new BukkitFrame(this);
    private final ViewFrame viewFrame = new ViewFrame(this);

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

    public void registerInventoryListener(@NonNull View... views) {
        viewFrame.addView(views);
    }

    public <T> void registerService(@NonNull T object) {
        servicesManager.register(
          ((Class<T>) object.getClass()),
          object,
          this,
          ServicePriority.Normal
        );
    }

    public <T> T getService(@NonNull Class<T> clazz) {
        return servicesManager
          .getRegistration(clazz)
          .getProvider();
    }

    public CredentialRegistry getCredentialRegistry() {
        return getService(CredentialRegistry.class);
    }

    public void info(String... messages) {
        final Logger logger = getLogger();
        for (String message : messages) {
            logger.info(message);
        }
    }
}
