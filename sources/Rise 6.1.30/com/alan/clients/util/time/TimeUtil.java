package com.alan.clients.util.time;

public class TimeUtil {

    public static String humanizeTime(long time, boolean decimal) {
        if (time <= 0) {
            return decimal ? "0.0s" : "0s";
        }

        String text;

        if (time < 60000) {
            text = decimal ? String.format("%.1fs", time / 1000.0) : time / 1000 + "s";
        } else if (time < 3600000) {
            text = time / 60000 + "m, " + time % 60000 / 1000 + "s";
        } else if (time < 86400000) {
            text = time / 3600000 + "h, " + time % 3600000 / 60000 + "m, " + time % 60000 / 1000 + "s";
        } else {
            text = time / 86400000 + "d, " + time % 86400000 / 3600000 + "h, " + time % 3600000 / 60000 + "m, " + time % 60000 / 1000 + "s";
        }

        return text;
    }
}