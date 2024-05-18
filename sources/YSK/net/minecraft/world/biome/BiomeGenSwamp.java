package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;

public class BiomeGenSwamp extends BiomeGenBase
{
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.worldGeneratorSwamp;
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        final double func_151601_a = BiomeGenSwamp.GRASS_COLOR_NOISE.func_151601_a(n * 0.25, n2 * 0.25);
        if (func_151601_a > 0.0) {
            final int n4 = n & (0x2A ^ 0x25);
            final int n5 = n2 & (0x55 ^ 0x5A);
            int i = 109 + 93 - 73 + 126;
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (i >= 0) {
                if (chunkPrimer.getBlockState(n5, i, n4).getBlock().getMaterial() != Material.air) {
                    if (i != (0xFF ^ 0xC1) || chunkPrimer.getBlockState(n5, i, n4).getBlock() == Blocks.water) {
                        break;
                    }
                    chunkPrimer.setBlockState(n5, i, n4, Blocks.water.getDefaultState());
                    if (func_151601_a >= 0.12) {
                        break;
                    }
                    chunkPrimer.setBlockState(n5, i + " ".length(), n4, Blocks.waterlily.getDefaultState());
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                    break;
                }
                else {
                    --i;
                }
            }
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public int getGrassColorAtPos(final BlockPos blockPos) {
        int n;
        if (BiomeGenSwamp.GRASS_COLOR_NOISE.func_151601_a(blockPos.getX() * 0.0225, blockPos.getZ() * 0.0225) < -0.1) {
            n = 116718 + 3814415 - 1886858 + 2966729;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = 2886894 + 1285979 - 277335 + 3080007;
        }
        return n;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BiomeGenSwamp(final int n) {
        super(n);
        this.theBiomeDecorator.treesPerChunk = "  ".length();
        this.theBiomeDecorator.flowersPerChunk = " ".length();
        this.theBiomeDecorator.deadBushPerChunk = " ".length();
        this.theBiomeDecorator.mushroomsPerChunk = (0x38 ^ 0x30);
        this.theBiomeDecorator.reedsPerChunk = (0x50 ^ 0x5A);
        this.theBiomeDecorator.clayPerChunk = " ".length();
        this.theBiomeDecorator.waterlilyPerChunk = (0xB0 ^ 0xB4);
        this.theBiomeDecorator.sandPerChunk2 = "".length();
        this.theBiomeDecorator.sandPerChunk = "".length();
        this.theBiomeDecorator.grassPerChunk = (0x89 ^ 0x8C);
        this.waterColorMultiplier = 11191431 + 3750036 - 14136154 + 13940205;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, " ".length(), " ".length(), " ".length()));
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }
    
    @Override
    public int getFoliageColorAtPos(final BlockPos blockPos) {
        return 2775258 + 6016866 - 6858093 + 5041514;
    }
}
