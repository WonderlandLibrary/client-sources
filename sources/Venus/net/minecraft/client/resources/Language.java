/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

public class Language
implements com.mojang.bridge.game.Language,
Comparable<Language> {
    private final String languageCode;
    private final String region;
    private final String name;
    private final boolean bidirectional;

    public Language(String string, String string2, String string3, boolean bl) {
        this.languageCode = string;
        this.region = string2;
        this.name = string3;
        this.bidirectional = bl;
    }

    @Override
    public String getCode() {
        return this.languageCode;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRegion() {
        return this.region;
    }

    public boolean isBidirectional() {
        return this.bidirectional;
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.region);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        return !(object instanceof Language) ? false : this.languageCode.equals(((Language)object).languageCode);
    }

    public int hashCode() {
        return this.languageCode.hashCode();
    }

    @Override
    public int compareTo(Language language) {
        return this.languageCode.compareTo(language.languageCode);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Language)object);
    }
}

