package com.github.battle.core.scoreboard.sidebar;

import com.google.common.cache.CacheLoader;

@SuppressWarnings("all")
public final class ScoreboardCacheLoader extends CacheLoader<String, ScoreboardPlayer> {

    @Override
    public ScoreboardPlayer load(String player) throws Exception {
        return new ScoreboardPlayer(player);
    }
}
