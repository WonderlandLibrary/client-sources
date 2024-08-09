/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.tokens;

public final class TagTuple {
    private final String handle;
    private final String suffix;

    public TagTuple(String string, String string2) {
        if (string2 == null) {
            throw new NullPointerException("Suffix must be provided.");
        }
        this.handle = string;
        this.suffix = string2;
    }

    public String getHandle() {
        return this.handle;
    }

    public String getSuffix() {
        return this.suffix;
    }
}

