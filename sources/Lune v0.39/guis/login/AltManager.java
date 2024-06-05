/*
 * Decompiled with CFR 0_132.
 */
package me.superskidder.lune.guis.login;

import me.superskidder.lune.utils.client.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class AltManager {
    public static List<Alt> alts = new ArrayList<Alt>();;
    static Alt lastAlt;

    public static void init() {
        AltManager.getAlts();
        FileUtil.loadAlts();
    }

    public Alt getLastAlt() {
        return lastAlt;
    }

    public void setLastAlt(Alt alt) {
        lastAlt = alt;
    }


    public static List<Alt> getAlts() {
        return alts;
    }
}

