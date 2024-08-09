/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.Optional;

public enum SoundSource {
    MASTER("master", 0),
    MUSIC("music", 1),
    RECORD("record", 2),
    WEATHER("weather", 3),
    BLOCK("block", 4),
    HOSTILE("hostile", 5),
    NEUTRAL("neutral", 6),
    PLAYER("player", 7),
    AMBIENT("ambient", 8),
    VOICE("voice", 9);

    private final String name;
    private final int id;

    private SoundSource(String string2, int n2) {
        this.name = string2;
        this.id = n2;
    }

    public static Optional<SoundSource> findBySource(String string) {
        for (SoundSource soundSource : SoundSource.values()) {
            if (!soundSource.name.equalsIgnoreCase(string)) continue;
            return Optional.of(soundSource);
        }
        return Optional.empty();
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}

