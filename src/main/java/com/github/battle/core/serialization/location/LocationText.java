package com.github.battle.core.serialization.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationText {

    public static Location deserializeLocation(String serialized) {
        final String[] data = serialized.split(";");
        final World world = Bukkit.getWorld(data[0]);

        final double x = Double.parseDouble(data[1]);
        final double y = Double.parseDouble(data[2]);
        final double z = Double.parseDouble(data[3]);

        final float yaw = Float.parseFloat(data[4]);
        final float pitch = Float.parseFloat(data[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String serializeLocation(Location location) {
        final String[] stringData = {
          location.getWorld().getName(),
          String.valueOf(location.getBlockX()),
          String.valueOf(location.getBlockY()),
          String.valueOf(location.getBlockZ()),
          String.valueOf(Math.round(location.getYaw())),
          String.valueOf(Math.round(location.getPitch()))
        };

        return String.join(";", stringData);
    }

}
