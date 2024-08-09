/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.ServerWorldLightManager;

public class ChunkStatus {
    private static final EnumSet<Heightmap.Type> PRE_FEATURES = EnumSet.of(Heightmap.Type.OCEAN_FLOOR_WG, Heightmap.Type.WORLD_SURFACE_WG);
    private static final EnumSet<Heightmap.Type> POST_FEATURES = EnumSet.of(Heightmap.Type.OCEAN_FLOOR, Heightmap.Type.WORLD_SURFACE, Heightmap.Type.MOTION_BLOCKING, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
    private static final ILoadingWorker NOOP_LOADING_WORKER = ChunkStatus::lambda$static$0;
    public static final ChunkStatus EMPTY = ChunkStatus.registerSelective("empty", null, -1, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$1);
    public static final ChunkStatus STRUCTURE_STARTS = ChunkStatus.register("structure_starts", EMPTY, 0, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$2);
    public static final ChunkStatus STRUCTURE_REFERENCES = ChunkStatus.registerSelective("structure_references", STRUCTURE_STARTS, 8, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$3);
    public static final ChunkStatus BIOMES = ChunkStatus.registerSelective("biomes", STRUCTURE_REFERENCES, 0, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$4);
    public static final ChunkStatus NOISE = ChunkStatus.registerSelective("noise", BIOMES, 8, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$5);
    public static final ChunkStatus SURFACE = ChunkStatus.registerSelective("surface", NOISE, 0, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$6);
    public static final ChunkStatus CARVERS = ChunkStatus.registerSelective("carvers", SURFACE, 0, PRE_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$7);
    public static final ChunkStatus LIQUID_CARVERS = ChunkStatus.registerSelective("liquid_carvers", CARVERS, 0, POST_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$8);
    public static final ChunkStatus FEATURES = ChunkStatus.register("features", LIQUID_CARVERS, 8, POST_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$9);
    public static final ChunkStatus LIGHT = ChunkStatus.register("light", FEATURES, 1, POST_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$10, ChunkStatus::lambda$static$11);
    public static final ChunkStatus SPAWN = ChunkStatus.registerSelective("spawn", LIGHT, 0, POST_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$12);
    public static final ChunkStatus HEIGHTMAPS = ChunkStatus.registerSelective("heightmaps", SPAWN, 0, POST_FEATURES, Type.PROTOCHUNK, ChunkStatus::lambda$static$13);
    public static final ChunkStatus FULL = ChunkStatus.register("full", HEIGHTMAPS, 0, POST_FEATURES, Type.LEVELCHUNK, ChunkStatus::lambda$static$14, ChunkStatus::lambda$static$15);
    private static final List<ChunkStatus> STATUS_BY_RANGE = ImmutableList.of(FULL, FEATURES, LIQUID_CARVERS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS);
    private static final IntList RANGE_BY_STATUS = Util.make(new IntArrayList(ChunkStatus.getAll().size()), ChunkStatus::lambda$static$16);
    private final String name;
    private final int ordinal;
    private final ChunkStatus parent;
    private final IGenerationWorker generationWorker;
    private final ILoadingWorker loadingWorker;
    private final int taskRange;
    private final Type type;
    private final EnumSet<Heightmap.Type> heightmaps;

    private static CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> lightChunk(ChunkStatus chunkStatus, ServerWorldLightManager serverWorldLightManager, IChunk iChunk) {
        boolean bl = ChunkStatus.isLighted(chunkStatus, iChunk);
        if (!iChunk.getStatus().isAtLeast(chunkStatus)) {
            ((ChunkPrimer)iChunk).setStatus(chunkStatus);
        }
        return serverWorldLightManager.lightChunk(iChunk, bl).thenApply(Either::left);
    }

    private static ChunkStatus registerSelective(String string, @Nullable ChunkStatus chunkStatus, int n, EnumSet<Heightmap.Type> enumSet, Type type, ISelectiveWorker iSelectiveWorker) {
        return ChunkStatus.register(string, chunkStatus, n, enumSet, type, iSelectiveWorker);
    }

    private static ChunkStatus register(String string, @Nullable ChunkStatus chunkStatus, int n, EnumSet<Heightmap.Type> enumSet, Type type, IGenerationWorker iGenerationWorker) {
        return ChunkStatus.register(string, chunkStatus, n, enumSet, type, iGenerationWorker, NOOP_LOADING_WORKER);
    }

    private static ChunkStatus register(String string, @Nullable ChunkStatus chunkStatus, int n, EnumSet<Heightmap.Type> enumSet, Type type, IGenerationWorker iGenerationWorker, ILoadingWorker iLoadingWorker) {
        return Registry.register(Registry.CHUNK_STATUS, string, new ChunkStatus(string, chunkStatus, n, enumSet, type, iGenerationWorker, iLoadingWorker));
    }

    public static List<ChunkStatus> getAll() {
        ChunkStatus chunkStatus;
        ArrayList<ChunkStatus> arrayList = Lists.newArrayList();
        for (chunkStatus = FULL; chunkStatus.getParent() != chunkStatus; chunkStatus = chunkStatus.getParent()) {
            arrayList.add(chunkStatus);
        }
        arrayList.add(chunkStatus);
        Collections.reverse(arrayList);
        return arrayList;
    }

    private static boolean isLighted(ChunkStatus chunkStatus, IChunk iChunk) {
        return iChunk.getStatus().isAtLeast(chunkStatus) && iChunk.hasLight();
    }

    public static ChunkStatus getStatus(int n) {
        if (n >= STATUS_BY_RANGE.size()) {
            return EMPTY;
        }
        return n < 0 ? FULL : STATUS_BY_RANGE.get(n);
    }

    public static int maxDistance() {
        return STATUS_BY_RANGE.size();
    }

    public static int getDistance(ChunkStatus chunkStatus) {
        return RANGE_BY_STATUS.getInt(chunkStatus.ordinal());
    }

    ChunkStatus(String string, @Nullable ChunkStatus chunkStatus, int n, EnumSet<Heightmap.Type> enumSet, Type type, IGenerationWorker iGenerationWorker, ILoadingWorker iLoadingWorker) {
        this.name = string;
        this.parent = chunkStatus == null ? this : chunkStatus;
        this.generationWorker = iGenerationWorker;
        this.loadingWorker = iLoadingWorker;
        this.taskRange = n;
        this.type = type;
        this.heightmaps = enumSet;
        this.ordinal = chunkStatus == null ? 0 : chunkStatus.ordinal() + 1;
    }

    public int ordinal() {
        return this.ordinal;
    }

    public String getName() {
        return this.name;
    }

    public ChunkStatus getParent() {
        return this.parent;
    }

    public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doGenerationWork(ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> function, List<IChunk> list) {
        return this.generationWorker.doWork(this, serverWorld, chunkGenerator, templateManager, serverWorldLightManager, function, list, list.get(list.size() / 2));
    }

    public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doLoadingWork(ServerWorld serverWorld, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> function, IChunk iChunk) {
        return this.loadingWorker.doWork(this, serverWorld, templateManager, serverWorldLightManager, function, iChunk);
    }

    public int getTaskRange() {
        return this.taskRange;
    }

    public Type getType() {
        return this.type;
    }

    public static ChunkStatus byName(String string) {
        return Registry.CHUNK_STATUS.getOrDefault(ResourceLocation.tryCreate(string));
    }

    public EnumSet<Heightmap.Type> getHeightMaps() {
        return this.heightmaps;
    }

    public boolean isAtLeast(ChunkStatus chunkStatus) {
        return this.ordinal() >= chunkStatus.ordinal();
    }

    public String toString() {
        return Registry.CHUNK_STATUS.getKey(this).toString();
    }

    private static void lambda$static$16(IntArrayList intArrayList) {
        int n = 0;
        for (int i = ChunkStatus.getAll().size() - 1; i >= 0; --i) {
            while (n + 1 < STATUS_BY_RANGE.size() && i <= STATUS_BY_RANGE.get(n + 1).ordinal()) {
                ++n;
            }
            intArrayList.add(0, n);
        }
    }

    private static CompletableFuture lambda$static$15(ChunkStatus chunkStatus, ServerWorld serverWorld, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, IChunk iChunk) {
        return (CompletableFuture)function.apply(iChunk);
    }

    private static CompletableFuture lambda$static$14(ChunkStatus chunkStatus, ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, List list, IChunk iChunk) {
        return (CompletableFuture)function.apply(iChunk);
    }

    private static void lambda$static$13(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
    }

    private static void lambda$static$12(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        chunkGenerator.func_230354_a_(new WorldGenRegion(serverWorld, list));
    }

    private static CompletableFuture lambda$static$11(ChunkStatus chunkStatus, ServerWorld serverWorld, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, IChunk iChunk) {
        return ChunkStatus.lightChunk(chunkStatus, serverWorldLightManager, iChunk);
    }

    private static CompletableFuture lambda$static$10(ChunkStatus chunkStatus, ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, List list, IChunk iChunk) {
        return ChunkStatus.lightChunk(chunkStatus, serverWorldLightManager, iChunk);
    }

    private static CompletableFuture lambda$static$9(ChunkStatus chunkStatus, ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, List list, IChunk iChunk) {
        ChunkPrimer chunkPrimer = (ChunkPrimer)iChunk;
        chunkPrimer.setLightManager(serverWorldLightManager);
        if (!iChunk.getStatus().isAtLeast(chunkStatus)) {
            Heightmap.updateChunkHeightmaps(iChunk, EnumSet.of(Heightmap.Type.MOTION_BLOCKING, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Heightmap.Type.OCEAN_FLOOR, Heightmap.Type.WORLD_SURFACE));
            WorldGenRegion worldGenRegion = new WorldGenRegion(serverWorld, list);
            chunkGenerator.func_230351_a_(worldGenRegion, serverWorld.func_241112_a_().func_241464_a_(worldGenRegion));
            chunkPrimer.setStatus(chunkStatus);
        }
        return CompletableFuture.completedFuture(Either.left(iChunk));
    }

    private static void lambda$static$8(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        chunkGenerator.func_230350_a_(serverWorld.getSeed(), serverWorld.getBiomeManager(), iChunk, GenerationStage.Carving.LIQUID);
    }

    private static void lambda$static$7(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        chunkGenerator.func_230350_a_(serverWorld.getSeed(), serverWorld.getBiomeManager(), iChunk, GenerationStage.Carving.AIR);
    }

    private static void lambda$static$6(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        chunkGenerator.generateSurface(new WorldGenRegion(serverWorld, list), iChunk);
    }

    private static void lambda$static$5(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        WorldGenRegion worldGenRegion = new WorldGenRegion(serverWorld, list);
        chunkGenerator.func_230352_b_(worldGenRegion, serverWorld.func_241112_a_().func_241464_a_(worldGenRegion), iChunk);
    }

    private static void lambda$static$4(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        chunkGenerator.func_242706_a(serverWorld.func_241828_r().getRegistry(Registry.BIOME_KEY), iChunk);
    }

    private static void lambda$static$3(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
        WorldGenRegion worldGenRegion = new WorldGenRegion(serverWorld, list);
        chunkGenerator.func_235953_a_(worldGenRegion, serverWorld.func_241112_a_().func_241464_a_(worldGenRegion), iChunk);
    }

    private static CompletableFuture lambda$static$2(ChunkStatus chunkStatus, ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, List list, IChunk iChunk) {
        if (!iChunk.getStatus().isAtLeast(chunkStatus)) {
            if (serverWorld.getServer().func_240793_aU_().getDimensionGeneratorSettings().doesGenerateFeatures()) {
                chunkGenerator.func_242707_a(serverWorld.func_241828_r(), serverWorld.func_241112_a_(), iChunk, templateManager, serverWorld.getSeed());
            }
            if (iChunk instanceof ChunkPrimer) {
                ((ChunkPrimer)iChunk).setStatus(chunkStatus);
            }
        }
        return CompletableFuture.completedFuture(Either.left(iChunk));
    }

    private static void lambda$static$1(ServerWorld serverWorld, ChunkGenerator chunkGenerator, List list, IChunk iChunk) {
    }

    private static CompletableFuture lambda$static$0(ChunkStatus chunkStatus, ServerWorld serverWorld, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function function, IChunk iChunk) {
        if (iChunk instanceof ChunkPrimer && !iChunk.getStatus().isAtLeast(chunkStatus)) {
            ((ChunkPrimer)iChunk).setStatus(chunkStatus);
        }
        return CompletableFuture.completedFuture(Either.left(iChunk));
    }

    public static enum Type {
        PROTOCHUNK,
        LEVELCHUNK;

    }

    static interface IGenerationWorker {
        public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus var1, ServerWorld var2, ChunkGenerator var3, TemplateManager var4, ServerWorldLightManager var5, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> var6, List<IChunk> var7, IChunk var8);
    }

    static interface ILoadingWorker {
        public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus var1, ServerWorld var2, TemplateManager var3, ServerWorldLightManager var4, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> var5, IChunk var6);
    }

    static interface ISelectiveWorker
    extends IGenerationWorker {
        @Override
        default public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus chunkStatus, ServerWorld serverWorld, ChunkGenerator chunkGenerator, TemplateManager templateManager, ServerWorldLightManager serverWorldLightManager, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> function, List<IChunk> list, IChunk iChunk) {
            if (!iChunk.getStatus().isAtLeast(chunkStatus)) {
                this.doWork(serverWorld, chunkGenerator, list, iChunk);
                if (iChunk instanceof ChunkPrimer) {
                    ((ChunkPrimer)iChunk).setStatus(chunkStatus);
                }
            }
            return CompletableFuture.completedFuture(Either.left(iChunk));
        }

        public void doWork(ServerWorld var1, ChunkGenerator var2, List<IChunk> var3, IChunk var4);
    }
}

