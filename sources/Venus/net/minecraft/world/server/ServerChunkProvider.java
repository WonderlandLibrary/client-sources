/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.ServerWorldLightManager;
import net.minecraft.world.server.TicketManager;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraft.world.storage.SaveFormat;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ServerChunkProvider
extends AbstractChunkProvider {
    private static final List<ChunkStatus> field_217239_c = ChunkStatus.getAll();
    private final TicketManager ticketManager;
    private final ChunkGenerator generator;
    private final ServerWorld world;
    private final Thread mainThread;
    private final ServerWorldLightManager lightManager;
    private final ChunkExecutor executor;
    public final ChunkManager chunkManager;
    private final DimensionSavedDataManager savedData;
    private long lastGameTime;
    private boolean spawnHostiles = true;
    private boolean spawnPassives = true;
    private final long[] recentPositions = new long[4];
    private final ChunkStatus[] recentStatuses = new ChunkStatus[4];
    private final IChunk[] recentChunks = new IChunk[4];
    @Nullable
    private WorldEntitySpawner.EntityDensityManager field_241097_p_;

    public ServerChunkProvider(ServerWorld serverWorld, SaveFormat.LevelSave levelSave, DataFixer dataFixer, TemplateManager templateManager, Executor executor, ChunkGenerator chunkGenerator, int n, boolean bl, IChunkStatusListener iChunkStatusListener, Supplier<DimensionSavedDataManager> supplier) {
        this.world = serverWorld;
        this.executor = new ChunkExecutor(this, serverWorld);
        this.generator = chunkGenerator;
        this.mainThread = Thread.currentThread();
        File file = levelSave.getDimensionFolder(serverWorld.getDimensionKey());
        File file2 = new File(file, "data");
        file2.mkdirs();
        this.savedData = new DimensionSavedDataManager(file2, dataFixer);
        this.chunkManager = new ChunkManager(serverWorld, levelSave, dataFixer, templateManager, executor, this.executor, this, this.getChunkGenerator(), iChunkStatusListener, supplier, n, bl);
        this.lightManager = this.chunkManager.getLightManager();
        this.ticketManager = this.chunkManager.getTicketManager();
        this.invalidateCaches();
    }

    @Override
    public ServerWorldLightManager getLightManager() {
        return this.lightManager;
    }

    @Nullable
    private ChunkHolder func_217213_a(long l) {
        return this.chunkManager.func_219219_b(l);
    }

    public int getLoadedChunksCount() {
        return this.chunkManager.func_219174_c();
    }

    private void func_225315_a(long l, IChunk iChunk, ChunkStatus chunkStatus) {
        for (int i = 3; i > 0; --i) {
            this.recentPositions[i] = this.recentPositions[i - 1];
            this.recentStatuses[i] = this.recentStatuses[i - 1];
            this.recentChunks[i] = this.recentChunks[i - 1];
        }
        this.recentPositions[0] = l;
        this.recentStatuses[0] = chunkStatus;
        this.recentChunks[0] = iChunk;
    }

    @Override
    @Nullable
    public IChunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        IChunk iChunk;
        if (Thread.currentThread() != this.mainThread) {
            return CompletableFuture.supplyAsync(() -> this.lambda$getChunk$0(n, n2, chunkStatus, bl), this.executor).join();
        }
        IProfiler iProfiler = this.world.getProfiler();
        iProfiler.func_230035_c_("getChunk");
        long l = ChunkPos.asLong(n, n2);
        for (int i = 0; i < 4; ++i) {
            if (l != this.recentPositions[i] || chunkStatus != this.recentStatuses[i] || (iChunk = this.recentChunks[i]) == null && bl) continue;
            return iChunk;
        }
        iProfiler.func_230035_c_("getChunkCacheMiss");
        CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = this.func_217233_c(n, n2, chunkStatus, bl);
        this.executor.driveUntil(completableFuture::isDone);
        iChunk = completableFuture.join().map(ServerChunkProvider::lambda$getChunk$1, arg_0 -> ServerChunkProvider.lambda$getChunk$2(bl, arg_0));
        this.func_225315_a(l, iChunk, chunkStatus);
        return iChunk;
    }

    @Override
    @Nullable
    public Chunk getChunkNow(int n, int n2) {
        if (Thread.currentThread() != this.mainThread) {
            return null;
        }
        this.world.getProfiler().func_230035_c_("getChunkNow");
        long l = ChunkPos.asLong(n, n2);
        for (int i = 0; i < 4; ++i) {
            if (l != this.recentPositions[i] || this.recentStatuses[i] != ChunkStatus.FULL) continue;
            IChunk iChunk = this.recentChunks[i];
            return iChunk instanceof Chunk ? (Chunk)iChunk : null;
        }
        ChunkHolder chunkHolder = this.func_217213_a(l);
        if (chunkHolder == null) {
            return null;
        }
        Either<IChunk, ChunkHolder.IChunkLoadingError> either = chunkHolder.func_225410_b(ChunkStatus.FULL).getNow(null);
        if (either == null) {
            return null;
        }
        IChunk iChunk = either.left().orElse(null);
        if (iChunk != null) {
            this.func_225315_a(l, iChunk, ChunkStatus.FULL);
            if (iChunk instanceof Chunk) {
                return (Chunk)iChunk;
            }
        }
        return null;
    }

    private void invalidateCaches() {
        Arrays.fill(this.recentPositions, ChunkPos.SENTINEL);
        Arrays.fill(this.recentStatuses, null);
        Arrays.fill(this.recentChunks, null);
    }

    public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217232_b(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        CompletionStage<Either<IChunk, ChunkHolder.IChunkLoadingError>> completionStage;
        boolean bl2;
        boolean bl3 = bl2 = Thread.currentThread() == this.mainThread;
        if (bl2) {
            completionStage = this.func_217233_c(n, n2, chunkStatus, bl);
            this.executor.driveUntil(() -> completionStage.isDone());
        } else {
            completionStage = CompletableFuture.supplyAsync(() -> this.lambda$func_217232_b$3(n, n2, chunkStatus, bl), this.executor).thenCompose(ServerChunkProvider::lambda$func_217232_b$4);
        }
        return completionStage;
    }

    private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217233_c(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        ChunkPos chunkPos = new ChunkPos(n, n2);
        long l = chunkPos.asLong();
        int n3 = 33 + ChunkStatus.getDistance(chunkStatus);
        ChunkHolder chunkHolder = this.func_217213_a(l);
        if (bl) {
            this.ticketManager.registerWithLevel(TicketType.UNKNOWN, chunkPos, n3, chunkPos);
            if (this.func_217224_a(chunkHolder, n3)) {
                IProfiler iProfiler = this.world.getProfiler();
                iProfiler.startSection("chunkLoad");
                this.func_217235_l();
                chunkHolder = this.func_217213_a(l);
                iProfiler.endSection();
                if (this.func_217224_a(chunkHolder, n3)) {
                    throw Util.pauseDevMode(new IllegalStateException("No chunk holder after ticket has been added"));
                }
            }
        }
        return this.func_217224_a(chunkHolder, n3) ? ChunkHolder.MISSING_CHUNK_FUTURE : chunkHolder.func_219276_a(chunkStatus, this.chunkManager);
    }

    private boolean func_217224_a(@Nullable ChunkHolder chunkHolder, int n) {
        return chunkHolder == null || chunkHolder.getChunkLevel() > n;
    }

    @Override
    public boolean chunkExists(int n, int n2) {
        int n3;
        ChunkHolder chunkHolder = this.func_217213_a(new ChunkPos(n, n2).asLong());
        return !this.func_217224_a(chunkHolder, n3 = 33 + ChunkStatus.getDistance(ChunkStatus.FULL));
    }

    @Override
    public IBlockReader getChunkForLight(int n, int n2) {
        long l = ChunkPos.asLong(n, n2);
        ChunkHolder chunkHolder = this.func_217213_a(l);
        if (chunkHolder == null) {
            return null;
        }
        int n3 = field_217239_c.size() - 1;
        ChunkStatus chunkStatus;
        Optional<IChunk> optional;
        while (!(optional = chunkHolder.func_219301_a(chunkStatus = field_217239_c.get(n3)).getNow(ChunkHolder.MISSING_CHUNK).left()).isPresent()) {
            if (chunkStatus == ChunkStatus.LIGHT.getParent()) {
                return null;
            }
            --n3;
        }
        return optional.get();
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public boolean driveOneTask() {
        return this.executor.driveOne();
    }

    private boolean func_217235_l() {
        boolean bl = this.ticketManager.processUpdates(this.chunkManager);
        boolean bl2 = this.chunkManager.refreshOffThreadCache();
        if (!bl && !bl2) {
            return true;
        }
        this.invalidateCaches();
        return false;
    }

    @Override
    public boolean isChunkLoaded(Entity entity2) {
        long l = ChunkPos.asLong(MathHelper.floor(entity2.getPosX()) >> 4, MathHelper.floor(entity2.getPosZ()) >> 4);
        return this.isChunkLoaded(l, ChunkHolder::getEntityTickingFuture);
    }

    @Override
    public boolean isChunkLoaded(ChunkPos chunkPos) {
        return this.isChunkLoaded(chunkPos.asLong(), ChunkHolder::getEntityTickingFuture);
    }

    @Override
    public boolean canTick(BlockPos blockPos) {
        long l = ChunkPos.asLong(blockPos.getX() >> 4, blockPos.getZ() >> 4);
        return this.isChunkLoaded(l, ChunkHolder::getTickingFuture);
    }

    private boolean isChunkLoaded(long l, Function<ChunkHolder, CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>>> function) {
        ChunkHolder chunkHolder = this.func_217213_a(l);
        if (chunkHolder == null) {
            return true;
        }
        Either<Chunk, ChunkHolder.IChunkLoadingError> either = function.apply(chunkHolder).getNow(ChunkHolder.UNLOADED_CHUNK);
        return either.left().isPresent();
    }

    public void save(boolean bl) {
        this.func_217235_l();
        this.chunkManager.save(bl);
    }

    @Override
    public void close() throws IOException {
        this.save(false);
        this.lightManager.close();
        this.chunkManager.close();
    }

    public void tick(BooleanSupplier booleanSupplier) {
        this.world.getProfiler().startSection("purge");
        this.ticketManager.tick();
        this.func_217235_l();
        this.world.getProfiler().endStartSection("chunks");
        this.tickChunks();
        this.world.getProfiler().endStartSection("unload");
        this.chunkManager.tick(booleanSupplier);
        this.world.getProfiler().endSection();
        this.invalidateCaches();
    }

    private void tickChunks() {
        long l = this.world.getGameTime();
        long l2 = l - this.lastGameTime;
        this.lastGameTime = l;
        IWorldInfo iWorldInfo = this.world.getWorldInfo();
        boolean bl = this.world.isDebug();
        boolean bl2 = this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING);
        if (!bl) {
            WorldEntitySpawner.EntityDensityManager entityDensityManager;
            this.world.getProfiler().startSection("pollingChunks");
            int n = this.world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED);
            boolean bl3 = iWorldInfo.getGameTime() % 400L == 0L;
            this.world.getProfiler().startSection("naturalSpawnCount");
            int n2 = this.ticketManager.getSpawningChunksCount();
            this.field_241097_p_ = entityDensityManager = WorldEntitySpawner.func_234964_a_(n2, this.world.func_241136_z_(), this::func_241098_a_);
            this.world.getProfiler().endSection();
            ArrayList<ChunkHolder> arrayList = Lists.newArrayList(this.chunkManager.getLoadedChunksIterable());
            Collections.shuffle(arrayList);
            arrayList.forEach(arg_0 -> this.lambda$tickChunks$5(l2, bl2, entityDensityManager, bl3, n, arg_0));
            this.world.getProfiler().startSection("customSpawners");
            if (bl2) {
                this.world.func_241123_a_(this.spawnHostiles, this.spawnPassives);
            }
            this.world.getProfiler().endSection();
            this.world.getProfiler().endSection();
        }
        this.chunkManager.tickEntityTracker();
    }

    private void func_241098_a_(long l, Consumer<Chunk> consumer) {
        ChunkHolder chunkHolder = this.func_217213_a(l);
        if (chunkHolder != null) {
            chunkHolder.getBorderFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left().ifPresent(consumer);
        }
    }

    @Override
    public String makeString() {
        return "ServerChunkCache: " + this.getLoadedChunkCount();
    }

    @VisibleForTesting
    public int func_225314_f() {
        return this.executor.getQueueSize();
    }

    public ChunkGenerator getChunkGenerator() {
        return this.generator;
    }

    public int getLoadedChunkCount() {
        return this.chunkManager.getLoadedChunkCount();
    }

    public void markBlockChanged(BlockPos blockPos) {
        int n;
        int n2 = blockPos.getX() >> 4;
        ChunkHolder chunkHolder = this.func_217213_a(ChunkPos.asLong(n2, n = blockPos.getZ() >> 4));
        if (chunkHolder != null) {
            chunkHolder.func_244386_a(blockPos);
        }
    }

    @Override
    public void markLightChanged(LightType lightType, SectionPos sectionPos) {
        this.executor.execute(() -> this.lambda$markLightChanged$6(sectionPos, lightType));
    }

    public <T> void registerTicket(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        this.ticketManager.register(ticketType, chunkPos, n, t);
    }

    public <T> void releaseTicket(TicketType<T> ticketType, ChunkPos chunkPos, int n, T t) {
        this.ticketManager.release(ticketType, chunkPos, n, t);
    }

    @Override
    public void forceChunk(ChunkPos chunkPos, boolean bl) {
        this.ticketManager.forceChunk(chunkPos, bl);
    }

    public void updatePlayerPosition(ServerPlayerEntity serverPlayerEntity) {
        this.chunkManager.updatePlayerPosition(serverPlayerEntity);
    }

    public void untrack(Entity entity2) {
        this.chunkManager.untrack(entity2);
    }

    public void track(Entity entity2) {
        this.chunkManager.track(entity2);
    }

    public void sendToTrackingAndSelf(Entity entity2, IPacket<?> iPacket) {
        this.chunkManager.sendToTrackingAndSelf(entity2, iPacket);
    }

    public void sendToAllTracking(Entity entity2, IPacket<?> iPacket) {
        this.chunkManager.sendToAllTracking(entity2, iPacket);
    }

    public void setViewDistance(int n) {
        this.chunkManager.setViewDistance(n);
    }

    @Override
    public void setAllowedSpawnTypes(boolean bl, boolean bl2) {
        this.spawnHostiles = bl;
        this.spawnPassives = bl2;
    }

    public String getDebugInfo(ChunkPos chunkPos) {
        return this.chunkManager.getDebugInfo(chunkPos);
    }

    public DimensionSavedDataManager getSavedData() {
        return this.savedData;
    }

    public PointOfInterestManager getPointOfInterestManager() {
        return this.chunkManager.getPointOfInterestManager();
    }

    @Nullable
    public WorldEntitySpawner.EntityDensityManager func_241101_k_() {
        return this.field_241097_p_;
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.getLightManager();
    }

    @Override
    public IBlockReader getWorld() {
        return this.getWorld();
    }

    private void lambda$markLightChanged$6(SectionPos sectionPos, LightType lightType) {
        ChunkHolder chunkHolder = this.func_217213_a(sectionPos.asChunkPos().asLong());
        if (chunkHolder != null) {
            chunkHolder.markLightChanged(lightType, sectionPos.getSectionY());
        }
    }

    private void lambda$tickChunks$5(long l, boolean bl, WorldEntitySpawner.EntityDensityManager entityDensityManager, boolean bl2, int n, ChunkHolder chunkHolder) {
        Optional<Chunk> optional = chunkHolder.getTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
        if (optional.isPresent()) {
            this.world.getProfiler().startSection("broadcast");
            chunkHolder.sendChanges(optional.get());
            this.world.getProfiler().endSection();
            Optional<Chunk> optional2 = chunkHolder.getEntityTickingFuture().getNow(ChunkHolder.UNLOADED_CHUNK).left();
            if (optional2.isPresent()) {
                Chunk chunk = optional2.get();
                ChunkPos chunkPos = chunkHolder.getPosition();
                if (!this.chunkManager.isOutsideSpawningRadius(chunkPos)) {
                    chunk.setInhabitedTime(chunk.getInhabitedTime() + l);
                    if (bl && (this.spawnHostiles || this.spawnPassives) && this.world.getWorldBorder().contains(chunk.getPos())) {
                        WorldEntitySpawner.func_234979_a_(this.world, chunk, entityDensityManager, this.spawnPassives, this.spawnHostiles, bl2);
                    }
                    this.world.tickEnvironment(chunk, n);
                }
            }
        }
    }

    private static CompletionStage lambda$func_217232_b$4(CompletableFuture completableFuture) {
        return completableFuture;
    }

    private CompletableFuture lambda$func_217232_b$3(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        return this.func_217233_c(n, n2, chunkStatus, bl);
    }

    private static IChunk lambda$getChunk$2(boolean bl, ChunkHolder.IChunkLoadingError iChunkLoadingError) {
        if (bl) {
            throw Util.pauseDevMode(new IllegalStateException("Chunk not there when requested: " + iChunkLoadingError));
        }
        return null;
    }

    private static IChunk lambda$getChunk$1(IChunk iChunk) {
        return iChunk;
    }

    private IChunk lambda$getChunk$0(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        return this.getChunk(n, n2, chunkStatus, bl);
    }

    final class ChunkExecutor
    extends ThreadTaskExecutor<Runnable> {
        final ServerChunkProvider this$0;

        private ChunkExecutor(ServerChunkProvider serverChunkProvider, World world) {
            this.this$0 = serverChunkProvider;
            super("Chunk source main thread executor for " + world.getDimensionKey().getLocation());
        }

        @Override
        protected Runnable wrapTask(Runnable runnable) {
            return runnable;
        }

        @Override
        protected boolean canRun(Runnable runnable) {
            return false;
        }

        @Override
        protected boolean shouldDeferTasks() {
            return false;
        }

        @Override
        protected Thread getExecutionThread() {
            return this.this$0.mainThread;
        }

        @Override
        protected void run(Runnable runnable) {
            this.this$0.world.getProfiler().func_230035_c_("runTask");
            super.run(runnable);
        }

        @Override
        protected boolean driveOne() {
            if (this.this$0.func_217235_l()) {
                return false;
            }
            this.this$0.lightManager.func_215588_z_();
            return super.driveOne();
        }
    }
}

