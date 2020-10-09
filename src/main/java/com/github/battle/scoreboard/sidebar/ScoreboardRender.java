package com.github.battle.scoreboard.sidebar;

import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import org.bukkit.entity.Player;

public abstract class ScoreboardRender {

    public String onRender(@NonNull Player player, @NonNull ScoreboardScore scoreboardScore) {
        return PlaceholderAPI.setPlaceholders(player, scoreboardScore.getPlayerName());
    }
}
