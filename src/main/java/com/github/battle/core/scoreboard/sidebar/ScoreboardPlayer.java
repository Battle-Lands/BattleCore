package com.github.battle.core.scoreboard.sidebar;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public final class ScoreboardPlayer {

    private final String playerName;

    private final String[] scoreboardLines = new String[16];

    @Setter
    private boolean sentScoreboardInformation = false;

    public void setOldScoreboardLine(@NonNull String value, int index) {
        scoreboardLines[index] = value;
    }

    public String getOldScoreboardLine(int index) {
        return scoreboardLines[index];
    }
}
