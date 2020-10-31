package com.github.battle.core.plugin;

import com.github.battle.core.common.CredentialRegistry;
import com.github.battle.core.util.format.date.DateFormat;
import lombok.Getter;
import lombok.NonNull;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.InventoryFrame;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.TimeZone;
import java.util.logging.Logger;

@Getter
public abstract class PluginCore extends JavaPlugin {

    private final ServicesManager servicesManager = Bukkit.getServicesManager();
    private final BukkitFrame bukkitFrame = new BukkitFrame(this);

    @Override
    public void onLoad() {
        this.onPluginLoad();
    }

    @Override
    public void onEnable() {
        TimeZone.setDefault(DateFormat.TIMEZONE);
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

    public <T> void registerService(@NonNull T object) {
        servicesManager.register(
          ((Class<T>) object.getClass()),
          object,
          this,
          ServicePriority.Normal
        );
    }

    public <T> void registerService(Class<T> clazz, T obj) {
        servicesManager.register(clazz, obj, this, ServicePriority.Normal);
    }

    public <T> void registerService(Class<T> clazz, T obj, Plugin plugin) {
        servicesManager.register(clazz, obj, plugin, ServicePriority.Normal);
    }

    public <T> void registerService(Class<T> clazz, T obj, Plugin plugin, ServicePriority priority) {
        servicesManager.register(clazz, obj, plugin, priority);
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

    public static OfflinePlayer getOfflinePlayer(@NonNull String name) {
        final Player player = Bukkit.getPlayer(name);
        if (player != null) return player;

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.getName().equalsIgnoreCase(name)) {
                return offlinePlayer;
            }
        }
        return null;
    }
}
