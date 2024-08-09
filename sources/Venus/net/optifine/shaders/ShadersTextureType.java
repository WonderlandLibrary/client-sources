/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

public enum ShadersTextureType {
    NORMAL("_n"),
    SPECULAR("_s");

    private String suffix;

    private ShadersTextureType(String string2) {
        this.suffix = string2;
    }

    public String getSuffix() {
        return this.suffix;
    }
}

