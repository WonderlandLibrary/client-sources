/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.Biome;
import net.optifine.config.BiomeId;
import net.optifine.config.MatchBlock;

public class Matches {
    public static boolean block(BlockState blockState, MatchBlock[] matchBlockArray) {
        if (matchBlockArray == null) {
            return false;
        }
        for (int i = 0; i < matchBlockArray.length; ++i) {
            MatchBlock matchBlock = matchBlockArray[i];
            if (!matchBlock.matches(blockState)) continue;
            return false;
        }
        return true;
    }

    public static boolean block(int n, int n2, MatchBlock[] matchBlockArray) {
        if (matchBlockArray == null) {
            return false;
        }
        for (int i = 0; i < matchBlockArray.length; ++i) {
            MatchBlock matchBlock = matchBlockArray[i];
            if (!matchBlock.matches(n, n2)) continue;
            return false;
        }
        return true;
    }

    public static boolean blockId(int n, MatchBlock[] matchBlockArray) {
        if (matchBlockArray == null) {
            return false;
        }
        for (int i = 0; i < matchBlockArray.length; ++i) {
            MatchBlock matchBlock = matchBlockArray[i];
            if (matchBlock.getBlockId() != n) continue;
            return false;
        }
        return true;
    }

    public static boolean metadata(int n, int[] nArray) {
        if (nArray == null) {
            return false;
        }
        for (int i = 0; i < nArray.length; ++i) {
            if (nArray[i] != n) continue;
            return false;
        }
        return true;
    }

    public static boolean sprite(TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite[] textureAtlasSpriteArray) {
        if (textureAtlasSpriteArray == null) {
            return false;
        }
        for (int i = 0; i < textureAtlasSpriteArray.length; ++i) {
            if (textureAtlasSpriteArray[i] != textureAtlasSprite) continue;
            return false;
        }
        return true;
    }

    public static boolean biome(Biome biome, BiomeId[] biomeIdArray) {
        if (biomeIdArray == null) {
            return false;
        }
        for (int i = 0; i < biomeIdArray.length; ++i) {
            BiomeId biomeId = biomeIdArray[i];
            if (biomeId == null || biomeId.getBiome() != biome) continue;
            return false;
        }
        return true;
    }
}

