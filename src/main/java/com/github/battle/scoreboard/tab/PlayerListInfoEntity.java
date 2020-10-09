package com.github.battle.scoreboard.tab;

import com.github.battle.scoreboard.PacketAccessor;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public final class PlayerListInfoEntity {

    private final static String CHAT_SERIALIZER_FORMAT = "{text: '%s'}";
    private final static Field PLAYER_LIST_HEADER_FIELD;
    private final static Field PLAYER_LIST_FOOTER_FIELD;

    static {
        PLAYER_LIST_HEADER_FIELD = PacketAccessor.accessAndGetDeclaredField(
          PacketPlayOutPlayerListHeaderFooter.class,
          "a"
        );

        PLAYER_LIST_FOOTER_FIELD = PacketAccessor.accessAndGetDeclaredField(
          PacketPlayOutPlayerListHeaderFooter.class,
          "b"
        );
    }

    private String[] headerPlayerInfo;
    private String[] footerPlayerInfo;

    public PlayerListInfoEntity setHeaderPlayerInfo(String... headerPlayerInfo) {
        this.headerPlayerInfo = headerPlayerInfo;
        return this;
    }

    public PlayerListInfoEntity setFooterPlayerInfo(String... footerPlayerInfo) {
        this.footerPlayerInfo = footerPlayerInfo;
        return this;
    }

    public void buildAndShowPlayerList(Player player) {
        final PacketPlayOutPlayerListHeaderFooter packetPlayOutPlayerListHeaderFooter = new PacketPlayOutPlayerListHeaderFooter();

        if (headerPlayerInfo != null) {
            String headerText = String.join("\n", headerPlayerInfo);
            headerText = PlaceholderAPI.setPlaceholders(player, headerText);

            PacketAccessor.updateContextField(
              packetPlayOutPlayerListHeaderFooter,
              PLAYER_LIST_HEADER_FIELD,
              serializeChatFormat(headerText)
            );
        }

        if (footerPlayerInfo != null) {
            String footerText = String.join("\n", footerPlayerInfo);
            footerText = PlaceholderAPI.setPlaceholders(player, footerText);

            PacketAccessor.updateContextField(
              packetPlayOutPlayerListHeaderFooter,
              PLAYER_LIST_FOOTER_FIELD,
              serializeChatFormat(footerText)
            );
        }

        PacketAccessor.sendPacket(player, packetPlayOutPlayerListHeaderFooter);
    }

    public IChatBaseComponent serializeChatFormat(String text) {
        return IChatBaseComponent.ChatSerializer.a(
          String.format(CHAT_SERIALIZER_FORMAT, text)
        );
    }
}
