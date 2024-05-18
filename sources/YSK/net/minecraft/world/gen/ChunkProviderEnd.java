package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class ChunkProviderEnd implements IChunkProvider
{
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData4;
    private double[] densities;
    public NoiseGeneratorOctaves noiseGen5;
    private NoiseGeneratorOctaves noiseGen2;
    private Random endRNG;
    double[] noiseData5;
    private World endWorld;
    double[] noiseData2;
    private static final String[] I;
    public NoiseGeneratorOctaves noiseGen4;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen1;
    double[] noiseData3;
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = (" ".length() != 0);
        final BlockPos blockPos = new BlockPos(n * (0xC ^ 0x1C), "".length(), n2 * (0x2F ^ 0x3F));
        this.endWorld.getBiomeGenForCoords(blockPos.add(0x30 ^ 0x20, "".length(), 0x66 ^ 0x76)).decorate(this.endWorld, this.endWorld.rand, blockPos);
        BlockFalling.fallInstantly = ("".length() != 0);
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        return null;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("!)\u0014\u0007\u0017\u001e\u0004\u001f\u0015\u001d\u001f\u001b\u0015\u0016\n\u0010-", "sHzcx");
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x57 ^ 0x53), blockPos.getZ() >> (0x99 ^ 0x9D));
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    public void func_180519_a(final ChunkPrimer chunkPrimer) {
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < (0x3F ^ 0x2F)) {
            int j = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (j < (0x5D ^ 0x4D)) {
                final int length = " ".length();
                int n = -" ".length();
                IBlockState blockState = Blocks.end_stone.getDefaultState();
                IBlockState blockState2 = Blocks.end_stone.getDefaultState();
                int k = 23 + 59 + 43 + 2;
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (k >= 0) {
                    final IBlockState blockState3 = chunkPrimer.getBlockState(i, k, j);
                    if (blockState3.getBlock().getMaterial() == Material.air) {
                        n = -" ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else if (blockState3.getBlock() == Blocks.stone) {
                        if (n == -" ".length()) {
                            if (length <= 0) {
                                blockState = Blocks.air.getDefaultState();
                                blockState2 = Blocks.end_stone.getDefaultState();
                            }
                            n = length;
                            if (k >= 0) {
                                chunkPrimer.setBlockState(i, k, j, blockState);
                                "".length();
                                if (4 < 4) {
                                    throw null;
                                }
                            }
                            else {
                                chunkPrimer.setBlockState(i, k, j, blockState2);
                                "".length();
                                if (-1 != -1) {
                                    throw null;
                                }
                            }
                        }
                        else if (n > 0) {
                            --n;
                            chunkPrimer.setBlockState(i, k, j, blockState2);
                        }
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.endWorld.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    public ChunkProviderEnd(final World endWorld, final long n) {
        this.endWorld = endWorld;
        this.endRNG = new Random(n);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 0x13 ^ 0x3);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 0x53 ^ 0x43);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 0x2A ^ 0x22);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 0xB2 ^ 0xB8);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 0x2F ^ 0x3F);
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
    }
    
    @Override
    public void saveExtraData() {
    }
    
    private double[] initializeNoiseField(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
        }
        final double n7 = 684.412;
        final double n8 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, n, n3, n4, n6, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, n, n3, n4, n6, 200.0, 200.0, 0.5);
        final double n9 = n7 * 2.0;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, n, n2, n3, n4, n5, n6, n9 / 80.0, n8 / 160.0, n9 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, n, n2, n3, n4, n5, n6, n9, n8, n9);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, n, n2, n3, n4, n5, n6, n9, n8, n9);
        int length = "".length();
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (j < n6) {
                final float n10 = (i + n) / 1.0f;
                final float n11 = (j + n3) / 1.0f;
                float n12 = 100.0f - MathHelper.sqrt_float(n10 * n10 + n11 * n11) * 8.0f;
                if (n12 > 80.0f) {
                    n12 = 80.0f;
                }
                if (n12 < -100.0f) {
                    n12 = -100.0f;
                }
                int k = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (k < n5) {
                    final double n13 = this.noiseData2[length] / 512.0;
                    final double n14 = this.noiseData3[length] / 512.0;
                    final double n15 = (this.noiseData1[length] / 10.0 + 1.0) / 2.0;
                    double n16;
                    if (n15 < 0.0) {
                        n16 = n13;
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                    }
                    else if (n15 > 1.0) {
                        n16 = n14;
                        "".length();
                        if (!true) {
                            throw null;
                        }
                    }
                    else {
                        n16 = n13 + (n14 - n13) * n15;
                    }
                    double n17 = n16 - 8.0 + n12;
                    final int length2 = "  ".length();
                    if (k > n5 / "  ".length() - length2) {
                        final double clamp_double = MathHelper.clamp_double((k - (n5 / "  ".length() - length2)) / 64.0f, 0.0, 1.0);
                        n17 = n17 * (1.0 - clamp_double) + -3000.0 * clamp_double;
                    }
                    final int n18 = 0x9F ^ 0x97;
                    if (k < n18) {
                        final double n19 = (n18 - k) / (n18 - 1.0f);
                        n17 = n17 * (1.0 - n19) + -30.0 * n19;
                    }
                    array[length] = n17;
                    ++length;
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return array;
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return "".length() != 0;
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return "".length() != 0;
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.endRNG.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, n * (0x83 ^ 0x93), n2 * (0x7B ^ 0x6B), 0x79 ^ 0x69, 0x8A ^ 0x9A);
        this.func_180520_a(n, n2, chunkPrimer);
        this.func_180519_a(chunkPrimer);
        final Chunk chunk = new Chunk(this.endWorld, chunkPrimer, n, n2);
        final byte[] biomeArray = chunk.getBiomeArray();
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < biomeArray.length) {
            biomeArray[i] = (byte)this.biomesForGeneration[i].biomeID;
            ++i;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return "".length();
    }
    
    @Override
    public boolean canSave() {
        return " ".length() != 0;
    }
    
    @Override
    public String makeString() {
        return ChunkProviderEnd.I["".length()];
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    public void func_180520_a(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        final int length = "  ".length();
        final int n3 = length + " ".length();
        final int n4 = 0xB2 ^ 0x93;
        final int n5 = length + " ".length();
        this.densities = this.initializeNoiseField(this.densities, n * length, "".length(), n2 * length, n3, n4, n5);
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < length) {
            int j = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
            while (j < length) {
                int k = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k < (0x61 ^ 0x41)) {
                    final double n6 = 0.25;
                    double n7 = this.densities[((i + "".length()) * n5 + j + "".length()) * n4 + k + "".length()];
                    double n8 = this.densities[((i + "".length()) * n5 + j + " ".length()) * n4 + k + "".length()];
                    double n9 = this.densities[((i + " ".length()) * n5 + j + "".length()) * n4 + k + "".length()];
                    double n10 = this.densities[((i + " ".length()) * n5 + j + " ".length()) * n4 + k + "".length()];
                    final double n11 = (this.densities[((i + "".length()) * n5 + j + "".length()) * n4 + k + " ".length()] - n7) * n6;
                    final double n12 = (this.densities[((i + "".length()) * n5 + j + " ".length()) * n4 + k + " ".length()] - n8) * n6;
                    final double n13 = (this.densities[((i + " ".length()) * n5 + j + "".length()) * n4 + k + " ".length()] - n9) * n6;
                    final double n14 = (this.densities[((i + " ".length()) * n5 + j + " ".length()) * n4 + k + " ".length()] - n10) * n6;
                    int l = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    while (l < (0x35 ^ 0x31)) {
                        final double n15 = 0.125;
                        double n16 = n7;
                        double n17 = n8;
                        final double n18 = (n9 - n7) * n15;
                        final double n19 = (n10 - n8) * n15;
                        int length2 = "".length();
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                        while (length2 < (0x8E ^ 0x86)) {
                            final double n20 = 0.125;
                            double n21 = n16;
                            final double n22 = (n17 - n16) * n20;
                            int length3 = "".length();
                            "".length();
                            if (4 == 2) {
                                throw null;
                            }
                            while (length3 < (0xAB ^ 0xA3)) {
                                IBlockState defaultState = null;
                                if (n21 > 0.0) {
                                    defaultState = Blocks.end_stone.getDefaultState();
                                }
                                chunkPrimer.setBlockState(length2 + i * (0x2F ^ 0x27), l + k * (0x3A ^ 0x3E), length3 + j * (0x75 ^ 0x7D), defaultState);
                                n21 += n22;
                                ++length3;
                            }
                            n16 += n18;
                            n17 += n19;
                            ++length2;
                        }
                        n7 += n11;
                        n8 += n12;
                        n9 += n13;
                        n10 += n14;
                        ++l;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
}
