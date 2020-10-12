package com.github.battle.core.builder;

import com.github.battle.core.reflection.ReflectUtil;
import com.github.battle.core.reflection.Reflection;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TextBuilder {

    private String title;
    private String subtitle;
    private int fadeIn;
    private int show;
    private int fadeOut;

    public TextBuilder() {
        this.fadeIn = 20;
        this.show = 60;
        this.fadeOut = 20;
    }

    public TextBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TextBuilder setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public TextBuilder setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn * 20;
        return this;
    }

    public TextBuilder setShow(int show) {
        this.show = show * 20;
        return this;
    }

    public TextBuilder setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut * 20;
        return this;
    }

    public TextBuilder build(Player player) {
        try {
            Class iChatBaseComponent = ReflectUtil.getNMSClass("IChatBaseComponent");
            Class packetPlayOutTitle = ReflectUtil.getNMSClass("PacketPlayOutTitle");

            Constructor<?> constructor = packetPlayOutTitle.getConstructor(
              packetPlayOutTitle.getDeclaredClasses()[0],
              iChatBaseComponent,
              int.class, int.class, int.class
            );
            String titleMessage = title == null ? "" : title;
            Method method = iChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class);
            Object title = method.invoke(null, "{\"text\": \"" + titleMessage + "\"}");
            Object packet = constructor.newInstance(
              packetPlayOutTitle.getDeclaredClasses()[0].getField("TITLE").get(null),
              title, fadeIn, show, fadeOut
            );
            Reflection.sendPacket(player, (Packet<?>) packet);
            if (subtitle != null) {
                Object subtitle = method.invoke(null, "{\"text\": \"" + this.subtitle + "\"}");
                packet = constructor.newInstance(
                  packetPlayOutTitle.getDeclaredClasses()[0].getField("SUBTITLE").get(null),
                  subtitle, fadeIn, show, fadeOut
                );
                Reflection.sendPacket(player, (Packet<?>) packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
