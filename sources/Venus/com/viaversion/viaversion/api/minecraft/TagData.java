/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public final class TagData {
    private final String identifier;
    private final int[] entries;

    public TagData(String string, int[] nArray) {
        this.identifier = string;
        this.entries = nArray;
    }

    public String identifier() {
        return this.identifier;
    }

    public int[] entries() {
        return this.entries;
    }
}

