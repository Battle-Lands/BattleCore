package com.github.battle_lands.serialization;

import com.github.battle_lands.plugin.Main;
import org.bukkit.World;

public class Location {

    public static org.bukkit.Location unserializeLocation(String serialized) {
        String[] data = serialized.split(";");
        World world = Main.getInstance().getServer().getWorld(data[0]);
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);
        return new org.bukkit.Location(world, x, y, z, yaw, pitch);
    }

    public static String serializeLocation(org.bukkit.Location location) {
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
