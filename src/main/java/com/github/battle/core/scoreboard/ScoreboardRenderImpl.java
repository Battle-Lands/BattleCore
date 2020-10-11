package com.github.battle.core.scoreboard;

import com.github.battle.core.scoreboard.sidebar.ScoreboardRender;
import lombok.NonNull;
import me.clip.placeholderapi.PlaceholderAPI;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import org.bukkit.entity.Player;

public final class ScoreboardRenderImpl implements ScoreboardRender {

    @Override
    public String onRender(@NonNull Player player, @NonNull ScoreboardScore scoreboardScore) {
        return PlaceholderAPI.setPlaceholders(player, scoreboardScore.getPlayerName());
    }
}
