package com.github.battle.scoreboard.sidebar;

import com.github.battle.scoreboard.PacketAccessor;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public final class ScoreEntity {

    private final static Field PACKET_PLAYER_NAME_FIELD;

    static {
        PACKET_PLAYER_NAME_FIELD = PacketAccessor.accessAndGetDeclaredField(
          PacketPlayOutScoreboardScore.class,
          "a"
        );
    }

    private final Scoreboard scoreboard;
    private final ScoreboardObjective scoreboardObjective;

    private final PacketPlayOutScoreboardObjective removeScoreboardObjective;
    private final PacketPlayOutScoreboardObjective createScoreboardObjective;

    private final PacketPlayOutScoreboardDisplayObjective displayScoreboardObjective;

    private final List<ScoreboardScore> scoreboardScores;

    @Setter
    @Accessors(chain = true)
    private ScoreboardRender scoreboardRender;

    public ScoreEntity(@NonNull Plugin plugin, @NonNull String displayName) {
        this.scoreboard = new Scoreboard();

        String pluginName = plugin.getName();
        if(pluginName.length() > 15) {
            pluginName = pluginName.substring(0, 15);
        }

        this.scoreboardObjective = scoreboard.registerObjective(pluginName, IScoreboardCriteria.b);
        scoreboardObjective.setDisplayName(displayName);

        this.removeScoreboardObjective = new PacketPlayOutScoreboardObjective(scoreboardObjective, 1);
        this.createScoreboardObjective = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
        this.displayScoreboardObjective = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);

        this.scoreboardScores = new LinkedList<>();
        this.scoreboardRender = new ScoreboardRender() {
        };
    }

    public ScoreEntity buildScoreboardLines(String... texts) {
        for (int index = (texts.length - 1), position = 0; index >= 0; index--) {
            final String currentText = texts[position++];

            final ScoreboardScore scoreboardScore = new ScoreboardScore(
              scoreboard,
              scoreboardObjective,
              currentText
            );

            scoreboardScore.setScore(index);
            scoreboardScores.add(scoreboardScore);
        }
        return this;
    }

    @SneakyThrows
    public void buildAndShowToPlayer(Player player) {
        final PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketAccessor.sendAllPackets(
          playerConnection,
          removeScoreboardObjective,
          createScoreboardObjective,
          displayScoreboardObjective
        );

        for (ScoreboardScore scoreboardScore : scoreboardScores) {
            final PacketPlayOutScoreboardScore packetPlayOutScoreboardScore = new PacketPlayOutScoreboardScore(scoreboardScore);

            final String render = scoreboardRender.onRender(player, scoreboardScore);
            if (!scoreboardScore.getPlayerName().equalsIgnoreCase(render)) {
                PacketAccessor.updateContextField(
                  packetPlayOutScoreboardScore,
                  PACKET_PLAYER_NAME_FIELD,
                  render
                );
            }

            playerConnection.sendPacket(packetPlayOutScoreboardScore);
        }
    }
}
