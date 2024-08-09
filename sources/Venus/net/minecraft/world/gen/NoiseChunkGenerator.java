/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.spawner.WorldEntitySpawner;

public final class NoiseChunkGenerator
extends ChunkGenerator {
    public static final Codec<NoiseChunkGenerator> field_236079_d_ = RecordCodecBuilder.create(NoiseChunkGenerator::lambda$static$3);
    private static final float[] field_222561_h = Util.make(new float[13824], NoiseChunkGenerator::lambda$static$4);
    private static final float[] field_236081_j_ = Util.make(new float[25], NoiseChunkGenerator::lambda$static$5);
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private final int verticalNoiseGranularity;
    private final int horizontalNoiseGranularity;
    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;
    protected final SharedSeedRandom randomSeed;
    private final OctavesNoiseGenerator field_222568_o;
    private final OctavesNoiseGenerator field_222569_p;
    private final OctavesNoiseGenerator field_222570_q;
    private final INoiseGenerator surfaceDepthNoise;
    private final OctavesNoiseGenerator field_236082_u_;
    @Nullable
    private final SimplexNoiseGenerator field_236083_v_;
    protected final BlockState defaultBlock;
    protected final BlockState defaultFluid;
    private final long field_236084_w_;
    protected final Supplier<DimensionSettings> field_236080_h_;
    private final int field_236085_x_;

    public NoiseChunkGenerator(BiomeProvider biomeProvider, long l, Supplier<DimensionSettings> supplier) {
        this(biomeProvider, biomeProvider, l, supplier);
    }

    private NoiseChunkGenerator(BiomeProvider biomeProvider, BiomeProvider biomeProvider2, long l, Supplier<DimensionSettings> supplier) {
        super(biomeProvider, biomeProvider2, supplier.get().getStructures(), l);
        this.field_236084_w_ = l;
        DimensionSettings dimensionSettings = supplier.get();
        this.field_236080_h_ = supplier;
        NoiseSettings noiseSettings = dimensionSettings.getNoise();
        this.field_236085_x_ = noiseSettings.func_236169_a_();
        this.verticalNoiseGranularity = noiseSettings.func_236175_f_() * 4;
        this.horizontalNoiseGranularity = noiseSettings.func_236174_e_() * 4;
        this.defaultBlock = dimensionSettings.getDefaultBlock();
        this.defaultFluid = dimensionSettings.getDefaultFluid();
        this.noiseSizeX = 16 / this.horizontalNoiseGranularity;
        this.noiseSizeY = noiseSettings.func_236169_a_() / this.verticalNoiseGranularity;
        this.noiseSizeZ = 16 / this.horizontalNoiseGranularity;
        this.randomSeed = new SharedSeedRandom(l);
        this.field_222568_o = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
        this.field_222569_p = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
        this.field_222570_q = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-7, 0));
        this.surfaceDepthNoise = noiseSettings.func_236178_i_() ? new PerlinNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-3, 0)) : new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-3, 0));
        this.randomSeed.skip(2620);
        this.field_236082_u_ = new OctavesNoiseGenerator(this.randomSeed, IntStream.rangeClosed(-15, 0));
        if (noiseSettings.func_236180_k_()) {
            SharedSeedRandom sharedSeedRandom = new SharedSeedRandom(l);
            sharedSeedRandom.skip(17292);
            this.field_236083_v_ = new SimplexNoiseGenerator(sharedSeedRandom);
        } else {
            this.field_236083_v_ = null;
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return field_236079_d_;
    }

    @Override
    public ChunkGenerator func_230349_a_(long l) {
        return new NoiseChunkGenerator(this.biomeProvider.getBiomeProvider(l), l, this.field_236080_h_);
    }

    public boolean func_236088_a_(long l, RegistryKey<DimensionSettings> registryKey) {
        return this.field_236084_w_ == l && this.field_236080_h_.get().func_242744_a(registryKey);
    }

    private double func_222552_a(int n, int n2, int n3, double d, double d2, double d3, double d4) {
        double d5 = 0.0;
        double d6 = 0.0;
        double d7 = 0.0;
        boolean bl = true;
        double d8 = 1.0;
        for (int i = 0; i < 16; ++i) {
            ImprovedNoiseGenerator improvedNoiseGenerator;
            ImprovedNoiseGenerator improvedNoiseGenerator2;
            double d9 = OctavesNoiseGenerator.maintainPrecision((double)n * d * d8);
            double d10 = OctavesNoiseGenerator.maintainPrecision((double)n2 * d2 * d8);
            double d11 = OctavesNoiseGenerator.maintainPrecision((double)n3 * d * d8);
            double d12 = d2 * d8;
            ImprovedNoiseGenerator improvedNoiseGenerator3 = this.field_222568_o.getOctave(i);
            if (improvedNoiseGenerator3 != null) {
                d5 += improvedNoiseGenerator3.func_215456_a(d9, d10, d11, d12, (double)n2 * d12) / d8;
            }
            if ((improvedNoiseGenerator2 = this.field_222569_p.getOctave(i)) != null) {
                d6 += improvedNoiseGenerator2.func_215456_a(d9, d10, d11, d12, (double)n2 * d12) / d8;
            }
            if (i < 8 && (improvedNoiseGenerator = this.field_222570_q.getOctave(i)) != null) {
                d7 += improvedNoiseGenerator.func_215456_a(OctavesNoiseGenerator.maintainPrecision((double)n * d3 * d8), OctavesNoiseGenerator.maintainPrecision((double)n2 * d4 * d8), OctavesNoiseGenerator.maintainPrecision((double)n3 * d3 * d8), d4 * d8, (double)n2 * d4 * d8) / d8;
            }
            d8 /= 2.0;
        }
        return MathHelper.clampedLerp(d5 / 512.0, d6 / 512.0, (d7 / 10.0 + 1.0) / 2.0);
    }

    private double[] func_222547_b(int n, int n2) {
        double[] dArray = new double[this.noiseSizeY + 1];
        this.fillNoiseColumn(dArray, n, n2);
        return dArray;
    }

    private void fillNoiseColumn(double[] dArray, int n, int n2) {
        double d;
        double d2;
        double d3;
        double d4;
        NoiseSettings noiseSettings = this.field_236080_h_.get().getNoise();
        if (this.field_236083_v_ != null) {
            d4 = EndBiomeProvider.getRandomNoise(this.field_236083_v_, n, n2) - 8.0f;
            d3 = d4 > 0.0 ? 0.25 : 1.0;
        } else {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            int n3 = 2;
            int n4 = this.func_230356_f_();
            float f4 = this.biomeProvider.getNoiseBiome(n, n4, n2).getDepth();
            for (int i = -2; i <= 2; ++i) {
                for (int j = -2; j <= 2; ++j) {
                    float f5;
                    float f6;
                    Biome biome = this.biomeProvider.getNoiseBiome(n + i, n4, n2 + j);
                    float f7 = biome.getDepth();
                    float f8 = biome.getScale();
                    if (noiseSettings.func_236181_l_() && f7 > 0.0f) {
                        f6 = 1.0f + f7 * 2.0f;
                        f5 = 1.0f + f8 * 4.0f;
                    } else {
                        f6 = f7;
                        f5 = f8;
                    }
                    float f9 = f7 > f4 ? 0.5f : 1.0f;
                    float f10 = f9 * field_236081_j_[i + 2 + (j + 2) * 5] / (f6 + 2.0f);
                    f += f5 * f10;
                    f2 += f6 * f10;
                    f3 += f10;
                }
            }
            float f11 = f2 / f3;
            float f12 = f / f3;
            d2 = f11 * 0.5f - 0.125f;
            d = f12 * 0.9f + 0.1f;
            d4 = d2 * 0.265625;
            d3 = 96.0 / d;
        }
        double d5 = 684.412 * noiseSettings.func_236171_b_().func_236151_a_();
        double d6 = 684.412 * noiseSettings.func_236171_b_().func_236153_b_();
        double d7 = d5 / noiseSettings.func_236171_b_().func_236154_c_();
        double d8 = d6 / noiseSettings.func_236171_b_().func_236155_d_();
        d2 = noiseSettings.func_236172_c_().func_236186_a_();
        d = noiseSettings.func_236172_c_().func_236188_b_();
        double d9 = noiseSettings.func_236172_c_().func_236189_c_();
        double d10 = noiseSettings.func_236173_d_().func_236186_a_();
        double d11 = noiseSettings.func_236173_d_().func_236188_b_();
        double d12 = noiseSettings.func_236173_d_().func_236189_c_();
        double d13 = noiseSettings.func_236179_j_() ? this.func_236095_c_(n, n2) : 0.0;
        double d14 = noiseSettings.func_236176_g_();
        double d15 = noiseSettings.func_236177_h_();
        for (int i = 0; i <= this.noiseSizeY; ++i) {
            double d16;
            double d17 = this.func_222552_a(n, i, n2, d5, d6, d7, d8);
            double d18 = 1.0 - (double)i * 2.0 / (double)this.noiseSizeY + d13;
            double d19 = d18 * d14 + d15;
            double d20 = (d19 + d4) * d3;
            d17 = d20 > 0.0 ? (d17 += d20 * 4.0) : (d17 += d20);
            if (d > 0.0) {
                d16 = ((double)(this.noiseSizeY - i) - d9) / d;
                d17 = MathHelper.clampedLerp(d2, d17, d16);
            }
            if (d11 > 0.0) {
                d16 = ((double)i - d12) / d11;
                d17 = MathHelper.clampedLerp(d10, d17, d16);
            }
            dArray[i] = d17;
        }
    }

    private double func_236095_c_(int n, int n2) {
        double d = this.field_236082_u_.getValue(n * 200, 10.0, n2 * 200, 1.0, 0.0, false);
        double d2 = d < 0.0 ? -d * 0.3 : d;
        double d3 = d2 * 24.575625 - 2.0;
        return d3 < 0.0 ? d3 * 0.009486607142857142 : Math.min(d3, 1.0) * 0.006640625;
    }

    @Override
    public int getHeight(int n, int n2, Heightmap.Type type) {
        return this.func_236087_a_(n, n2, null, type.getHeightLimitPredicate());
    }

    @Override
    public IBlockReader func_230348_a_(int n, int n2) {
        BlockState[] blockStateArray = new BlockState[this.noiseSizeY * this.verticalNoiseGranularity];
        this.func_236087_a_(n, n2, blockStateArray, null);
        return new Blockreader(blockStateArray);
    }

    private int func_236087_a_(int n, int n2, @Nullable BlockState[] blockStateArray, @Nullable Predicate<BlockState> predicate) {
        int n3 = Math.floorDiv(n, this.horizontalNoiseGranularity);
        int n4 = Math.floorDiv(n2, this.horizontalNoiseGranularity);
        int n5 = Math.floorMod(n, this.horizontalNoiseGranularity);
        int n6 = Math.floorMod(n2, this.horizontalNoiseGranularity);
        double d = (double)n5 / (double)this.horizontalNoiseGranularity;
        double d2 = (double)n6 / (double)this.horizontalNoiseGranularity;
        double[][] dArrayArray = new double[][]{this.func_222547_b(n3, n4), this.func_222547_b(n3, n4 + 1), this.func_222547_b(n3 + 1, n4), this.func_222547_b(n3 + 1, n4 + 1)};
        for (int i = this.noiseSizeY - 1; i >= 0; --i) {
            double d3 = dArrayArray[0][i];
            double d4 = dArrayArray[5][i];
            double d5 = dArrayArray[5][i];
            double d6 = dArrayArray[5][i];
            double d7 = dArrayArray[0][i + 1];
            double d8 = dArrayArray[5][i + 1];
            double d9 = dArrayArray[5][i + 1];
            double d10 = dArrayArray[5][i + 1];
            for (int j = this.verticalNoiseGranularity - 1; j >= 0; --j) {
                double d11 = (double)j / (double)this.verticalNoiseGranularity;
                double d12 = MathHelper.lerp3(d11, d, d2, d3, d7, d5, d9, d4, d8, d6, d10);
                int n7 = i * this.verticalNoiseGranularity + j;
                BlockState blockState = this.func_236086_a_(d12, n7);
                if (blockStateArray != null) {
                    blockStateArray[n7] = blockState;
                }
                if (predicate == null || !predicate.test(blockState)) continue;
                return n7 + 1;
            }
        }
        return 1;
    }

    protected BlockState func_236086_a_(double d, int n) {
        BlockState blockState = d > 0.0 ? this.defaultBlock : (n < this.func_230356_f_() ? this.defaultFluid : AIR);
        return blockState;
    }

    @Override
    public void generateSurface(WorldGenRegion worldGenRegion, IChunk iChunk) {
        ChunkPos chunkPos = iChunk.getPos();
        int n = chunkPos.x;
        int n2 = chunkPos.z;
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
        sharedSeedRandom.setBaseChunkSeed(n, n2);
        ChunkPos chunkPos2 = iChunk.getPos();
        int n3 = chunkPos2.getXStart();
        int n4 = chunkPos2.getZStart();
        double d = 0.0625;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n5 = n3 + i;
                int n6 = n4 + j;
                int n7 = iChunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, i, j) + 1;
                double d2 = this.surfaceDepthNoise.noiseAt((double)n5 * 0.0625, (double)n6 * 0.0625, 0.0625, (double)i * 0.0625) * 15.0;
                worldGenRegion.getBiome(mutable.setPos(n3 + i, n7, n4 + j)).buildSurface(sharedSeedRandom, iChunk, n5, n6, n7, d2, this.defaultBlock, this.defaultFluid, this.func_230356_f_(), worldGenRegion.getSeed());
            }
        }
        this.makeBedrock(iChunk, sharedSeedRandom);
    }

    private void makeBedrock(IChunk iChunk, Random random2) {
        boolean bl;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int n = iChunk.getPos().getXStart();
        int n2 = iChunk.getPos().getZStart();
        DimensionSettings dimensionSettings = this.field_236080_h_.get();
        int n3 = dimensionSettings.func_236118_f_();
        int n4 = this.field_236085_x_ - 1 - dimensionSettings.func_236117_e_();
        int n5 = 5;
        boolean bl2 = n4 + 4 >= 0 && n4 < this.field_236085_x_;
        boolean bl3 = bl = n3 + 4 >= 0 && n3 < this.field_236085_x_;
        if (bl2 || bl) {
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(n, 0, n2, n + 15, 0, n2 + 15)) {
                int n6;
                if (bl2) {
                    for (n6 = 0; n6 < 5; ++n6) {
                        if (n6 > random2.nextInt(5)) continue;
                        iChunk.setBlockState(mutable.setPos(blockPos.getX(), n4 - n6, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                    }
                }
                if (!bl) continue;
                for (n6 = 4; n6 >= 0; --n6) {
                    if (n6 > random2.nextInt(5)) continue;
                    iChunk.setBlockState(mutable.setPos(blockPos.getX(), n3 + n6, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                }
            }
        }
    }

    @Override
    public void func_230352_b_(IWorld iWorld, StructureManager structureManager, IChunk iChunk) {
        ObjectArrayList objectArrayList = new ObjectArrayList(10);
        ObjectArrayList objectArrayList2 = new ObjectArrayList(32);
        ChunkPos chunkPos = iChunk.getPos();
        int n = chunkPos.x;
        int n2 = chunkPos.z;
        int n3 = n << 4;
        int n4 = n2 << 4;
        for (Structure<?> structure : Structure.field_236384_t_) {
            structureManager.func_235011_a_(SectionPos.from(chunkPos, 0), structure).forEach(arg_0 -> NoiseChunkGenerator.lambda$func_230352_b_$6(chunkPos, objectArrayList, n3, n4, objectArrayList2, arg_0));
        }
        Object object = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];
        for (int i = 0; i < this.noiseSizeZ + 1; ++i) {
            object[0][i] = new double[this.noiseSizeY + 1];
            this.fillNoiseColumn((double[])object[0][i], n * this.noiseSizeX, n2 * this.noiseSizeZ + i);
            object[5][i] = new double[this.noiseSizeY + 1];
        }
        ChunkPrimer chunkPrimer = (ChunkPrimer)iChunk;
        Heightmap heightmap = chunkPrimer.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = chunkPrimer.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Iterator iterator2 = objectArrayList.iterator();
        Iterator iterator3 = objectArrayList2.iterator();
        for (int i = 0; i < this.noiseSizeX; ++i) {
            int n5;
            for (n5 = 0; n5 < this.noiseSizeZ + 1; ++n5) {
                this.fillNoiseColumn((double[])object[5][n5], n * this.noiseSizeX + i + 1, n2 * this.noiseSizeZ + n5);
            }
            for (n5 = 0; n5 < this.noiseSizeZ; ++n5) {
                ChunkSection chunkSection = chunkPrimer.getSection(15);
                chunkSection.lock();
                for (int j = this.noiseSizeY - 1; j >= 0; --j) {
                    Object object2 = object[0][n5][j];
                    Object object3 = object[0][n5 + 1][j];
                    Object object4 = object[5][n5][j];
                    Object object5 = object[5][n5 + 1][j];
                    Object object6 = object[0][n5][j + 1];
                    Object object7 = object[0][n5 + 1][j + 1];
                    Object object8 = object[5][n5][j + 1];
                    Object object9 = object[5][n5 + 1][j + 1];
                    for (int k = this.verticalNoiseGranularity - 1; k >= 0; --k) {
                        int n6 = j * this.verticalNoiseGranularity + k;
                        int n7 = n6 & 0xF;
                        int n8 = n6 >> 4;
                        if (chunkSection.getYLocation() >> 4 != n8) {
                            chunkSection.unlock();
                            chunkSection = chunkPrimer.getSection(n8);
                            chunkSection.lock();
                        }
                        double d = (double)k / (double)this.verticalNoiseGranularity;
                        double d2 = MathHelper.lerp(d, (double)object2, (double)object6);
                        double d3 = MathHelper.lerp(d, (double)object4, (double)object8);
                        double d4 = MathHelper.lerp(d, (double)object3, (double)object7);
                        double d5 = MathHelper.lerp(d, (double)object5, (double)object9);
                        for (int i2 = 0; i2 < this.horizontalNoiseGranularity; ++i2) {
                            int n9 = n3 + i * this.horizontalNoiseGranularity + i2;
                            int n10 = n9 & 0xF;
                            double d6 = (double)i2 / (double)this.horizontalNoiseGranularity;
                            double d7 = MathHelper.lerp(d6, d2, d3);
                            double d8 = MathHelper.lerp(d6, d4, d5);
                            for (int i3 = 0; i3 < this.horizontalNoiseGranularity; ++i3) {
                                int n11;
                                int n12;
                                Object object10;
                                int n13 = n4 + n5 * this.horizontalNoiseGranularity + i3;
                                int n14 = n13 & 0xF;
                                double d9 = (double)i3 / (double)this.horizontalNoiseGranularity;
                                double d10 = MathHelper.lerp(d9, d7, d8);
                                double d11 = MathHelper.clamp(d10 / 200.0, -1.0, 1.0);
                                d11 = d11 / 2.0 - d11 * d11 * d11 / 24.0;
                                while (iterator2.hasNext()) {
                                    object10 = (StructurePiece)iterator2.next();
                                    MutableBoundingBox mutableBoundingBox = ((StructurePiece)object10).getBoundingBox();
                                    n12 = Math.max(0, Math.max(mutableBoundingBox.minX - n9, n9 - mutableBoundingBox.maxX));
                                    n11 = n6 - (mutableBoundingBox.minY + (object10 instanceof AbstractVillagePiece ? ((AbstractVillagePiece)object10).getGroundLevelDelta() : 0));
                                    int n15 = Math.max(0, Math.max(mutableBoundingBox.minZ - n13, n13 - mutableBoundingBox.maxZ));
                                    d11 += NoiseChunkGenerator.func_222556_a(n12, n11, n15) * 0.8;
                                }
                                iterator2.back(objectArrayList.size());
                                while (iterator3.hasNext()) {
                                    object10 = (JigsawJunction)iterator3.next();
                                    int n16 = n9 - ((JigsawJunction)object10).getSourceX();
                                    n12 = n6 - ((JigsawJunction)object10).getSourceGroundY();
                                    n11 = n13 - ((JigsawJunction)object10).getSourceZ();
                                    d11 += NoiseChunkGenerator.func_222556_a(n16, n12, n11) * 0.4;
                                }
                                iterator3.back(objectArrayList2.size());
                                object10 = this.func_236086_a_(d11, n6);
                                if (object10 == AIR) continue;
                                if (((AbstractBlock.AbstractBlockState)object10).getLightValue() != 0) {
                                    mutable.setPos(n9, n6, n13);
                                    chunkPrimer.addLightPosition(mutable);
                                }
                                chunkSection.setBlockState(n10, n7, n14, (BlockState)object10, true);
                                heightmap.update(n10, n6, n14, (BlockState)object10);
                                heightmap2.update(n10, n6, n14, (BlockState)object10);
                            }
                        }
                    }
                }
                chunkSection.unlock();
            }
            Object object11 = object[0];
            object[0] = object[5];
            object[1] = object11;
        }
    }

    private static double func_222556_a(int n, int n2, int n3) {
        int n4 = n + 12;
        int n5 = n2 + 12;
        int n6 = n3 + 12;
        if (n4 >= 0 && n4 < 24) {
            if (n5 >= 0 && n5 < 24) {
                return n6 >= 0 && n6 < 24 ? (double)field_222561_h[n6 * 24 * 24 + n4 * 24 + n5] : 0.0;
            }
            return 0.0;
        }
        return 0.0;
    }

    private static double func_222554_b(int n, int n2, int n3) {
        double d = n * n + n3 * n3;
        double d2 = (double)n2 + 0.5;
        double d3 = d2 * d2;
        double d4 = Math.pow(Math.E, -(d3 / 16.0 + d / 16.0));
        double d5 = -d2 * MathHelper.fastInvSqrt(d3 / 2.0 + d / 2.0) / 2.0;
        return d5 * d4;
    }

    @Override
    public int func_230355_e_() {
        return this.field_236085_x_;
    }

    @Override
    public int func_230356_f_() {
        return this.field_236080_h_.get().func_236119_g_();
    }

    @Override
    public List<MobSpawnInfo.Spawners> func_230353_a_(Biome biome, StructureManager structureManager, EntityClassification entityClassification, BlockPos blockPos) {
        if (structureManager.func_235010_a_(blockPos, true, Structure.field_236374_j_).isValid()) {
            if (entityClassification == EntityClassification.MONSTER) {
                return Structure.field_236374_j_.getSpawnList();
            }
            if (entityClassification == EntityClassification.CREATURE) {
                return Structure.field_236374_j_.getCreatureSpawnList();
            }
        }
        if (entityClassification == EntityClassification.MONSTER) {
            if (structureManager.func_235010_a_(blockPos, false, Structure.field_236366_b_).isValid()) {
                return Structure.field_236366_b_.getSpawnList();
            }
            if (structureManager.func_235010_a_(blockPos, false, Structure.field_236376_l_).isValid()) {
                return Structure.field_236376_l_.getSpawnList();
            }
            if (structureManager.func_235010_a_(blockPos, true, Structure.field_236378_n_).isValid()) {
                return Structure.field_236378_n_.getSpawnList();
            }
        }
        return super.func_230353_a_(biome, structureManager, entityClassification, blockPos);
    }

    @Override
    public void func_230354_a_(WorldGenRegion worldGenRegion) {
        if (!this.field_236080_h_.get().func_236120_h_()) {
            int n = worldGenRegion.getMainChunkX();
            int n2 = worldGenRegion.getMainChunkZ();
            Biome biome = worldGenRegion.getBiome(new ChunkPos(n, n2).asBlockPos());
            SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
            sharedSeedRandom.setDecorationSeed(worldGenRegion.getSeed(), n << 4, n2 << 4);
            WorldEntitySpawner.performWorldGenSpawning(worldGenRegion, biome, n, n2, sharedSeedRandom);
        }
    }

    private static void lambda$func_230352_b_$6(ChunkPos chunkPos, ObjectList objectList, int n, int n2, ObjectList objectList2, StructureStart structureStart) {
        for (StructurePiece structurePiece : structureStart.getComponents()) {
            if (!structurePiece.func_214810_a(chunkPos, 1)) continue;
            if (structurePiece instanceof AbstractVillagePiece) {
                AbstractVillagePiece abstractVillagePiece = (AbstractVillagePiece)structurePiece;
                JigsawPattern.PlacementBehaviour placementBehaviour = abstractVillagePiece.getJigsawPiece().getPlacementBehaviour();
                if (placementBehaviour == JigsawPattern.PlacementBehaviour.RIGID) {
                    objectList.add(abstractVillagePiece);
                }
                for (JigsawJunction jigsawJunction : abstractVillagePiece.getJunctions()) {
                    int n3 = jigsawJunction.getSourceX();
                    int n4 = jigsawJunction.getSourceZ();
                    if (n3 <= n - 12 || n4 <= n2 - 12 || n3 >= n + 15 + 12 || n4 >= n2 + 15 + 12) continue;
                    objectList2.add(jigsawJunction);
                }
                continue;
            }
            objectList.add(structurePiece);
        }
    }

    private static void lambda$static$5(float[] fArray) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                float f;
                fArray[i + 2 + (j + 2) * 5] = f = 10.0f / MathHelper.sqrt((float)(i * i + j * j) + 0.2f);
            }
        }
    }

    private static void lambda$static$4(float[] fArray) {
        for (int i = 0; i < 24; ++i) {
            for (int j = 0; j < 24; ++j) {
                for (int k = 0; k < 24; ++k) {
                    fArray[i * 24 * 24 + j * 24 + k] = (float)NoiseChunkGenerator.func_222554_b(j - 12, k - 12, i - 12);
                }
            }
        }
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BiomeProvider.CODEC.fieldOf("biome_source")).forGetter(NoiseChunkGenerator::lambda$static$0), ((MapCodec)Codec.LONG.fieldOf("seed")).stable().forGetter(NoiseChunkGenerator::lambda$static$1), ((MapCodec)DimensionSettings.field_236098_b_.fieldOf("settings")).forGetter(NoiseChunkGenerator::lambda$static$2)).apply(instance, instance.stable(NoiseChunkGenerator::new));
    }

    private static Supplier lambda$static$2(NoiseChunkGenerator noiseChunkGenerator) {
        return noiseChunkGenerator.field_236080_h_;
    }

    private static Long lambda$static$1(NoiseChunkGenerator noiseChunkGenerator) {
        return noiseChunkGenerator.field_236084_w_;
    }

    private static BiomeProvider lambda$static$0(NoiseChunkGenerator noiseChunkGenerator) {
        return noiseChunkGenerator.biomeProvider;
    }
}

