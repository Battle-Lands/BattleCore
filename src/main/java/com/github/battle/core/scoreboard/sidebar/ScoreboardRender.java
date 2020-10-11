package com.github.battle.core.scoreboard.sidebar;

import lombok.NonNull;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import org.bukkit.entity.Player;

public interface ScoreboardRender {

    String onRender(@NonNull Player player, @NonNull ScoreboardScore scoreboardScore);
}
