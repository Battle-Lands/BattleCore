package com.github.battle.core.reflection;

import lombok.NonNull;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by iso2013 on 10/15/19.
 */
public class ReflectUtil {

    private static final String VERSION;
    private static final String NMS_PACKAGE_PATH = "net.minecraft.server.%s.%s";
    private static final String CRAFT_BUKKIT_PACKAGE_PATH = "org.bukkit.craftbukkit.%s.%s";

    static {
        String packageVersion = Bukkit.getServer().getClass().getPackage().getName();
        packageVersion = packageVersion.substring(packageVersion.lastIndexOf('.') + 1);
        VERSION = packageVersion;
    }

    public static Class<?> getNMSClass(@NonNull String name) {
        try {
            return Class.forName(String.format(NMS_PACKAGE_PATH, VERSION, name));
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    public static Class<?> getCraftBukkitClass(@NonNull String name) {
        try {
            return Class.forName(String.format(CRAFT_BUKKIT_PACKAGE_PATH, VERSION, name));
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    public static Field getField(@NonNull Class<?> clazz, @NonNull String name) {
        try {
            final Field declaredField = clazz.getDeclaredField(name);
            if (!declaredField.isAccessible()) declaredField.setAccessible(true);
            return declaredField;
        } catch (NoSuchFieldException ignored) {
            return null;
        }
    }

    public static Field getField(@NonNull Class<?> clazz, @NonNull Class<?> type) {
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getType() == type) {
                if (!declaredField.isAccessible()) declaredField.setAccessible(true);
                return declaredField;
            }
        }
        return null;
    }

    public static Method getMethod(@NonNull Class<?> clazz, @NonNull String name, Class<?>... params) {
        try {
            Method declaredMethod = clazz.getDeclaredMethod(name, params);
            if (!declaredMethod.isAccessible()) declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException ignored) {
            return null;
        }
    }

    public static Method getMethod(@NonNull Class<?> clazz, @NonNull Class<?> returnType) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (declaredMethod.getReturnType() == returnType) {
                if (!declaredMethod.isAccessible()) declaredMethod.setAccessible(true);
                return declaredMethod;
            }
        }
        return null;
    }

    public static Constructor<?> getConstructor(@NonNull Class<?> clazz, Class<?>... classes) {
        try {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(classes);
            if (!declaredConstructor.isAccessible()) declaredConstructor.setAccessible(true);
            return declaredConstructor;
        } catch (NoSuchMethodException ignored) {
            return null;
        }
    }

    public static int getVersionNumber() {
        String number = VERSION.substring(VERSION.indexOf('_') + 1, VERSION.lastIndexOf('_'));
        return Integer.parseInt(number);
    }
}
