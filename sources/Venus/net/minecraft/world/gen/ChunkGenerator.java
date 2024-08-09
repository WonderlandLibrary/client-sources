/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.EntityClassification;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.DebugChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.gen.settings.StructureSpreadSettings;
import net.minecraft.world.server.ServerWorld;

public abstract class ChunkGenerator {
    public static final Codec<ChunkGenerator> field_235948_a_ = Registry.CHUNK_GENERATOR_CODEC.dispatchStable(ChunkGenerator::func_230347_a_, Function.identity());
    protected final BiomeProvider biomeProvider;
    protected final BiomeProvider field_235949_c_;
    private final DimensionStructuresSettings settings;
    private final long field_235950_e_;
    private final List<ChunkPos> field_235951_f_ = Lists.newArrayList();

    public ChunkGenerator(BiomeProvider biomeProvider, DimensionStructuresSettings dimensionStructuresSettings) {
        this(biomeProvider, biomeProvider, dimensionStructuresSettings, 0L);
    }

    public ChunkGenerator(BiomeProvider biomeProvider, BiomeProvider biomeProvider2, DimensionStructuresSettings dimensionStructuresSettings, long l) {
        this.biomeProvider = biomeProvider;
        this.field_235949_c_ = biomeProvider2;
        this.settings = dimensionStructuresSettings;
        this.field_235950_e_ = l;
    }

    private void func_235958_g_() {
        StructureSpreadSettings structureSpreadSettings;
        if (this.field_235951_f_.isEmpty() && (structureSpreadSettings = this.settings.func_236199_b_()) != null && structureSpreadSettings.func_236663_c_() != 0) {
            ArrayList<Biome> arrayList = Lists.newArrayList();
            for (Biome biome : this.biomeProvider.getBiomes()) {
                if (!biome.getGenerationSettings().hasStructure(Structure.field_236375_k_)) continue;
                arrayList.add(biome);
            }
            int n = structureSpreadSettings.func_236660_a_();
            int n2 = structureSpreadSettings.func_236663_c_();
            int n3 = structureSpreadSettings.func_236662_b_();
            Random random2 = new Random();
            random2.setSeed(this.field_235950_e_);
            double d = random2.nextDouble() * Math.PI * 2.0;
            int n4 = 0;
            int n5 = 0;
            for (int i = 0; i < n2; ++i) {
                double d2 = (double)(4 * n + n * n5 * 6) + (random2.nextDouble() - 0.5) * (double)n * 2.5;
                int n6 = (int)Math.round(Math.cos(d) * d2);
                int n7 = (int)Math.round(Math.sin(d) * d2);
                BlockPos blockPos = this.biomeProvider.findBiomePosition((n6 << 4) + 8, 0, (n7 << 4) + 8, 112, arrayList::contains, random2);
                if (blockPos != null) {
                    n6 = blockPos.getX() >> 4;
                    n7 = blockPos.getZ() >> 4;
                }
                this.field_235951_f_.add(new ChunkPos(n6, n7));
                d += Math.PI * 2 / (double)n3;
                if (++n4 != n3) continue;
                n4 = 0;
                n3 += 2 * n3 / (++n5 + 1);
                n3 = Math.min(n3, n2 - i);
                d += random2.nextDouble() * Math.PI * 2.0;
            }
        }
    }

    protected abstract Codec<? extends ChunkGenerator> func_230347_a_();

    public abstract ChunkGenerator func_230349_a_(long var1);

    public void func_242706_a(Registry<Biome> registry, IChunk iChunk) {
        ChunkPos chunkPos = iChunk.getPos();
        ((ChunkPrimer)iChunk).setBiomes(new BiomeContainer(registry, chunkPos, this.field_235949_c_));
    }

    public void func_230350_a_(long l, BiomeManager biomeManager, IChunk iChunk, GenerationStage.Carving carving) {
        BiomeManager biomeManager2 = biomeManager.copyWithProvider(this.biomeProvider);
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
        int n = 8;
        ChunkPos chunkPos = iChunk.getPos();
        int n2 = chunkPos.x;
        int n3 = chunkPos.z;
        BiomeGenerationSettings biomeGenerationSettings = this.biomeProvider.getNoiseBiome(chunkPos.x << 2, 0, chunkPos.z << 2).getGenerationSettings();
        BitSet bitSet = ((ChunkPrimer)iChunk).getOrAddCarvingMask(carving);
        for (int i = n2 - 8; i <= n2 + 8; ++i) {
            for (int j = n3 - 8; j <= n3 + 8; ++j) {
                List<Supplier<ConfiguredCarver<?>>> list = biomeGenerationSettings.getCarvers(carving);
                ListIterator<Supplier<ConfiguredCarver<?>>> listIterator2 = list.listIterator();
                while (listIterator2.hasNext()) {
                    int n4 = listIterator2.nextIndex();
                    ConfiguredCarver<?> configuredCarver = listIterator2.next().get();
                    sharedSeedRandom.setLargeFeatureSeed(l + (long)n4, i, j);
                    if (!configuredCarver.shouldCarve(sharedSeedRandom, i, j)) continue;
                    configuredCarver.carveRegion(iChunk, biomeManager2::getBiome, sharedSeedRandom, this.func_230356_f_(), i, j, n2, n3, bitSet);
                }
            }
        }
    }

    @Nullable
    public BlockPos func_235956_a_(ServerWorld serverWorld, Structure<?> structure, BlockPos blockPos, int n, boolean bl) {
        if (!this.biomeProvider.hasStructure(structure)) {
            return null;
        }
        if (structure == Structure.field_236375_k_) {
            this.func_235958_g_();
            BlockPos blockPos2 = null;
            double d = Double.MAX_VALUE;
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (ChunkPos chunkPos : this.field_235951_f_) {
                mutable.setPos((chunkPos.x << 4) + 8, 32, (chunkPos.z << 4) + 8);
                double d2 = mutable.distanceSq(blockPos);
                if (blockPos2 == null) {
                    blockPos2 = new BlockPos(mutable);
                    d = d2;
                    continue;
                }
                if (!(d2 < d)) continue;
                blockPos2 = new BlockPos(mutable);
                d = d2;
            }
            return blockPos2;
        }
        StructureSeparationSettings structureSeparationSettings = this.settings.func_236197_a_(structure);
        return structureSeparationSettings == null ? null : structure.func_236388_a_(serverWorld, serverWorld.func_241112_a_(), blockPos, n, bl, serverWorld.getSeed(), structureSeparationSettings);
    }

    public void func_230351_a_(WorldGenRegion worldGenRegion, StructureManager structureManager) {
        int n = worldGenRegion.getMainChunkX();
        int n2 = worldGenRegion.getMainChunkZ();
        int n3 = n * 16;
        int n4 = n2 * 16;
        BlockPos blockPos = new BlockPos(n3, 0, n4);
        Biome biome = this.biomeProvider.getNoiseBiome((n << 2) + 2, 2, (n2 << 2) + 2);
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
        long l = sharedSeedRandom.setDecorationSeed(worldGenRegion.getSeed(), n3, n4);
        try {
            biome.generateFeatures(structureManager, this, worldGenRegion, l, sharedSeedRandom, blockPos);
        } catch (Exception exception) {
            CrashReport crashReport = CrashReport.makeCrashReport(exception, "Biome decoration");
            crashReport.makeCategory("Generation").addDetail("CenterX", n).addDetail("CenterZ", n2).addDetail("Seed", l).addDetail("Biome", biome);
            throw new ReportedException(crashReport);
        }
    }

    public abstract void generateSurface(WorldGenRegion var1, IChunk var2);

    public void func_230354_a_(WorldGenRegion worldGenRegion) {
    }

    public DimensionStructuresSettings func_235957_b_() {
        return this.settings;
    }

    public int getGroundHeight() {
        return 1;
    }

    public BiomeProvider getBiomeProvider() {
        return this.field_235949_c_;
    }

    public int func_230355_e_() {
        return 1;
    }

    public List<MobSpawnInfo.Spawners> func_230353_a_(Biome biome, StructureManager structureManager, EntityClassification entityClassification, BlockPos blockPos) {
        return biome.getMobSpawnInfo().getSpawners(entityClassification);
    }

    public void func_242707_a(DynamicRegistries dynamicRegistries, StructureManager structureManager, IChunk iChunk, TemplateManager templateManager, long l) {
        ChunkPos chunkPos = iChunk.getPos();
        Biome biome = this.biomeProvider.getNoiseBiome((chunkPos.x << 2) + 2, 0, (chunkPos.z << 2) + 2);
        this.func_242705_a(StructureFeatures.field_244145_k, dynamicRegistries, structureManager, iChunk, templateManager, l, chunkPos, biome);
        for (Supplier<StructureFeature<?, ?>> supplier : biome.getGenerationSettings().getStructures()) {
            this.func_242705_a(supplier.get(), dynamicRegistries, structureManager, iChunk, templateManager, l, chunkPos, biome);
        }
    }

    private void func_242705_a(StructureFeature<?, ?> structureFeature, DynamicRegistries dynamicRegistries, StructureManager structureManager, IChunk iChunk, TemplateManager templateManager, long l, ChunkPos chunkPos, Biome biome) {
        StructureStart<?> structureStart = structureManager.func_235013_a_(SectionPos.from(iChunk.getPos(), 0), (Structure<?>)structureFeature.field_236268_b_, iChunk);
        int n = structureStart != null ? structureStart.getRefCount() : 0;
        StructureSeparationSettings structureSeparationSettings = this.settings.func_236197_a_((Structure<?>)structureFeature.field_236268_b_);
        if (structureSeparationSettings != null) {
            StructureStart<?> structureStart2 = structureFeature.func_242771_a(dynamicRegistries, this, this.biomeProvider, templateManager, l, chunkPos, biome, n, structureSeparationSettings);
            structureManager.func_235014_a_(SectionPos.from(iChunk.getPos(), 0), (Structure<?>)structureFeature.field_236268_b_, structureStart2, iChunk);
        }
    }

    public void func_235953_a_(ISeedReader iSeedReader, StructureManager structureManager, IChunk iChunk) {
        int n = 8;
        int n2 = iChunk.getPos().x;
        int n3 = iChunk.getPos().z;
        int n4 = n2 << 4;
        int n5 = n3 << 4;
        SectionPos sectionPos = SectionPos.from(iChunk.getPos(), 0);
        for (int i = n2 - 8; i <= n2 + 8; ++i) {
            for (int j = n3 - 8; j <= n3 + 8; ++j) {
                long l = ChunkPos.asLong(i, j);
                for (StructureStart<?> structureStart : iSeedReader.getChunk(i, j).getStructureStarts().values()) {
                    try {
                        if (structureStart == StructureStart.DUMMY || !structureStart.getBoundingBox().intersectsWith(n4, n5, n4 + 15, n5 + 15)) continue;
                        structureManager.func_235012_a_(sectionPos, structureStart.getStructure(), l, iChunk);
                        DebugPacketSender.sendStructureStart(iSeedReader, structureStart);
                    } catch (Exception exception) {
                        CrashReport crashReport = CrashReport.makeCrashReport(exception, "Generating structure reference");
                        CrashReportCategory crashReportCategory = crashReport.makeCategory("Structure");
                        crashReportCategory.addDetail("Id", () -> ChunkGenerator.lambda$func_235953_a_$0(structureStart));
                        crashReportCategory.addDetail("Name", () -> ChunkGenerator.lambda$func_235953_a_$1(structureStart));
                        crashReportCategory.addDetail("Class", () -> ChunkGenerator.lambda$func_235953_a_$2(structureStart));
                        throw new ReportedException(crashReport);
                    }
                }
            }
        }
    }

    public abstract void func_230352_b_(IWorld var1, StructureManager var2, IChunk var3);

    public int func_230356_f_() {
        return 0;
    }

    public abstract int getHeight(int var1, int var2, Heightmap.Type var3);

    public abstract IBlockReader func_230348_a_(int var1, int var2);

    public int getNoiseHeight(int n, int n2, Heightmap.Type type) {
        return this.getHeight(n, n2, type);
    }

    public int getNoiseHeightMinusOne(int n, int n2, Heightmap.Type type) {
        return this.getHeight(n, n2, type) - 1;
    }

    public boolean func_235952_a_(ChunkPos chunkPos) {
        this.func_235958_g_();
        return this.field_235951_f_.contains(chunkPos);
    }

    private static String lambda$func_235953_a_$2(StructureStart structureStart) throws Exception {
        return structureStart.getStructure().getClass().getCanonicalName();
    }

    private static String lambda$func_235953_a_$1(StructureStart structureStart) throws Exception {
        return structureStart.getStructure().getStructureName();
    }

    private static String lambda$func_235953_a_$0(StructureStart structureStart) throws Exception {
        return Registry.STRUCTURE_FEATURE.getKey(structureStart.getStructure()).toString();
    }

    static {
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, "noise", NoiseChunkGenerator.field_236079_d_);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, "flat", FlatChunkGenerator.field_236069_d_);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, "debug", DebugChunkGenerator.field_236066_e_);
    }
}

