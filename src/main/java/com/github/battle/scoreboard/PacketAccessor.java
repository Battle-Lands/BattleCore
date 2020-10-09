package com.github.battle.scoreboard;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public final class PacketAccessor {

    public static Field accessAndGetDeclaredField(Class<?> clazz, String fieldName) {
        try {
            final Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);

            return declaredField;
        } catch (Exception $) {
            $.printStackTrace();
            return null;
        }
    }

    public static void updateContextField(Object instance, Field field, Object value) {
        try {
            field.set(instance, value);
        } catch (Exception $) {
            $.printStackTrace();
        }
    }

    public static void sendPacket(Player player, Packet<?> packet) {
        sendPacket(((CraftPlayer) player).getHandle().playerConnection, packet);
    }

    public static void sendPacket(PlayerConnection playerConnection, Packet<?> packet) {
        playerConnection.sendPacket(packet);
    }

    public static void sendAllPackets(Player player, Packet<?>... packets) {
        sendAllPackets(((CraftPlayer) player).getHandle().playerConnection, packets);
    }

    public static void sendAllPackets(PlayerConnection playerConnection, Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            sendPacket(playerConnection, packet);
        }
    }
}
