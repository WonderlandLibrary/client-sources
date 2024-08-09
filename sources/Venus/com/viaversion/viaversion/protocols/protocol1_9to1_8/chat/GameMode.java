/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

public enum GameMode {
    SURVIVAL(0, "Survival Mode"),
    CREATIVE(1, "Creative Mode"),
    ADVENTURE(2, "Adventure Mode"),
    SPECTATOR(3, "Spectator Mode");

    private final int id;
    private final String text;

    private GameMode(int n2, String string2) {
        this.id = n2;
        this.text = string2;
    }

    public int getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public static GameMode getById(int n) {
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode.getId() != n) continue;
            return gameMode;
        }
        return null;
    }
}

