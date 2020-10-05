package com.github.battle.core.builders;

import com.google.common.base.Strings;

public class ProgressBuilder {

    private final int current;
    private final float percent;
    private int max;
    private int totalBars;
    private char symbol;
    /**
     * Storage two colors
     * <p>
     * Color[0] return completedColor
     * Color[1] return uncompletedColor
     */
    private String[] color;

    public ProgressBuilder(int current) {
        this.current = current;
        this.max = 100;
        this.totalBars = 40;
        this.symbol = '|';
        this.color = new String[]{"§e", "§7"};
        this.percent = (float) current / max;
    }

    public ProgressBuilder max(int max) {
        this.max = max;
        return this;
    }

    public ProgressBuilder totalBars(int totalBars) {
        this.totalBars = totalBars;
        return this;
    }

    public ProgressBuilder symbolChar(char symbol) {
        this.symbol = symbol;
        return this;
    }

    public ProgressBuilder color(String completedColor, String notCompletedColor) {
        this.color = new String[]{completedColor, notCompletedColor};
        return this;
    }

    public ProgressBuilder completedColor(String completedColor) {
        this.color = new String[]{completedColor, this.color[1]};
        return this;
    }

    public ProgressBuilder notCompletedColor(String notCompletedColor) {
        this.color = new String[]{this.color[0], notCompletedColor};
        return this;
    }

    public double getPercent() {
        return Math.round(percent * 10.0) / 10.0;
    }

    public String build() {
        int progressBars = (int) (totalBars * percent);
        return Strings.repeat("" + color[0] + symbol, progressBars)
          + Strings.repeat("" + color[1] + symbol, totalBars - progressBars);
    }

}
