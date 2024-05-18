package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;

public class ChunkProviderGenerate implements IChunkProvider
{
    private MapGenVillage villageGenerator;
    double[] field_147426_g;
    private ChunkProviderSettings settings;
    public NoiseGeneratorOctaves noiseGen5;
    private NoiseGeneratorPerlin field_147430_m;
    private Random rand;
    private final boolean mapFeaturesEnabled;
    private WorldType field_177475_o;
    private BiomeGenBase[] biomesForGeneration;
    private final double[] field_147434_q;
    public NoiseGeneratorOctaves noiseGen6;
    private NoiseGeneratorOctaves field_147432_k;
    private World worldObj;
    private Block field_177476_s;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private double[] stoneNoise;
    private NoiseGeneratorOctaves field_147429_l;
    private MapGenBase ravineGenerator;
    private static final String[] I;
    private StructureOceanMonument oceanMonumentGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private MapGenStronghold strongholdGenerator;
    double[] field_147428_e;
    private final float[] parabolicField;
    double[] field_147427_d;
    private MapGenBase caveGenerator;
    private MapGenMineshaft mineshaftGenerator;
    private NoiseGeneratorOctaves field_147431_j;
    double[] field_147425_f;
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        int length = "".length();
        if (this.settings.useMonuments && this.mapFeaturesEnabled && chunk.getInhabitedTime() < 3600L) {
            length |= (this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkCoordIntPair(n, n2)) ? 1 : 0);
        }
        return length != 0;
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        BlockPos closestStrongholdPos;
        if (ChunkProviderGenerate.I[" ".length()].equals(s) && this.strongholdGenerator != null) {
            closestStrongholdPos = this.strongholdGenerator.getClosestStrongholdPos(world, blockPos);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            closestStrongholdPos = null;
        }
        return closestStrongholdPos;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    private void func_147423_a(int length, final int n, int length2) {
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, length, length2, 0x20 ^ 0x25, 0xA9 ^ 0xAC, this.settings.depthNoiseScaleX, this.settings.depthNoiseScaleZ, this.settings.depthNoiseScaleExponent);
        final float coordinateScale = this.settings.coordinateScale;
        final float heightScale = this.settings.heightScale;
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, length, n, length2, 0xB ^ 0xE, 0x2C ^ 0xD, 0x6B ^ 0x6E, coordinateScale / this.settings.mainNoiseScaleX, heightScale / this.settings.mainNoiseScaleY, coordinateScale / this.settings.mainNoiseScaleZ);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, length, n, length2, 0xC ^ 0x9, 0x96 ^ 0xB7, 0x54 ^ 0x51, coordinateScale, heightScale, coordinateScale);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, length, n, length2, 0x3E ^ 0x3B, 0x6F ^ 0x4E, 0x5D ^ 0x58, coordinateScale, heightScale, coordinateScale);
        length2 = "".length();
        length = "".length();
        int length3 = "".length();
        int length4 = "".length();
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < (0x7C ^ 0x79)) {
            int j = "".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (j < (0xB8 ^ 0xBD)) {
                float n2 = 0.0f;
                float n3 = 0.0f;
                float n4 = 0.0f;
                final int length5 = "  ".length();
                final BiomeGenBase biomeGenBase = this.biomesForGeneration[i + "  ".length() + (j + "  ".length()) * (0xA3 ^ 0xA9)];
                int k = -length5;
                "".length();
                if (1 <= 0) {
                    throw null;
                }
                while (k <= length5) {
                    int l = -length5;
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (l <= length5) {
                        final BiomeGenBase biomeGenBase2 = this.biomesForGeneration[i + k + "  ".length() + (j + l + "  ".length()) * (0x38 ^ 0x32)];
                        float n5 = this.settings.biomeDepthOffSet + biomeGenBase2.minHeight * this.settings.biomeDepthWeight;
                        float n6 = this.settings.biomeScaleOffset + biomeGenBase2.maxHeight * this.settings.biomeScaleWeight;
                        if (this.field_177475_o == WorldType.AMPLIFIED && n5 > 0.0f) {
                            n5 = 1.0f + n5 * 2.0f;
                            n6 = 1.0f + n6 * 4.0f;
                        }
                        float n7 = this.parabolicField[k + "  ".length() + (l + "  ".length()) * (0xA2 ^ 0xA7)] / (n5 + 2.0f);
                        if (biomeGenBase2.minHeight > biomeGenBase.minHeight) {
                            n7 /= 2.0f;
                        }
                        n2 += n6 * n7;
                        n3 += n5 * n7;
                        n4 += n7;
                        ++l;
                    }
                    ++k;
                }
                final float n8 = n2 / n4;
                final float n9 = n3 / n4;
                final float n10 = n8 * 0.9f + 0.1f;
                final float n11 = (n9 * 4.0f - 1.0f) / 8.0f;
                double n12 = this.field_147426_g[length4] / 8000.0;
                if (n12 < 0.0) {
                    n12 = -n12 * 0.3;
                }
                double n13 = n12 * 3.0 - 2.0;
                double n15;
                if (n13 < 0.0) {
                    double n14 = n13 / 2.0;
                    if (n14 < -1.0) {
                        n14 = -1.0;
                    }
                    n15 = n14 / 1.4 / 2.0;
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    if (n13 > 1.0) {
                        n13 = 1.0;
                    }
                    n15 = n13 / 8.0;
                }
                ++length4;
                final double n16 = n11;
                final double n17 = n10;
                final double n18 = this.settings.baseSize + (n16 + n15 * 0.2) * this.settings.baseSize / 8.0 * 4.0;
                int length6 = "".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (length6 < (0x56 ^ 0x77)) {
                    double n19 = (length6 - n18) * this.settings.stretchY * 128.0 / 256.0 / n17;
                    if (n19 < 0.0) {
                        n19 *= 4.0;
                    }
                    double n20 = MathHelper.denormalizeClamp(this.field_147428_e[length3] / this.settings.lowerLimitScale, this.field_147425_f[length3] / this.settings.upperLimitScale, (this.field_147427_d[length3] / 10.0 + 1.0) / 2.0) - n19;
                    if (length6 > (0x1C ^ 0x1)) {
                        final double n21 = (length6 - (0x3F ^ 0x22)) / 3.0f;
                        n20 = n20 * (1.0 - n21) + -10.0 * n21;
                    }
                    this.field_147434_q[length3] = n20;
                    ++length3;
                    ++length6;
                }
                ++j;
            }
            ++i;
        }
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return "".length();
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        this.rand.setSeed(n * 341873128712L + n2 * 132897987541L);
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.setBlocksInChunk(n, n2, chunkPrimer);
        this.replaceBlocksForBiome(n, n2, chunkPrimer, this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, n * (0xD2 ^ 0xC2), n2 * (0x96 ^ 0x86), 0x52 ^ 0x42, 0x57 ^ 0x47));
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useRavines) {
            this.ravineGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final byte[] biomeArray = chunk.getBiomeArray();
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < biomeArray.length) {
            biomeArray[i] = (byte)this.biomesForGeneration[i].biomeID;
            ++i;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    public ChunkProviderGenerate(final World worldObj, final long n, final boolean mapFeaturesEnabled, final String s) {
        this.field_177476_s = Blocks.water;
        this.stoneNoise = new double[6 + 101 + 80 + 69];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.oceanMonumentGenerator = new StructureOceanMonument();
        this.worldObj = worldObj;
        this.mapFeaturesEnabled = mapFeaturesEnabled;
        this.field_177475_o = worldObj.getWorldInfo().getTerrainType();
        this.rand = new Random(n);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 0xAB ^ 0xBB);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 0x2E ^ 0x3E);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 0x4B ^ 0x43);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 0x2A ^ 0x2E);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 0x16 ^ 0x1C);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 0x8B ^ 0x9B);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 0x10 ^ 0x18);
        this.field_147434_q = new double[204 + 255 + 109 + 257];
        this.parabolicField = new float[0x48 ^ 0x51];
        int i = -"  ".length();
        "".length();
        if (2 == 1) {
            throw null;
        }
        while (i <= "  ".length()) {
            int j = -"  ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (j <= "  ".length()) {
                this.parabolicField[i + "  ".length() + (j + "  ".length()) * (0x38 ^ 0x3D)] = 10.0f / MathHelper.sqrt_float(i * i + j * j + 0.2f);
                ++j;
            }
            ++i;
        }
        if (s != null) {
            this.settings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
            BlockStaticLiquid field_177476_s;
            if (this.settings.useLavaOceans) {
                field_177476_s = Blocks.lava;
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            else {
                field_177476_s = Blocks.water;
            }
            this.field_177476_s = field_177476_s;
            worldObj.func_181544_b(this.settings.seaLevel);
        }
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        BlockFalling.fallInstantly = (" ".length() != 0);
        final int n3 = n * (0x42 ^ 0x52);
        final int n4 = n2 * (0x22 ^ 0x32);
        final BlockPos blockPos = new BlockPos(n3, "".length(), n4);
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos.add(0x3F ^ 0x2F, "".length(), 0xBB ^ 0xAB));
        this.rand.setSeed(this.worldObj.getSeed());
        this.rand.setSeed(n * (this.rand.nextLong() / 2L * 2L + 1L) + n2 * (this.rand.nextLong() / 2L * 2L + 1L) ^ this.worldObj.getSeed());
        int n5 = "".length();
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(n, n2);
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            n5 = (this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair) ? 1 : 0);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }
        if (biomeGenForCoords != BiomeGenBase.desert && biomeGenForCoords != BiomeGenBase.desertHills && this.settings.useWaterLakes && n5 == 0 && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
            new WorldGenLakes(Blocks.water).generate(this.worldObj, this.rand, blockPos.add(this.rand.nextInt(0x2A ^ 0x3A) + (0x41 ^ 0x49), this.rand.nextInt(44 + 10 + 18 + 184), this.rand.nextInt(0x79 ^ 0x69) + (0x1D ^ 0x15)));
        }
        if (n5 == 0 && this.rand.nextInt(this.settings.lavaLakeChance / (0xB9 ^ 0xB3)) == 0 && this.settings.useLavaLakes) {
            final int n6 = this.rand.nextInt(0x3A ^ 0x2A) + (0x2D ^ 0x25);
            final int nextInt = this.rand.nextInt(this.rand.nextInt(154 + 94 - 150 + 150) + (0x59 ^ 0x51));
            final int n7 = this.rand.nextInt(0x9F ^ 0x8F) + (0x82 ^ 0x8A);
            if (nextInt < this.worldObj.func_181545_F() || this.rand.nextInt(this.settings.lavaLakeChance / (0xB5 ^ 0xBD)) == 0) {
                new WorldGenLakes(Blocks.lava).generate(this.worldObj, this.rand, blockPos.add(n6, nextInt, n7));
            }
        }
        if (this.settings.useDungeons) {
            int i = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (i < this.settings.dungeonChance) {
                new WorldGenDungeons().generate(this.worldObj, this.rand, blockPos.add(this.rand.nextInt(0x8E ^ 0x9E) + (0x62 ^ 0x6A), this.rand.nextInt(188 + 229 - 416 + 255), this.rand.nextInt(0x72 ^ 0x62) + (0x60 ^ 0x68)));
                ++i;
            }
        }
        biomeGenForCoords.decorate(this.worldObj, this.rand, new BlockPos(n3, "".length(), n4));
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomeGenForCoords, n3 + (0x12 ^ 0x1A), n4 + (0x5A ^ 0x52), 0x6B ^ 0x7B, 0xA5 ^ 0xB5, this.rand);
        final BlockPos add = blockPos.add(0x24 ^ 0x2C, "".length(), 0x3E ^ 0x36);
        int j = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (j < (0xAA ^ 0xBA)) {
            int k = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (k < (0xA1 ^ 0xB1)) {
                final BlockPos precipitationHeight = this.worldObj.getPrecipitationHeight(add.add(j, "".length(), k));
                final BlockPos down = precipitationHeight.down();
                if (this.worldObj.canBlockFreezeWater(down)) {
                    this.worldObj.setBlockState(down, Blocks.ice.getDefaultState(), "  ".length());
                }
                if (this.worldObj.canSnowAt(precipitationHeight, " ".length() != 0)) {
                    this.worldObj.setBlockState(precipitationHeight, Blocks.snow_layer.getDefaultState(), "  ".length());
                }
                ++k;
            }
            ++j;
        }
        BlockFalling.fallInstantly = ("".length() != 0);
    }
    
    @Override
    public void saveExtraData() {
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001a4\u000b6.%\u0019\u0000$$$\u0006\n'3+0", "HUeRA");
        ChunkProviderGenerate.I[" ".length()] = I(">:\u0013+\u001a\n&\u000e(\u0010", "mNaDt");
    }
    
    @Override
    public boolean canSave() {
        return " ".length() != 0;
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos);
        if (this.mapFeaturesEnabled) {
            if (enumCreatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.func_175798_a(blockPos)) {
                return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
            }
            if (enumCreatureType == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.func_175796_a(this.worldObj, blockPos)) {
                return this.oceanMonumentGenerator.func_175799_b();
            }
        }
        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }
    
    public void setBlocksInChunk(final int n, final int n2, final ChunkPrimer chunkPrimer) {
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, n * (0x45 ^ 0x41) - "  ".length(), n2 * (0x5B ^ 0x5F) - "  ".length(), 0x32 ^ 0x38, 0x1 ^ 0xB);
        this.func_147423_a(n * (0x91 ^ 0x95), "".length(), n2 * (0x4C ^ 0x48));
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < (0x50 ^ 0x54)) {
            final int n3 = i * (0x39 ^ 0x3C);
            final int n4 = (i + " ".length()) * (0x36 ^ 0x33);
            int j = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (j < (0xB0 ^ 0xB4)) {
                final int n5 = (n3 + j) * (0x1E ^ 0x3F);
                final int n6 = (n3 + j + " ".length()) * (0x4 ^ 0x25);
                final int n7 = (n4 + j) * (0x34 ^ 0x15);
                final int n8 = (n4 + j + " ".length()) * (0x50 ^ 0x71);
                int k = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                while (k < (0xA4 ^ 0x84)) {
                    final double n9 = 0.125;
                    double n10 = this.field_147434_q[n5 + k];
                    double n11 = this.field_147434_q[n6 + k];
                    double n12 = this.field_147434_q[n7 + k];
                    double n13 = this.field_147434_q[n8 + k];
                    final double n14 = (this.field_147434_q[n5 + k + " ".length()] - n10) * n9;
                    final double n15 = (this.field_147434_q[n6 + k + " ".length()] - n11) * n9;
                    final double n16 = (this.field_147434_q[n7 + k + " ".length()] - n12) * n9;
                    final double n17 = (this.field_147434_q[n8 + k + " ".length()] - n13) * n9;
                    int l = "".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    while (l < (0xB1 ^ 0xB9)) {
                        final double n18 = 0.25;
                        double n19 = n10;
                        double n20 = n11;
                        final double n21 = (n12 - n10) * n18;
                        final double n22 = (n13 - n11) * n18;
                        int length = "".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        while (length < (0x3F ^ 0x3B)) {
                            final double n23 = (n20 - n19) * 0.25;
                            double n24 = n19 - n23;
                            int length2 = "".length();
                            "".length();
                            if (-1 == 1) {
                                throw null;
                            }
                            while (length2 < (0xAA ^ 0xAE)) {
                                if ((n24 += n23) > 0.0) {
                                    chunkPrimer.setBlockState(i * (0x2F ^ 0x2B) + length, k * (0x38 ^ 0x30) + l, j * (0xB2 ^ 0xB6) + length2, Blocks.stone.getDefaultState());
                                    "".length();
                                    if (3 == -1) {
                                        throw null;
                                    }
                                }
                                else if (k * (0x5B ^ 0x53) + l < this.settings.seaLevel) {
                                    chunkPrimer.setBlockState(i * (0x72 ^ 0x76) + length, k * (0x23 ^ 0x2B) + l, j * (0xBF ^ 0xBB) + length2, this.field_177476_s.getDefaultState());
                                }
                                ++length2;
                            }
                            n19 += n21;
                            n20 += n22;
                            ++length;
                        }
                        n10 += n14;
                        n11 += n15;
                        n12 += n16;
                        n13 += n17;
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
        return this.provideChunk(blockPos.getX() >> (0x7B ^ 0x7F), blockPos.getZ() >> (0x9E ^ 0x9A));
    }
    
    public void replaceBlocksForBiome(final int n, final int n2, final ChunkPrimer chunkPrimer, final BiomeGenBase[] array) {
        final double n3 = 0.03125;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, n * (0x87 ^ 0x97), n2 * (0x62 ^ 0x72), 0x8C ^ 0x9C, 0x86 ^ 0x96, n3 * 2.0, n3 * 2.0, 1.0);
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < (0x62 ^ 0x72)) {
            int j = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < (0xA9 ^ 0xB9)) {
                array[j + i * (0x20 ^ 0x30)].genTerrainBlocks(this.worldObj, this.rand, chunkPrimer, n * (0xB7 ^ 0xA7) + i, n2 * (0x7F ^ 0x6F) + j, this.stoneNoise[j + i * (0xD ^ 0x1D)]);
                ++j;
            }
            ++i;
        }
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, n, n2, null);
        }
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, n, n2, null);
        }
    }
    
    @Override
    public String makeString() {
        return ChunkProviderGenerate.I["".length()];
    }
}
