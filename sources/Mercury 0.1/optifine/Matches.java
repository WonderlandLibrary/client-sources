/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.MatchBlock;

public class Matches {
    public static boolean block(BlockStateBase blockStateBase, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i2 = 0; i2 < matchBlocks.length; ++i2) {
            MatchBlock mb2 = matchBlocks[i2];
            if (!mb2.matches(blockStateBase)) continue;
            return true;
        }
        return false;
    }

    public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i2 = 0; i2 < matchBlocks.length; ++i2) {
            MatchBlock mb2 = matchBlocks[i2];
            if (!mb2.matches(blockId, metadata)) continue;
            return true;
        }
        return false;
    }

    public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i2 = 0; i2 < matchBlocks.length; ++i2) {
            MatchBlock mb2 = matchBlocks[i2];
            if (mb2.getBlockId() != blockId) continue;
            return true;
        }
        return false;
    }

    public static boolean metadata(int metadata, int[] metadatas) {
        if (metadatas == null) {
            return true;
        }
        for (int i2 = 0; i2 < metadatas.length; ++i2) {
            if (metadatas[i2] != metadata) continue;
            return true;
        }
        return false;
    }

    public static boolean sprite(TextureAtlasSprite sprite, TextureAtlasSprite[] sprites) {
        if (sprites == null) {
            return true;
        }
        for (int i2 = 0; i2 < sprites.length; ++i2) {
            if (sprites[i2] != sprite) continue;
            return true;
        }
        return false;
    }

    public static boolean biome(BiomeGenBase biome, BiomeGenBase[] biomes) {
        if (biomes == null) {
            return true;
        }
        for (int i2 = 0; i2 < biomes.length; ++i2) {
            if (biomes[i2] != biome) continue;
            return true;
        }
        return false;
    }
}

