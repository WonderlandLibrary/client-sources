package net.minecraft.world.gen;

import net.minecraft.world.gen.structure.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.world.gen.feature.*;
import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.biome.*;

public class ChunkProviderHell implements IChunkProvider
{
    private double[] netherrackExclusivityNoise;
    double[] noiseData1;
    private final boolean field_177466_i;
    double[] noiseData5;
    private final WorldGenFire field_177470_t;
    private double[] noiseField;
    private final WorldGenHellLava field_177472_y;
    private final NoiseGeneratorOctaves netherNoiseGen2;
    private final WorldGenerator field_177467_w;
    double[] noiseData2;
    private double[] slowsandNoise;
    private final GeneratorBushFeature field_177471_z;
    private static final String[] I;
    private final MapGenBase netherCaveGenerator;
    double[] noiseData4;
    private final MapGenNetherBridge genNetherBridge;
    private double[] gravelNoise;
    private final WorldGenGlowStone1 field_177469_u;
    private final NoiseGeneratorOctaves netherNoiseGen3;
    private final WorldGenHellLava field_177473_x;
    public final NoiseGeneratorOctaves netherNoiseGen6;
    private final GeneratorBushFeature field_177465_A;
    private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    private final WorldGenGlowStone2 field_177468_v;
    private final Random hellRNG;
    double[] noiseData3;
    public final NoiseGeneratorOctaves netherNoiseGen7;
    private final NoiseGeneratorOctaves netherNoiseGen1;
    private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private final World worldObj;
    
    public void func_180516_b(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int n3 = this.worldObj.func_181545_F() + " ".length();
        final double n4 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, n * (0x8F ^ 0x9F), n2 * (0x2D ^ 0x3D), "".length(), 0x39 ^ 0x29, 0x3F ^ 0x2F, " ".length(), n4, n4, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, n * (0xBF ^ 0xAF), 0x17 ^ 0x7A, n2 * (0xB3 ^ 0xA3), 0xD ^ 0x1D, " ".length(), 0xD6 ^ 0xC6, n4, 1.0, n4);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, n * (0x6B ^ 0x7B), n2 * (0x2D ^ 0x3D), "".length(), 0x41 ^ 0x51, 0x2E ^ 0x3E, " ".length(), n4 * 2.0, n4 * 2.0, n4 * 2.0);
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < (0x8A ^ 0x9A)) {
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < (0xA2 ^ 0xB2)) {
                int n5;
                if (this.slowsandNoise[i + j * (0x89 ^ 0x99)] + this.hellRNG.nextDouble() * 0.2 > 0.0) {
                    n5 = " ".length();
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                }
                else {
                    n5 = "".length();
                }
                final int n6 = n5;
                int n7;
                if (this.gravelNoise[i + j * (0x8A ^ 0x9A)] + this.hellRNG.nextDouble() * 0.2 > 0.0) {
                    n7 = " ".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    n7 = "".length();
                }
                final int n8 = n7;
                final int n9 = (int)(this.netherrackExclusivityNoise[i + j * (0x65 ^ 0x75)] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
                int n10 = -" ".length();
                IBlockState blockState = Blocks.netherrack.getDefaultState();
                IBlockState blockState2 = Blocks.netherrack.getDefaultState();
                int k = 89 + 97 - 83 + 24;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                while (k >= 0) {
                    if (k < 96 + 93 - 107 + 45 - this.hellRNG.nextInt(0x46 ^ 0x43) && k > this.hellRNG.nextInt(0x10 ^ 0x15)) {
                        final IBlockState blockState3 = chunkPrimer.getBlockState(j, k, i);
                        if (blockState3.getBlock() != null && blockState3.getBlock().getMaterial() != Material.air) {
                            if (blockState3.getBlock() == Blocks.netherrack) {
                                if (n10 == -" ".length()) {
                                    if (n9 <= 0) {
                                        blockState = null;
                                        blockState2 = Blocks.netherrack.getDefaultState();
                                        "".length();
                                        if (3 != 3) {
                                            throw null;
                                        }
                                    }
                                    else if (k >= n3 - (0x43 ^ 0x47) && k <= n3 + " ".length()) {
                                        blockState = Blocks.netherrack.getDefaultState();
                                        blockState2 = Blocks.netherrack.getDefaultState();
                                        if (n8 != 0) {
                                            blockState = Blocks.gravel.getDefaultState();
                                            blockState2 = Blocks.netherrack.getDefaultState();
                                        }
                                        if (n6 != 0) {
                                            blockState = Blocks.soul_sand.getDefaultState();
                                            blockState2 = Blocks.soul_sand.getDefaultState();
                                        }
                                    }
                                    if (k < n3 && (blockState == null || blockState.getBlock().getMaterial() == Material.air)) {
                                        blockState = Blocks.lava.getDefaultState();
                                    }
                                    n10 = n9;
                                    if (k >= n3 - " ".length()) {
                                        chunkPrimer.setBlockState(j, k, i, blockState);
                                        "".length();
                                        if (0 >= 2) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        chunkPrimer.setBlockState(j, k, i, blockState2);
                                        "".length();
                                        if (false == true) {
                                            throw null;
                                        }
                                    }
                                }
                                else if (n10 > 0) {
                                    --n10;
                                    chunkPrimer.setBlockState(j, k, i, blockState2);
                                    "".length();
                                    if (1 >= 2) {
                                        throw null;
                                    }
                                }
                            }
                        }
                        else {
                            n10 = -" ".length();
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        chunkPrimer.setBlockState(j, k, i, Blocks.bedrock.getDefaultState());
                    }
                    --k;
                }
                ++j;
            }
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
    
    @Override
    public boolean canSave() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*\u0004\u001d\u001d\u0000\u0003\u000f\u0015\u001e?.\u0004\u0007\u0014>1\u000e\u0004\u00031\u0007", "baqqR");
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return "".length() != 0;
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = (" ".length() != 0);
        final BlockPos blockPos = new BlockPos(n * (0x65 ^ 0x75), "".length(), n2 * (0x8B ^ 0x9B));
        this.genNetherBridge.generateStructure(this.worldObj, this.hellRNG, new ChunkCoordIntPair(n, n2));
        int i = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (i < (0xB8 ^ 0xB0)) {
            this.field_177472_y.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x12 ^ 0x2) + (0xAA ^ 0xA2), this.hellRNG.nextInt(0x53 ^ 0x2B) + (0x50 ^ 0x54), this.hellRNG.nextInt(0xA0 ^ 0xB0) + (0x7D ^ 0x75)));
            ++i;
        }
        int j = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (j < this.hellRNG.nextInt(this.hellRNG.nextInt(0xAB ^ 0xA1) + " ".length()) + " ".length()) {
            this.field_177470_t.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x47 ^ 0x57) + (0x73 ^ 0x7B), this.hellRNG.nextInt(0xCF ^ 0xB7) + (0x10 ^ 0x14), this.hellRNG.nextInt(0x32 ^ 0x22) + (0xB8 ^ 0xB0)));
            ++j;
        }
        int k = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (k < this.hellRNG.nextInt(this.hellRNG.nextInt(0xCE ^ 0xC4) + " ".length())) {
            this.field_177469_u.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x7B ^ 0x6B) + (0x81 ^ 0x89), this.hellRNG.nextInt(0x6E ^ 0x16) + (0x8F ^ 0x8B), this.hellRNG.nextInt(0x8E ^ 0x9E) + (0x4E ^ 0x46)));
            ++k;
        }
        int l = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (l < (0x74 ^ 0x7E)) {
            this.field_177468_v.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x94 ^ 0x84) + (0xCC ^ 0xC4), this.hellRNG.nextInt(55 + 14 - 28 + 87), this.hellRNG.nextInt(0xB6 ^ 0xA6) + (0x26 ^ 0x2E)));
            ++l;
        }
        if (this.hellRNG.nextBoolean()) {
            this.field_177471_z.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x3 ^ 0x13) + (0x29 ^ 0x21), this.hellRNG.nextInt(77 + 35 - 17 + 33), this.hellRNG.nextInt(0x6C ^ 0x7C) + (0xAE ^ 0xA6)));
        }
        if (this.hellRNG.nextBoolean()) {
            this.field_177465_A.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x54 ^ 0x44) + (0xB5 ^ 0xBD), this.hellRNG.nextInt(13 + 106 - 57 + 66), this.hellRNG.nextInt(0x56 ^ 0x46) + (0x27 ^ 0x2F)));
        }
        int length = "".length();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (length < (0x2 ^ 0x12)) {
            this.field_177467_w.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x50 ^ 0x40), this.hellRNG.nextInt(0x1A ^ 0x76) + (0xC8 ^ 0xC2), this.hellRNG.nextInt(0x56 ^ 0x46)));
            ++length;
        }
        int length2 = "".length();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (length2 < (0xBE ^ 0xAE)) {
            this.field_177473_x.generate(this.worldObj, this.hellRNG, blockPos.add(this.hellRNG.nextInt(0x70 ^ 0x60), this.hellRNG.nextInt(0xF9 ^ 0x95) + (0x6E ^ 0x64), this.hellRNG.nextInt(0xD3 ^ 0xC3)));
            ++length2;
        }
        BlockFalling.fallInstantly = ("".length() != 0);
    }
    
    @Override
    public int getLoadedChunkCount() {
        return "".length();
    }
    
    public ChunkProviderHell(final World worldObj, final boolean field_177466_i, final long n) {
        this.slowsandNoise = new double[227 + 29 - 244 + 244];
        this.gravelNoise = new double[177 + 170 - 159 + 68];
        this.netherrackExclusivityNoise = new double[246 + 167 - 366 + 209];
        this.field_177470_t = new WorldGenFire();
        this.field_177469_u = new WorldGenGlowStone1();
        this.field_177468_v = new WorldGenGlowStone2();
        this.field_177467_w = new WorldGenMinable(Blocks.quartz_ore.getDefaultState(), 0x25 ^ 0x2B, (Predicate<IBlockState>)BlockHelper.forBlock(Blocks.netherrack));
        this.field_177473_x = new WorldGenHellLava(Blocks.flowing_lava, " ".length() != 0);
        this.field_177472_y = new WorldGenHellLava(Blocks.flowing_lava, "".length() != 0);
        this.field_177471_z = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.field_177465_A = new GeneratorBushFeature(Blocks.red_mushroom);
        this.genNetherBridge = new MapGenNetherBridge();
        this.netherCaveGenerator = new MapGenCavesHell();
        this.worldObj = worldObj;
        this.field_177466_i = field_177466_i;
        this.hellRNG = new Random(n);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 0x83 ^ 0x93);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 0x4F ^ 0x5F);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 0x23 ^ 0x2B);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 0x1E ^ 0x1A);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 0x55 ^ 0x51);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 0x3C ^ 0x36);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 0x96 ^ 0x86);
        worldObj.func_181544_b(0xA4 ^ 0x9B);
    }
    
    public void func_180515_a(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int n3 = 0x32 ^ 0x36;
        final int n4 = this.worldObj.func_181545_F() / "  ".length() + " ".length();
        final int n5 = n3 + " ".length();
        final int n6 = 0x74 ^ 0x65;
        final int n7 = n3 + " ".length();
        this.noiseField = this.initializeNoiseField(this.noiseField, n * n3, "".length(), n2 * n3, n5, n6, n7);
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < n3) {
            int j = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (j < n3) {
                int k = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (k < (0x85 ^ 0x95)) {
                    final double n8 = 0.125;
                    double n9 = this.noiseField[((i + "".length()) * n7 + j + "".length()) * n6 + k + "".length()];
                    double n10 = this.noiseField[((i + "".length()) * n7 + j + " ".length()) * n6 + k + "".length()];
                    double n11 = this.noiseField[((i + " ".length()) * n7 + j + "".length()) * n6 + k + "".length()];
                    double n12 = this.noiseField[((i + " ".length()) * n7 + j + " ".length()) * n6 + k + "".length()];
                    final double n13 = (this.noiseField[((i + "".length()) * n7 + j + "".length()) * n6 + k + " ".length()] - n9) * n8;
                    final double n14 = (this.noiseField[((i + "".length()) * n7 + j + " ".length()) * n6 + k + " ".length()] - n10) * n8;
                    final double n15 = (this.noiseField[((i + " ".length()) * n7 + j + "".length()) * n6 + k + " ".length()] - n11) * n8;
                    final double n16 = (this.noiseField[((i + " ".length()) * n7 + j + " ".length()) * n6 + k + " ".length()] - n12) * n8;
                    int l = "".length();
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                    while (l < (0x81 ^ 0x89)) {
                        final double n17 = 0.25;
                        double n18 = n9;
                        double n19 = n10;
                        final double n20 = (n11 - n9) * n17;
                        final double n21 = (n12 - n10) * n17;
                        int length = "".length();
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                        while (length < (0x2C ^ 0x28)) {
                            final double n22 = 0.25;
                            double n23 = n18;
                            final double n24 = (n19 - n18) * n22;
                            int length2 = "".length();
                            "".length();
                            if (3 < 1) {
                                throw null;
                            }
                            while (length2 < (0x34 ^ 0x30)) {
                                IBlockState blockState = null;
                                if (k * (0xC8 ^ 0xC0) + l < n4) {
                                    blockState = Blocks.lava.getDefaultState();
                                }
                                if (n23 > 0.0) {
                                    blockState = Blocks.netherrack.getDefaultState();
                                }
                                chunkPrimer.setBlockState(length + i * (0x7E ^ 0x7A), l + k * (0x32 ^ 0x3A), length2 + j * (0x4 ^ 0x0), blockState);
                                n23 += n24;
                                ++length2;
                            }
                            n18 += n20;
                            n19 += n21;
                            ++length;
                        }
                        n9 += n13;
                        n10 += n14;
                        n11 += n15;
                        n12 += n16;
                        ++l;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x48 ^ 0x4C), blockPos.getZ() >> (0x9 ^ 0xD));
    }
    
    private double[] initializeNoiseField(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        final double n7 = 684.412;
        final double n8 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, n, n2, n3, n4, " ".length(), n6, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, n, n2, n3, n4, " ".length(), n6, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, n, n2, n3, n4, n5, n6, n7 / 80.0, n8 / 60.0, n7 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, n, n2, n3, n4, n5, n6, n7, n8, n7);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, n, n2, n3, n4, n5, n6, n7, n8, n7);
        int length = "".length();
        final double[] array2 = new double[n5];
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < n5) {
            array2[i] = Math.cos(i * 3.141592653589793 * 6.0 / n5) * 2.0;
            double n9 = i;
            if (i > n5 / "  ".length()) {
                n9 = n5 - " ".length() - i;
            }
            if (n9 < 4.0) {
                final double n10 = 4.0 - n9;
                final double[] array3 = array2;
                final int n11 = i;
                array3[n11] -= n10 * n10 * n10 * 10.0;
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (j < n4) {
            int k = "".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
            while (k < n6) {
                final double n12 = 0.0;
                int l = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (l < n5) {
                    final double n13 = array2[l];
                    final double n14 = this.noiseData2[length] / 512.0;
                    final double n15 = this.noiseData3[length] / 512.0;
                    final double n16 = (this.noiseData1[length] / 10.0 + 1.0) / 2.0;
                    double n17;
                    if (n16 < 0.0) {
                        n17 = n14;
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    else if (n16 > 1.0) {
                        n17 = n15;
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        n17 = n14 + (n15 - n14) * n16;
                    }
                    double n18 = n17 - n13;
                    if (l > n5 - (0x1E ^ 0x1A)) {
                        final double n19 = (l - (n5 - (0xAC ^ 0xA8))) / 3.0f;
                        n18 = n18 * (1.0 - n19) + -10.0 * n19;
                    }
                    if (l < n12) {
                        final double clamp_double = MathHelper.clamp_double((n12 - l) / 4.0, 0.0, 1.0);
                        n18 = n18 * (1.0 - clamp_double) + -10.0 * clamp_double;
                    }
                    array[length] = n18;
                    ++length;
                    ++l;
                }
                ++k;
            }
            ++j;
        }
        return array;
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return "".length() != 0;
    }
    
    @Override
    public String makeString() {
        return ChunkProviderHell.I["".length()];
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        if (enumCreatureType == EnumCreatureType.MONSTER) {
            if (this.genNetherBridge.func_175795_b(blockPos)) {
                return this.genNetherBridge.getSpawnList();
            }
            if (this.genNetherBridge.func_175796_a(this.worldObj, blockPos) && this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.nether_brick) {
                return this.genNetherBridge.getSpawnList();
            }
        }
        return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        this.genNetherBridge.generate(this, this.worldObj, n, n2, null);
    }
    
    static {
        I();
    }
    
    @Override
    public void saveExtraData() {
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.hellRNG.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.func_180515_a(n, n2, chunkPrimer);
        this.func_180516_b(n, n2, chunkPrimer);
        this.netherCaveGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        if (this.field_177466_i) {
            this.genNetherBridge.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final BiomeGenBase[] loadBlockGeneratorData = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, n * (0x63 ^ 0x73), n2 * (0x30 ^ 0x20), 0xA8 ^ 0xB8, 0x16 ^ 0x6);
        final byte[] biomeArray = chunk.getBiomeArray();
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < biomeArray.length) {
            biomeArray[i] = (byte)loadBlockGeneratorData[i].biomeID;
            ++i;
        }
        chunk.resetRelightChecks();
        return chunk;
    }
}
