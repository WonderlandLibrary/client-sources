package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeGenSavanna extends BiomeGenBase
{
    private static final WorldGenSavannaTree field_150627_aC;
    
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        field_150627_aC = new WorldGenSavannaTree("".length() != 0);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final Mutated mutated = new Mutated(n, this);
        mutated.temperature = (this.temperature + 1.0f) * 0.5f;
        mutated.minHeight = this.minHeight * 0.5f + 0.3f;
        mutated.maxHeight = this.maxHeight * 0.5f + 1.2f;
        return mutated;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        BiomeGenSavanna.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < (0x5 ^ 0x2)) {
            final int n = random.nextInt(0x78 ^ 0x68) + (0x19 ^ 0x11);
            final int n2 = random.nextInt(0x60 ^ 0x70) + (0x31 ^ 0x39);
            BiomeGenSavanna.DOUBLE_PLANT_GENERATOR.generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, "".length(), n2)).getY() + (0x90 ^ 0xB0)), n2));
            ++i;
        }
        super.decorate(world, random, blockPos);
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if (random.nextInt(0x30 ^ 0x35) > 0) {
            worldGenAbstractTree = BiomeGenSavanna.field_150627_aC;
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = this.worldGeneratorTrees;
        }
        return worldGenAbstractTree;
    }
    
    protected BiomeGenSavanna(final int n) {
        super(n);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, " ".length(), "  ".length(), 0x56 ^ 0x50));
        this.theBiomeDecorator.treesPerChunk = " ".length();
        this.theBiomeDecorator.flowersPerChunk = (0x9B ^ 0x9F);
        this.theBiomeDecorator.grassPerChunk = (0xBD ^ 0xA9);
    }
    
    public static class Mutated extends BiomeGenMutated
    {
        @Override
        public void decorate(final World world, final Random random, final BlockPos blockPos) {
            this.theBiomeDecorator.decorate(world, random, this, blockPos);
        }
        
        @Override
        public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.stone.getDefaultState();
                this.fillerBlock = Blocks.stone.getDefaultState();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else if (n3 > -0.5) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
        }
        
        public Mutated(final int n, final BiomeGenBase biomeGenBase) {
            super(n, biomeGenBase);
            this.theBiomeDecorator.treesPerChunk = "  ".length();
            this.theBiomeDecorator.flowersPerChunk = "  ".length();
            this.theBiomeDecorator.grassPerChunk = (0x66 ^ 0x63);
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
                if (-1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
