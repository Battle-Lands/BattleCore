package com.github.battle.core.util.format.money;

import lombok.NonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyFormat {

    public static final double LOG = 6.907755278982137D;
    public static final Object[][] VALUES = {
      {"", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D", "UN", "DD", "TR", "QT", "QN", "SD", "SPD", "OD", "ND", "VG", "UVG", "DVG", "TVG", "QTV"},
      {1D, 1000.0D, 1000000.0D, 1.0E9D, 1.0E12D, 1.0E15D, 1.0E18D, 1.0E21D, 1.0E24D, 1.0E27D, 1.0E30D, 1.0E33D, 1.0E36D, 1.0E39D, 1.0E42D, 1.0E45D, 1.0E48D, 1.0E51D, 1.0E54D, 1.0E57D, 1.0E60D, 1.0E63D, 1.0E66D, 1.0E69D, 1.0E72D}
    };
    public static final DecimalFormat FORMAT = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public static String format(double number) {
        if (number == 0) return FORMAT.format(number);
        int index = (int) (Math.log(number) / LOG);
        return FORMAT.format(number / (double) VALUES[1][index]) + VALUES[0][index];
    }

    public static double deform(@NonNull String string) {
        Pattern pattern = Pattern.compile("([A-z]*)([0-9]*)");
        Matcher matcher = pattern.matcher(string);
        List<Object> formats = Arrays.asList(VALUES[0]);
        int i = 0;
        double value = 0;
        String charName = null;
        while (matcher.find()) {
            if (i == 1) {
                charName = matcher.group();
                break;
            }
            value = Double.parseDouble(matcher.group());
            i++;
        }
        if (charName == null) return 0;
        int index = formats.contains(charName) ? formats.indexOf(charName) : 0;
        return value * (double) VALUES[1][index];
    }
}
