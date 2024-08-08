/*
 * Decompiled with CFR 0_132.
 */
package me.napoleon.napoline.guis.Altmanager;

import java.util.ArrayList;
import java.util.List;

import me.napoleon.napoline.utils.client.FileUtil;

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

