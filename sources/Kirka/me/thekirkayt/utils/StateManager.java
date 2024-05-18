/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

public class StateManager {
    private static boolean offsetLastPacketAura;

    public static boolean offsetLastPacketAura() {
        return offsetLastPacketAura;
    }

    public static void setOffsetLastPacketAura(boolean offsetLastPacketAura) {
        StateManager.offsetLastPacketAura = offsetLastPacketAura;
    }
}

