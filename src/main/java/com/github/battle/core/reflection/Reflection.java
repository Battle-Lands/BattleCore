package com.github.battle.core.reflection;

import lombok.NonNull;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

/**
 * @author Sasuked
 */
public class Reflection {

    public static boolean setField(@NonNull Object instance, @NonNull String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            setField(instance, field, value);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

<<<<<<< HEAD
    public static void setNonField(@NonNull Object instance, @NonNull String fieldName, Object value) {
=======
    public static void setNonField(Object instance, String fieldName, Object value) {
>>>>>>> 632babbd599d38509b7f207d5ad5dade431a1d5b
        try {
            setField(instance, fieldName, value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static boolean setField(Object instance, Field field, Object value) {
        try {
            if (!field.isAccessible()) field.setAccessible(true);
            field.set(instance, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

<<<<<<< HEAD
    public static <T> T getField(Object instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
=======
    public static <T> T getField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        try {
            final Field declaredField = clazz.getDeclaredField(fieldName);
            if (!declaredField.isAccessible()) declaredField.setAccessible(true);
            return ((T) declaredField.get(null));
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static <T> T getField(@NonNull Object instance, @NonNull String fieldName) {
        try {
            Field declaredField = instance.getClass().getDeclaredField(fieldName);
            if (!declaredField.isAccessible()) declaredField.setAccessible(true);
            return (T) declaredField.get(instance);
        } catch (Exception exception) {
            exception.printStackTrace();
>>>>>>> 632babbd599d38509b7f207d5ad5dade431a1d5b
            return null;
        }
    }

    public static Field[] getFields(Object instance) {
        return instance.getClass().getDeclaredFields();
    }

    public static void sendPacket(Player player, Packet<?> packet) {
        final PlayerConnection connection = getPlayerConnection(player);
        connection.sendPacket(packet);
    }

    public static void sendPackets(Player player, Packet<?>... packets) {
        final PlayerConnection connection = getPlayerConnection(player);
        for (Packet<?> packet : packets) {
            connection.sendPacket(packet);
        }
    }

    public static PlayerConnection getPlayerConnection(Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection;
    }
}
