package com.github.battle.core.scoreboard.sidebar;

import com.github.battle.core.scoreboard.ScoreboardRenderImpl;
import lombok.NonNull;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class ScoreboardAPI {

    private final ScoreboardInfoManager scoreboardInfoManager;

    public ScoreboardAPI(Plugin plugin, String displayName) {
        this(plugin, displayName, new ScoreboardRenderImpl());
    }

    public ScoreboardAPI(@NonNull Plugin plugin, @NonNull String displayName, ScoreboardRender scoreboardRender) {
        final Scoreboard scoreboard = new Scoreboard();

        String pluginName = plugin.getName();
        if(pluginName.length() > 15) {
            pluginName = pluginName.substring(0, 15);
        }

        final ScoreboardObjective scoreboardObjective = scoreboard.registerObjective(pluginName, IScoreboardCriteria.b);
        scoreboardObjective.setDisplayName(displayName);

        this.scoreboardInfoManager = new ScoreboardInfoManager(
          scoreboard,
          scoreboardRender,
          scoreboardObjective
        );
    }

    public ScoreboardAPI setShouldDisplayNameUpdate(boolean shouldDisplayNameUpdate) {
        scoreboardInfoManager.setShouldUpdateDisplayName(shouldDisplayNameUpdate);
        return this;
    }

    public ScoreboardAPI buildScoreboardLines(String... texts) {
        scoreboardInfoManager.buildScoreboardLines(texts);
        return this;
    }

    public void destroyScoreboardInformationToPlayer(Player player) {
        scoreboardInfoManager.destroyScoreboardInformation(player);
    }

    public void setDisplayNameObjective(@NonNull String displayName) {
        scoreboardInfoManager.setDisplayName(displayName);
    }

    /*
    Create listeners for this types, to remove from memory
     */
    public void removePlayerInformationFromCache(Player player) {
        scoreboardInfoManager.removePlayerFromHistoryScoreboard(player);
    }

    public void buildAndShowToPlayer(Player player) {
        scoreboardInfoManager.sendBaseScoreboardInformation(player);
        scoreboardInfoManager.buildAndSendScoreboardInfo(player);
    }
}
