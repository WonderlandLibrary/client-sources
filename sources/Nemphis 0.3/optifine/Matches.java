/*
 * Decompiled with CFR 0_118.
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
        int i = 0;
        while (i < matchBlocks.length) {
            MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockStateBase)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        int i = 0;
        while (i < matchBlocks.length) {
            MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockId, metadata)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        int i = 0;
        while (i < matchBlocks.length) {
            MatchBlock mb = matchBlocks[i];
            if (mb.getBlockId() == blockId) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean metadata(int metadata, int[] metadatas) {
        if (metadatas == null) {
            return true;
        }
        int i = 0;
        while (i < metadatas.length) {
            if (metadatas[i] == metadata) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean sprite(TextureAtlasSprite sprite, TextureAtlasSprite[] sprites) {
        if (sprites == null) {
            return true;
        }
        int i = 0;
        while (i < sprites.length) {
            if (sprites[i] == sprite) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean biome(BiomeGenBase biome, BiomeGenBase[] biomes) {
        if (biomes == null) {
            return true;
        }
        int i = 0;
        while (i < biomes.length) {
            if (biomes[i] == biome) {
                return true;
            }
            ++i;
        }
        return false;
    }
}

