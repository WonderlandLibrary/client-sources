package com.shroomclient.shroomclientnextgen.util;

public class TimerUtil {

    private static float tps = 20;

    public static float getMillisPerTick() {
        return 1000f / tps;
    }

    public static float getMulti() {
        return tps / 20f;
    }

    public static void setTPS(float tps) {
        TimerUtil.tps = tps;
    }

    public static void resetTPS() {
        setTPS(20f);
    }

    public static void setTPSMulti(float multi) {
        setTPS(20 * multi);
    }
}
