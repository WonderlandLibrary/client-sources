/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

public enum TextureType {
    TEXTURE_1D(3552),
    TEXTURE_2D(3553),
    TEXTURE_3D(32879),
    TEXTURE_RECTANGLE(34037);

    private int id;

    private TextureType(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }
}

