package net.minecraft.world.biome;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.chunk.*;

public class BiomeGenHills extends BiomeGenBase
{
    private WorldGenerator theWorldGenerator;
    private int field_150635_aE;
    private int field_150637_aG;
    private WorldGenTaiga2 field_150634_aD;
    private int field_150636_aF;
    private static final String[] I;
    private int field_150638_aH;
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        WorldGenAbstractTree worldGenAbstractTree;
        if (random.nextInt("   ".length()) > 0) {
            worldGenAbstractTree = this.field_150634_aD;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            worldGenAbstractTree = super.genBigTreeChance(random);
        }
        return worldGenAbstractTree;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("m\"", "MoEqQ");
    }
    
    @Override
    public void decorate(final World world, final Random random, final BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        final int n = "   ".length() + random.nextInt(0x9A ^ 0x9C);
        int i = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (i < n) {
            final BlockPos add = blockPos.add(random.nextInt(0x87 ^ 0x97), random.nextInt(0x38 ^ 0x24) + (0x5B ^ 0x5F), random.nextInt(0x64 ^ 0x74));
            if (world.getBlockState(add).getBlock() == Blocks.stone) {
                world.setBlockState(add, Blocks.emerald_ore.getDefaultState(), "  ".length());
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (j < (0x96 ^ 0x91)) {
            this.theWorldGenerator.generate(world, random, blockPos.add(random.nextInt(0x90 ^ 0x80), random.nextInt(0x59 ^ 0x19), random.nextInt(0xA0 ^ 0xB0)));
            ++j;
        }
    }
    
    private BiomeGenHills mutateHills(final BiomeGenBase biomeGenBase) {
        this.field_150638_aH = this.field_150637_aG;
        this.func_150557_a(biomeGenBase.color, " ".length() != 0);
        this.setBiomeName(String.valueOf(biomeGenBase.biomeName) + BiomeGenHills.I["".length()]);
        this.setHeight(new Height(biomeGenBase.minHeight, biomeGenBase.maxHeight));
        this.setTemperatureRainfall(biomeGenBase.temperature, biomeGenBase.rainfall);
        return this;
    }
    
    protected BiomeGenHills(final int n, final boolean b) {
        super(n);
        this.theWorldGenerator = new WorldGenMinable(Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.STONE), 0x81 ^ 0x88);
        this.field_150634_aD = new WorldGenTaiga2("".length() != 0);
        this.field_150635_aE = "".length();
        this.field_150636_aF = " ".length();
        this.field_150637_aG = "  ".length();
        this.field_150638_aH = this.field_150635_aE;
        if (b) {
            this.theBiomeDecorator.treesPerChunk = "   ".length();
            this.field_150638_aH = this.field_150636_aF;
        }
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        return new BiomeGenHills(n, "".length() != 0).mutateHills(this);
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        if ((n3 < -1.0 || n3 > 2.0) && this.field_150638_aH == this.field_150637_aG) {
            this.topBlock = Blocks.gravel.getDefaultState();
            this.fillerBlock = Blocks.gravel.getDefaultState();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (n3 > 1.0 && this.field_150638_aH != this.field_150636_aF) {
            this.topBlock = Blocks.stone.getDefaultState();
            this.fillerBlock = Blocks.stone.getDefaultState();
        }
        this.generateBiomeTerrain(world, random, chunkPrimer, n, n2, n3);
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
