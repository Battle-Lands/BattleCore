package com.github.battle.core.scoreboard.sidebar;

import com.github.battle.core.reflection.Reflection;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

@Getter
@SuppressWarnings("all")
@RequiredArgsConstructor
public final class ScoreboardInfoManager {

    private final Scoreboard scoreboard;
    private final ScoreboardObjective scoreboardObjective;

    private final PacketPlayOutScoreboardDisplayObjective displayScoreboardObjective;
    private final ScoreboardRender scoreboardRender;

    private final PacketPlayOutScoreboardObjective createScoreboardObjective;
    private final PacketPlayOutScoreboardObjective removeScoreboardObjective;
    private final PacketPlayOutScoreboardObjective updateScoreboardObjective;

    private final List<ScoreboardScore> scoreboardScores;
    private final LoadingCache<String, ScoreboardPlayer> scoreboardPlayerCache;

    @Setter
    private boolean shouldUpdateDisplayName = false;

    public ScoreboardInfoManager(@NonNull Scoreboard scoreboard, @NonNull ScoreboardRender scoreboardRender, @NonNull ScoreboardObjective scoreboardObjective) {
        this.scoreboard = scoreboard;
        this.scoreboardObjective = scoreboardObjective;
        this.scoreboardRender = scoreboardRender;

        this.removeScoreboardObjective = getScoreboardObjective(1);
        this.updateScoreboardObjective = getScoreboardObjective(2);
        this.createScoreboardObjective = getScoreboardObjective(0);

        this.displayScoreboardObjective = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);

        this.scoreboardScores = new LinkedList<>();
        this.scoreboardPlayerCache = CacheBuilder
          .newBuilder()
          .build(new ScoreboardCacheLoader());
    }

    public void setDisplayName(@NonNull String displayName) {
        scoreboardObjective.setDisplayName(displayName);
        updateScoreboardDisplayObjective();
    }

    public void addScoreboardScore(@NonNull ScoreboardScore scoreboardScore) {
        scoreboardScores.add(scoreboardScore);
    }

    public void buildScoreboardLines(@NonNull String... lines) {
        for (int index = (lines.length - 1), position = 0; index >= 0; index--) {
            final String currentText = lines[position++];

            final ScoreboardScore scoreboardScore = new ScoreboardScore(
              scoreboard,
              scoreboardObjective,
              currentText
            );

            scoreboardScore.setScore(index);
            addScoreboardScore(scoreboardScore);
        }
    }

    public void sendBaseScoreboardInformation(Player player) {
        final ScoreboardPlayer scoreboardPlayer = getScoreboardPlayer(player);
        if(!scoreboardPlayer.isSentScoreboardInformation()) {
            Reflection.sendPackets(
              player,
              removeScoreboardObjective,
              createScoreboardObjective,
              displayScoreboardObjective
            );

            scoreboardPlayer.setSentScoreboardInformation(true);
        }

        if(shouldUpdateDisplayName) {
            Reflection.sendPacket(player, updateScoreboardObjective);
        }
    }

    public void buildAndSendScoreboardInfo(Player player) {
        final PlayerConnection connection = Reflection.getPlayerConnection(player);
        for (ScoreboardScore scoreboardScore : scoreboardScores) {
            buildPartialScoreboardLineInfo(player, connection, scoreboardScore);
        }
    }

    private void buildPartialScoreboardLineInfo(Player player, PlayerConnection connection, ScoreboardScore scoreboardScore) {
        final ScoreboardPlayer scoreboardPlayer = getScoreboardPlayer(player);
        final PacketPlayOutScoreboardScore packetPlayOutScoreboardScore = getNewScoreboardScore(scoreboardScore);

        final int index = scoreboardScore.getScore();
        final String oldLineFromScoreboardHistoryLines = scoreboardPlayer.getOldScoreboardLine(index);

        final String render = scoreboardRender.onRender(player, scoreboardScore);
        if(oldLineFromScoreboardHistoryLines != null) {
            if (render.equalsIgnoreCase(oldLineFromScoreboardHistoryLines)) return;
            connection.sendPacket(removeScoreboardScore(oldLineFromScoreboardHistoryLines));
        }

        scoreboardPlayer.setOldScoreboardLine(render, index);
        Reflection.setNonField(packetPlayOutScoreboardScore, "a", render);

        connection.sendPacket(packetPlayOutScoreboardScore);
    }

    private void updateScoreboardDisplayObjective() {
        Reflection.setNonField(updateScoreboardObjective, "b", scoreboardObjective.getDisplayName());
    }

    private PacketPlayOutScoreboardScore removeScoreboardScore(@NonNull String line) {
        return new PacketPlayOutScoreboardScore(line);
    }

    private PacketPlayOutScoreboardScore getNewScoreboardScore(@NonNull ScoreboardScore scoreboardScore) {
        return new PacketPlayOutScoreboardScore(scoreboardScore);
    }

    private PacketPlayOutScoreboardObjective getScoreboardObjective(int type) {
        return new PacketPlayOutScoreboardObjective(scoreboardObjective, type);
    }

    public void removePlayerFromHistoryScoreboard(Player player) {
        scoreboardPlayerCache.invalidate(player.getName());
    }

    public ScoreboardPlayer getScoreboardPlayer(Player player) {
        return scoreboardPlayerCache.getUnchecked(player.getName());
    }

    public void destroyScoreboardInformation(Player player) {
        Reflection.sendPacket(player, removeScoreboardObjective);
    }
}
