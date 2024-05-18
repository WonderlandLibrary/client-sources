package net.minecraft.world.biome;

import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenJungle extends BiomeGenBase
{
    private static final IBlockState field_181620_aE;
    private static final IBlockState field_181621_aF;
    private boolean field_150614_aC;
    private static final IBlockState field_181622_aG;
    
    public BiomeGenJungle(final int n, final boolean field_150614_aC) {
        super(n);
        this.field_150614_aC = field_150614_aC;
        if (field_150614_aC) {
            this.theBiomeDecorator.treesPerChunk = "  ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.theBiomeDecorator.treesPerChunk = (0x13 ^ 0x21);
        }
        this.theBiomeDecorator.grassPerChunk = (0x3D ^ 0x24);
        this.theBiomeDecorator.flowersPerChunk = (0x41 ^ 0x45);
        if (!field_150614_aC) {
            this.spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, "  ".length(), " ".length(), " ".length()));
        }
        this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 0x9B ^ 0x91, 0x23 ^ 0x27, 0xC4 ^ 0xC0));
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGeneratorBigTree;
        if (random.nextInt(0x74 ^ 0x7E) == 0) {
            worldGeneratorBigTree = this.worldGeneratorBigTree;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (random.nextInt("  ".length()) == 0) {
            worldGeneratorBigTree = new WorldGenShrub(BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181622_aG);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (!this.field_150614_aC && random.nextInt("   ".length()) == 0) {
            worldGeneratorBigTree = new WorldGenMegaJungle("".length() != 0, 0x6C ^ 0x66, 0xC ^ 0x18, BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181621_aF);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            worldGeneratorBigTree = new WorldGenTrees("".length() != 0, (0x64 ^ 0x60) + random.nextInt(0xB2 ^ 0xB5), BiomeGenJungle.field_181620_aE, BiomeGenJungle.field_181621_aF, " ".length() != 0);
        }
        return worldGeneratorBigTree;
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        final int n = random.nextInt(0xA8 ^ 0xB8) + (0x25 ^ 0x2D);
        final int n2 = random.nextInt(0x94 ^ 0x84) + (0xA9 ^ 0xA1);
        new WorldGenMelon().generate(world, random, blockPos.add(n, random.nextInt(world.getHeight(blockPos.add(n, "".length(), n2)).getY() * "  ".length()), n2));
        final WorldGenVines worldGenVines = new WorldGenVines();
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < (0x97 ^ 0xA5)) {
            worldGenVines.generate(world, random, blockPos.add(random.nextInt(0x54 ^ 0x44) + (0xB0 ^ 0xB8), 40 + 87 - 37 + 38, random.nextInt(0x5C ^ 0x4C) + (0xB6 ^ 0xBE)));
            ++i;
        }
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        field_181620_aE = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
        field_181621_aF = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
        field_181622_aG = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        WorldGenTallGrass worldGenTallGrass;
        if (random.nextInt(0x39 ^ 0x3D) == 0) {
            worldGenTallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            worldGenTallGrass = new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
        }
        return worldGenTallGrass;
    }
}
