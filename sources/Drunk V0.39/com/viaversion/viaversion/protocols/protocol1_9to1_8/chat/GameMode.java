/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

public enum GameMode {
    SURVIVAL(0, "Survival Mode"),
    CREATIVE(1, "Creative Mode"),
    ADVENTURE(2, "Adventure Mode"),
    SPECTATOR(3, "Spectator Mode");

    private final int id;
    private final String text;

    private GameMode(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public static GameMode getById(int id) {
        GameMode[] gameModeArray = GameMode.values();
        int n = gameModeArray.length;
        int n2 = 0;
        while (n2 < n) {
            GameMode gm = gameModeArray[n2];
            if (gm.getId() == id) {
                return gm;
            }
            ++n2;
        }
        return null;
    }
}

