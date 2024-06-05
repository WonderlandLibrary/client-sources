/*
 * Decompiled with CFR 0.152.
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

    private SoundSource(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static Optional<SoundSource> findBySource(String source) {
        SoundSource[] soundSourceArray = SoundSource.values();
        int n = soundSourceArray.length;
        int n2 = 0;
        while (n2 < n) {
            SoundSource item = soundSourceArray[n2];
            if (item.name.equalsIgnoreCase(source)) {
                return Optional.of(item);
            }
            ++n2;
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

