/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IAnimatedSprite {
    public TextureAtlasSprite get(int var1, int var2);

    public TextureAtlasSprite get(Random var1);
}

