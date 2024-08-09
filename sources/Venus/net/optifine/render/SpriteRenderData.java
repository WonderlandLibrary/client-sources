/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class SpriteRenderData {
    private TextureAtlasSprite sprite;
    private int[] positions;
    private int[] counts;

    public SpriteRenderData(TextureAtlasSprite textureAtlasSprite, int[] nArray, int[] nArray2) {
        this.sprite = textureAtlasSprite;
        this.positions = nArray;
        this.counts = nArray2;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException(nArray.length + " != " + nArray2.length);
        }
    }

    public TextureAtlasSprite getSprite() {
        return this.sprite;
    }

    public int[] getPositions() {
        return this.positions;
    }

    public int[] getCounts() {
        return this.counts;
    }

    public String toString() {
        return this.sprite.getName() + ", " + this.positions.length;
    }
}

