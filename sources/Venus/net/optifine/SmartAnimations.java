/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.BitSet;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class SmartAnimations {
    private static boolean active;
    private static BitSet spritesRendered;
    private static BitSet texturesRendered;

    public static boolean isActive() {
        return active && !Shaders.isShadowPass;
    }

    public static void update() {
        active = Config.getGameSettings().ofSmartAnimations;
    }

    public static void spriteRendered(TextureAtlasSprite textureAtlasSprite) {
        int n;
        if (textureAtlasSprite.isTerrain() && (n = textureAtlasSprite.getAnimationIndex()) >= 0) {
            spritesRendered.set(n);
        }
    }

    public static void spritesRendered(BitSet bitSet) {
        if (bitSet != null) {
            spritesRendered.or(bitSet);
        }
    }

    public static boolean isSpriteRendered(TextureAtlasSprite textureAtlasSprite) {
        if (!textureAtlasSprite.isTerrain()) {
            return false;
        }
        int n = textureAtlasSprite.getAnimationIndex();
        return n < 0 ? false : spritesRendered.get(n);
    }

    public static void resetSpritesRendered(AtlasTexture atlasTexture) {
        if (atlasTexture.isTerrain()) {
            spritesRendered.clear();
        }
    }

    public static void textureRendered(int n) {
        if (n >= 0) {
            texturesRendered.set(n);
        }
    }

    public static boolean isTextureRendered(int n) {
        return n < 0 ? false : texturesRendered.get(n);
    }

    public static void resetTexturesRendered() {
        texturesRendered.clear();
    }

    static {
        spritesRendered = new BitSet();
        texturesRendered = new BitSet();
    }
}

