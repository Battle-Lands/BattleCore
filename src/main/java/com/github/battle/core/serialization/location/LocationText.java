package com.github.battle.core.serialization.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationText {

    public static Location unserializeLocation(String serialized) {
        String[] data = serialized.split(";");
        World world = Bukkit.getWorld(data[0]);
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String serializeLocation(Location location) {
        String[] data = {
          location.getWorld().getName(),
          Double.toString(location.getX()),
          Double.toString(location.getY()),
          Double.toString(location.getZ()),
          Float.toString(location.getYaw()),
          Float.toString(location.getPitch())
        };
        return String.join(";", data);
    }

}
