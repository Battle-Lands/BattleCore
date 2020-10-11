package com.github.battle.core.scoreboard.tab;

import com.github.battle.core.reflection.Reflection;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.entity.Player;

public final class PlayerListInfoEntity {

    private final static String CHAT_SERIALIZER_FORMAT = "{text: '%s'}";

    @Setter
    @Accessors(chain = true)
    private String[] headerPlayerInfo, footerPlayerInfo;

    public void buildAndShowPlayerList(Player player) {
        final PacketPlayOutPlayerListHeaderFooter packet = getPacketPlayerList();
        if (headerPlayerInfo != null) {
            updatePacketComponentInformation(
              player,
              packet,
              "a",
              headerPlayerInfo
            );
        }

        if (footerPlayerInfo != null) {
            updatePacketComponentInformation(
              player,
              packet,
              "b",
              footerPlayerInfo
            );
        }

        Reflection.sendPacket(player, packet);
    }

    public PacketPlayOutPlayerListHeaderFooter getPacketPlayerList() {
        return new PacketPlayOutPlayerListHeaderFooter();
    }

    public void updatePacketComponentInformation(Player player, PacketPlayOutPlayerListHeaderFooter packet, @NonNull String targetField, @NonNull String... texts) {
        String text = String.join("\n", texts);
        text = PlaceholderAPI.setPlaceholders(player, text);

        Reflection.setNonField(
          packet,
          targetField,
          serializeChatFormat(text)
        );
    }

    public IChatBaseComponent serializeChatFormat(String text) {
        return IChatBaseComponent.ChatSerializer.a(
          String.format(CHAT_SERIALIZER_FORMAT, text)
        );
    }
}
