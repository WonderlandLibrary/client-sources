/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;

public class TextureMetadataSection {
    public static final TextureMetadataSectionSerializer SERIALIZER = new TextureMetadataSectionSerializer();
    private final boolean textureBlur;
    private final boolean textureClamp;

    public TextureMetadataSection(boolean bl, boolean bl2) {
        this.textureBlur = bl;
        this.textureClamp = bl2;
    }

    public boolean getTextureBlur() {
        return this.textureBlur;
    }

    public boolean getTextureClamp() {
        return this.textureClamp;
    }
}

