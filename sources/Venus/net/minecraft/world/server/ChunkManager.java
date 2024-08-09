/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.network.play.server.SSetPassengersPacket;
import net.minecraft.network.play.server.SUpdateChunkPositionPacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.palette.UpgradeData;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.GameRules;
import net.minecraft.world.TrackedEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ChunkTaskPriorityQueue;
import net.minecraft.world.chunk.ChunkTaskPriorityQueueSorter;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.PlayerGenerationTracker;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.ChunkSerializer;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.ServerWorldLightManager;
import net.minecraft.world.server.TicketManager;
import net.minecraft.world.server.TicketType;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.SaveFormat;
import net.optifine.reflect.Reflector;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkManager
extends ChunkLoader
implements ChunkHolder.IPlayerProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final int MAX_LOADED_LEVEL = 33 + ChunkStatus.maxDistance();
    private final Long2ObjectLinkedOpenHashMap<ChunkHolder> loadedChunks = new Long2ObjectLinkedOpenHashMap();
    private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> immutableLoadedChunks = this.loadedChunks.clone();
    private final Long2ObjectLinkedOpenHashMap<ChunkHolder> chunksToUnload = new Long2ObjectLinkedOpenHashMap();
    private final LongSet loadedPositions = new LongOpenHashSet();
    private final ServerWorld world;
    private final ServerWorldLightManager lightManager;
    private final ThreadTaskExecutor<Runnable> mainThread;
    private final ChunkGenerator generator;
    private final Supplier<DimensionSavedDataManager> field_219259_m;
    private final PointOfInterestManager pointOfInterestManager;
    private final LongSet unloadableChunks = new LongOpenHashSet();
    private boolean immutableLoadedChunksDirty;
    private final ChunkTaskPriorityQueueSorter field_219263_q;
    private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_219264_r;
    private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_219265_s;
    private final IChunkStatusListener field_219266_t;
    private final ProxyTicketManager ticketManager;
    private final AtomicInteger field_219268_v = new AtomicInteger();
    private final TemplateManager templateManager;
    private final File dimensionDirectory;
    private final PlayerGenerationTracker playerGenerationTracker = new PlayerGenerationTracker();
    private final Int2ObjectMap<EntityTracker> entities = new Int2ObjectOpenHashMap<EntityTracker>();
    private final Long2ByteMap field_241087_z_ = new Long2ByteOpenHashMap();
    private final Queue<Runnable> saveTasks = Queues.newConcurrentLinkedQueue();
    private int viewDistance;

    public ChunkManager(ServerWorld serverWorld, SaveFormat.LevelSave levelSave, DataFixer dataFixer, TemplateManager templateManager, Executor executor, ThreadTaskExecutor<Runnable> threadTaskExecutor, IChunkLightProvider iChunkLightProvider, ChunkGenerator chunkGenerator, IChunkStatusListener iChunkStatusListener, Supplier<DimensionSavedDataManager> supplier, int n, boolean bl) {
        super(new File(levelSave.getDimensionFolder(serverWorld.getDimensionKey()), "region"), dataFixer, bl);
        this.templateManager = templateManager;
        this.dimensionDirectory = levelSave.getDimensionFolder(serverWorld.getDimensionKey());
        this.world = serverWorld;
        this.generator = chunkGenerator;
        this.mainThread = threadTaskExecutor;
        DelegatedTaskExecutor<Runnable> delegatedTaskExecutor = DelegatedTaskExecutor.create(executor, "worldgen");
        ITaskExecutor<Runnable> iTaskExecutor = ITaskExecutor.inline("main", threadTaskExecutor::enqueue);
        this.field_219266_t = iChunkStatusListener;
        DelegatedTaskExecutor<Runnable> delegatedTaskExecutor2 = DelegatedTaskExecutor.create(executor, "light");
        this.field_219263_q = new ChunkTaskPriorityQueueSorter(ImmutableList.of(delegatedTaskExecutor, iTaskExecutor, delegatedTaskExecutor2), executor, Integer.MAX_VALUE);
        this.field_219264_r = this.field_219263_q.func_219087_a(delegatedTaskExecutor, true);
        this.field_219265_s = this.field_219263_q.func_219087_a(iTaskExecutor, true);
        this.lightManager = new ServerWorldLightManager(iChunkLightProvider, this, this.world.getDimensionType().hasSkyLight(), delegatedTaskExecutor2, this.field_219263_q.func_219087_a(delegatedTaskExecutor2, true));
        this.ticketManager = new ProxyTicketManager(this, executor, threadTaskExecutor);
        this.field_219259_m = supplier;
        this.pointOfInterestManager = new PointOfInterestManager(new File(this.dimensionDirectory, "poi"), dataFixer, bl);
        this.setViewDistance(n);
    }

    private static double getDistanceSquaredToChunk(ChunkPos chunkPos, Entity entity2) {
        double d = chunkPos.x * 16 + 8;
        double d2 = chunkPos.z * 16 + 8;
        double d3 = d - entity2.getPosX();
        double d4 = d2 - entity2.getPosZ();
        return d3 * d3 + d4 * d4;
    }

    private static int func_219215_b(ChunkPos chunkPos, ServerPlayerEntity serverPlayerEntity, boolean bl) {
        int n;
        int n2;
        if (bl) {
            SectionPos sectionPos = serverPlayerEntity.getManagedSectionPos();
            n2 = sectionPos.getSectionX();
            n = sectionPos.getSectionZ();
        } else {
            n2 = MathHelper.floor(serverPlayerEntity.getPosX() / 16.0);
            n = MathHelper.floor(serverPlayerEntity.getPosZ() / 16.0);
        }
        return ChunkManager.getChunkDistance(chunkPos, n2, n);
    }

    private static int getChunkDistance(ChunkPos chunkPos, int n, int n2) {
        int n3 = chunkPos.x - n;
        int n4 = chunkPos.z - n2;
        return Math.max(Math.abs(n3), Math.abs(n4));
    }

    protected ServerWorldLightManager getLightManager() {
        return this.lightManager;
    }

    @Nullable
    protected ChunkHolder func_219220_a(long l) {
        return this.loadedChunks.get(l);
    }

    @Nullable
    protected ChunkHolder func_219219_b(long l) {
        return this.immutableLoadedChunks.get(l);
    }

    protected IntSupplier func_219191_c(long l) {
        return () -> this.lambda$func_219191_c$0(l);
    }

    public String getDebugInfo(ChunkPos chunkPos) {
        ChunkHolder chunkHolder = this.func_219219_b(chunkPos.asLong());
        if (chunkHolder == null) {
            return "null";
        }
        String string = chunkHolder.getChunkLevel() + "\n";
        ChunkStatus chunkStatus = chunkHolder.func_219285_d();
        IChunk iChunk = chunkHolder.func_219287_e();
        if (chunkStatus != null) {
            string = string + "St: \u00a7" + chunkStatus.ordinal() + chunkStatus + "\u00a7r\n";
        }
        if (iChunk != null) {
            string = string + "Ch: \u00a7" + iChunk.getStatus().ordinal() + iChunk.getStatus() + "\u00a7r\n";
        }
        ChunkHolder.LocationType locationType = chunkHolder.func_219300_g();
        string = string + "\u00a7" + locationType.ordinal() + locationType;
        return string + "\u00a7r";
    }

    private CompletableFuture<Either<List<IChunk>, ChunkHolder.IChunkLoadingError>> func_219236_a(ChunkPos chunkPos, int n, IntFunction<ChunkStatus> intFunction) {
        ArrayList<CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> arrayList = Lists.newArrayList();
        int n2 = chunkPos.x;
        int n3 = chunkPos.z;
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                int n4 = Math.max(Math.abs(j), Math.abs(i));
                ChunkPos chunkPos2 = new ChunkPos(n2 + j, n3 + i);
                long l = chunkPos2.asLong();
                ChunkHolder chunkHolder = this.func_219220_a(l);
                if (chunkHolder == null) {
                    return CompletableFuture.completedFuture(Either.right(new ChunkHolder.IChunkLoadingError(){
                        final ChunkPos val$chunkpos;
                        final ChunkManager this$0;
                        {
                            this.this$0 = chunkManager;
                            this.val$chunkpos = chunkPos;
                        }

                        public String toString() {
                            return "Unloaded " + this.val$chunkpos.toString();
                        }
                    }));
                }
                ChunkStatus chunkStatus = intFunction.apply(n4);
                CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkHolder.func_219276_a(chunkStatus, this);
                arrayList.add(completableFuture);
            }
        }
        CompletableFuture completableFuture = Util.gather(arrayList);
        return completableFuture.thenApply(arg_0 -> this.lambda$func_219236_a$1(n2, n, n3, arg_0));
    }

    public CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> func_219188_b(ChunkPos chunkPos) {
        return this.func_219236_a(chunkPos, 2, ChunkManager::lambda$func_219188_b$2).thenApplyAsync(ChunkManager::lambda$func_219188_b$4, (Executor)this.mainThread);
    }

    @Nullable
    private ChunkHolder setChunkLevel(long l, int n, @Nullable ChunkHolder chunkHolder, int n2) {
        if (n2 > MAX_LOADED_LEVEL && n > MAX_LOADED_LEVEL) {
            return chunkHolder;
        }
        if (chunkHolder != null) {
            chunkHolder.setChunkLevel(n);
        }
        if (chunkHolder != null) {
            if (n > MAX_LOADED_LEVEL) {
                this.unloadableChunks.add(l);
            } else {
                this.unloadableChunks.remove(l);
            }
        }
        if (n <= MAX_LOADED_LEVEL && chunkHolder == null) {
            chunkHolder = this.chunksToUnload.remove(l);
            if (chunkHolder != null) {
                chunkHolder.setChunkLevel(n);
            } else {
                chunkHolder = new ChunkHolder(new ChunkPos(l), n, this.lightManager, this.field_219263_q, this);
            }
            this.loadedChunks.put(l, chunkHolder);
            this.immutableLoadedChunksDirty = true;
        }
        return chunkHolder;
    }

    @Override
    public void close() throws IOException {
        try {
            this.field_219263_q.close();
            this.pointOfInterestManager.close();
        } finally {
            super.close();
        }
    }

    protected void save(boolean bl) {
        if (bl) {
            List list = this.immutableLoadedChunks.values().stream().filter(ChunkHolder::isAccessible).peek(ChunkHolder::updateAccessible).collect(Collectors.toList());
            MutableBoolean mutableBoolean = new MutableBoolean();
            do {
                mutableBoolean.setFalse();
                list.stream().map(this::lambda$save$5).filter(ChunkManager::lambda$save$6).filter(this::chunkSave).forEach(arg_0 -> ChunkManager.lambda$save$7(mutableBoolean, arg_0));
            } while (mutableBoolean.isTrue());
            this.scheduleUnloads(ChunkManager::lambda$save$8);
            this.func_227079_i_();
            LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)this.dimensionDirectory.getName());
        } else {
            this.immutableLoadedChunks.values().stream().filter(ChunkHolder::isAccessible).forEach(this::lambda$save$9);
        }
    }

    protected void tick(BooleanSupplier booleanSupplier) {
        IProfiler iProfiler = this.world.getProfiler();
        iProfiler.startSection("poi");
        this.pointOfInterestManager.tick(booleanSupplier);
        iProfiler.endStartSection("chunk_unload");
        if (!this.world.isSaveDisabled()) {
            this.scheduleUnloads(booleanSupplier);
        }
        iProfiler.endSection();
    }

    private void scheduleUnloads(BooleanSupplier booleanSupplier) {
        Runnable runnable;
        LongIterator longIterator = this.unloadableChunks.iterator();
        int n = 0;
        while (longIterator.hasNext() && (booleanSupplier.getAsBoolean() || n < 200 || this.unloadableChunks.size() > 2000)) {
            long l = longIterator.nextLong();
            ChunkHolder chunkHolder = this.loadedChunks.remove(l);
            if (chunkHolder != null) {
                this.chunksToUnload.put(l, chunkHolder);
                this.immutableLoadedChunksDirty = true;
                ++n;
                this.scheduleSave(l, chunkHolder);
            }
            longIterator.remove();
        }
        while ((booleanSupplier.getAsBoolean() || this.saveTasks.size() > 2000) && (runnable = this.saveTasks.poll()) != null) {
            runnable.run();
        }
    }

    private void scheduleSave(long l, ChunkHolder chunkHolder) {
        CompletableFuture<IChunk> completableFuture = chunkHolder.func_219302_f();
        ((CompletableFuture)completableFuture.thenAcceptAsync(arg_0 -> this.lambda$scheduleSave$10(chunkHolder, completableFuture, l, arg_0), this.saveTasks::add)).whenComplete((arg_0, arg_1) -> ChunkManager.lambda$scheduleSave$11(chunkHolder, arg_0, arg_1));
    }

    protected boolean refreshOffThreadCache() {
        if (!this.immutableLoadedChunksDirty) {
            return true;
        }
        this.immutableLoadedChunks = this.loadedChunks.clone();
        this.immutableLoadedChunksDirty = false;
        return false;
    }

    public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_219244_a(ChunkHolder chunkHolder, ChunkStatus chunkStatus) {
        ChunkPos chunkPos = chunkHolder.getPosition();
        if (chunkStatus == ChunkStatus.EMPTY) {
            return this.chunkLoad(chunkPos);
        }
        CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkHolder.func_219276_a(chunkStatus.getParent(), this);
        return completableFuture.thenComposeAsync(arg_0 -> this.lambda$func_219244_a$13(chunkStatus, chunkPos, chunkHolder, arg_0), (Executor)this.mainThread);
    }

    private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> chunkLoad(ChunkPos chunkPos) {
        return CompletableFuture.supplyAsync(() -> this.lambda$chunkLoad$14(chunkPos), this.mainThread);
    }

    private void func_241089_g_(ChunkPos chunkPos) {
        this.field_241087_z_.put(chunkPos.asLong(), (byte)-1);
    }

    private byte func_241088_a_(ChunkPos chunkPos, ChunkStatus.Type type) {
        return this.field_241087_z_.put(chunkPos.asLong(), (byte)(type == ChunkStatus.Type.PROTOCHUNK ? -1 : 1));
    }

    private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> chunkGenerate(ChunkHolder chunkHolder, ChunkStatus chunkStatus) {
        ChunkPos chunkPos = chunkHolder.getPosition();
        CompletableFuture<Either<List<IChunk>, ChunkHolder.IChunkLoadingError>> completableFuture = this.func_219236_a(chunkPos, chunkStatus.getTaskRange(), arg_0 -> this.lambda$chunkGenerate$15(chunkStatus, arg_0));
        this.world.getProfiler().func_230036_c_(() -> ChunkManager.lambda$chunkGenerate$16(chunkStatus));
        return completableFuture.thenComposeAsync(arg_0 -> this.lambda$chunkGenerate$20(chunkStatus, chunkHolder, chunkPos, arg_0), arg_0 -> this.lambda$chunkGenerate$21(chunkHolder, arg_0));
    }

    protected void func_219209_c(ChunkPos chunkPos) {
        this.mainThread.enqueue(Util.namedRunnable(() -> this.lambda$func_219209_c$22(chunkPos), () -> ChunkManager.lambda$func_219209_c$23(chunkPos)));
    }

    private ChunkStatus func_219205_a(ChunkStatus chunkStatus, int n) {
        ChunkStatus chunkStatus2 = n == 0 ? chunkStatus.getParent() : ChunkStatus.getStatus(ChunkStatus.getDistance(chunkStatus) + n);
        return chunkStatus2;
    }

    private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_219200_b(ChunkHolder chunkHolder) {
        CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkHolder.func_219301_a(ChunkStatus.FULL.getParent());
        return completableFuture.thenApplyAsync(arg_0 -> this.lambda$func_219200_b$26(chunkHolder, arg_0), arg_0 -> this.lambda$func_219200_b$27(chunkHolder, arg_0));
    }

    public CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> func_219179_a(ChunkHolder chunkHolder) {
        ChunkPos chunkPos = chunkHolder.getPosition();
        CompletableFuture<Either<List<IChunk>, ChunkHolder.IChunkLoadingError>> completableFuture = this.func_219236_a(chunkPos, 1, ChunkManager::lambda$func_219179_a$28);
        CompletionStage completionStage = completableFuture.thenApplyAsync(ChunkManager::lambda$func_219179_a$30, arg_0 -> this.lambda$func_219179_a$31(chunkHolder, arg_0));
        ((CompletableFuture)completionStage).thenAcceptAsync(arg_0 -> this.lambda$func_219179_a$34(chunkPos, arg_0), arg_0 -> this.lambda$func_219179_a$35(chunkHolder, arg_0));
        return completionStage;
    }

    public CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> func_222961_b(ChunkHolder chunkHolder) {
        return chunkHolder.func_219276_a(ChunkStatus.FULL, this).thenApplyAsync(ChunkManager::lambda$func_222961_b$37, arg_0 -> this.lambda$func_222961_b$38(chunkHolder, arg_0));
    }

    public int func_219174_c() {
        return this.field_219268_v.get();
    }

    private boolean chunkSave(IChunk iChunk) {
        this.pointOfInterestManager.saveIfDirty(iChunk.getPos());
        if (!iChunk.isModified()) {
            return true;
        }
        iChunk.setLastSaveTime(this.world.getGameTime());
        iChunk.setModified(false);
        ChunkPos chunkPos = iChunk.getPos();
        try {
            ChunkStatus chunkStatus = iChunk.getStatus();
            if (chunkStatus.getType() != ChunkStatus.Type.LEVELCHUNK) {
                if (this.func_241090_h_(chunkPos)) {
                    return false;
                }
                if (chunkStatus == ChunkStatus.EMPTY && iChunk.getStructureStarts().values().stream().noneMatch(StructureStart::isValid)) {
                    return false;
                }
            }
            this.world.getProfiler().func_230035_c_("chunkSave");
            CompoundNBT compoundNBT = ChunkSerializer.write(this.world, iChunk);
            if (Reflector.ChunkDataEvent_Save_Constructor.exists()) {
                World world = (World)Reflector.call(iChunk, Reflector.ForgeIChunk_getWorldForge, new Object[0]);
                Reflector.postForgeBusEvent(Reflector.ChunkDataEvent_Save_Constructor, iChunk, world != null ? world : this.world, compoundNBT);
            }
            this.writeChunk(chunkPos, compoundNBT);
            this.func_241088_a_(chunkPos, chunkStatus.getType());
            return true;
        } catch (Exception exception) {
            LOGGER.error("Failed to save chunk {},{}", (Object)chunkPos.x, (Object)chunkPos.z, (Object)exception);
            return true;
        }
    }

    private boolean func_241090_h_(ChunkPos chunkPos) {
        CompoundNBT compoundNBT;
        byte by = this.field_241087_z_.get(chunkPos.asLong());
        if (by != 0) {
            return by == 1;
        }
        try {
            compoundNBT = this.loadChunkData(chunkPos);
            if (compoundNBT == null) {
                this.func_241089_g_(chunkPos);
                return false;
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to read chunk {}", (Object)chunkPos, (Object)exception);
            this.func_241089_g_(chunkPos);
            return true;
        }
        ChunkStatus.Type type = ChunkSerializer.getChunkStatus(compoundNBT);
        return this.func_241088_a_(chunkPos, type) == 1;
    }

    protected void setViewDistance(int n) {
        int n2 = MathHelper.clamp(n + 1, 3, 64);
        if (n2 != this.viewDistance) {
            int n3 = this.viewDistance;
            this.viewDistance = n2;
            this.ticketManager.setViewDistance(this.viewDistance);
            for (ChunkHolder chunkHolder : this.loadedChunks.values()) {
                ChunkPos chunkPos = chunkHolder.getPosition();
                IPacket[] iPacketArray = new IPacket[2];
                this.getTrackingPlayers(chunkPos, true).forEach(arg_0 -> this.lambda$setViewDistance$39(chunkPos, n3, iPacketArray, arg_0));
            }
        }
    }

    protected void setChunkLoadedAtClient(ServerPlayerEntity serverPlayerEntity, ChunkPos chunkPos, IPacket<?>[] iPacketArray, boolean bl, boolean bl2) {
        if (serverPlayerEntity.world == this.world) {
            ChunkHolder chunkHolder;
            if (Reflector.ForgeEventFactory_fireChunkWatch.exists()) {
                Reflector.ForgeEventFactory_fireChunkWatch.call(bl, bl2, serverPlayerEntity, chunkPos, this.world);
            }
            if (bl2 && !bl && (chunkHolder = this.func_219219_b(chunkPos.asLong())) != null) {
                Chunk chunk = chunkHolder.getChunkIfComplete();
                if (chunk != null) {
                    this.sendChunkData(serverPlayerEntity, iPacketArray, chunk);
                }
                DebugPacketSender.sendChuckPos(this.world, chunkPos);
            }
            if (!bl2 && bl) {
                serverPlayerEntity.sendChunkUnload(chunkPos);
            }
        }
    }

    public int getLoadedChunkCount() {
        return this.immutableLoadedChunks.size();
    }

    protected ProxyTicketManager getTicketManager() {
        return this.ticketManager;
    }

    protected Iterable<ChunkHolder> getLoadedChunksIterable() {
        return Iterables.unmodifiableIterable(this.immutableLoadedChunks.values());
    }

    void func_225406_a(Writer writer) throws IOException {
        CSVWriter cSVWriter = CSVWriter.func_225428_a().func_225423_a("x").func_225423_a("z").func_225423_a("level").func_225423_a("in_memory").func_225423_a("status").func_225423_a("full_status").func_225423_a("accessible_ready").func_225423_a("ticking_ready").func_225423_a("entity_ticking_ready").func_225423_a("ticket").func_225423_a("spawning").func_225423_a("entity_count").func_225423_a("block_entity_count").func_225422_a(writer);
        for (Long2ObjectMap.Entry entry : this.immutableLoadedChunks.long2ObjectEntrySet()) {
            ChunkPos chunkPos = new ChunkPos(entry.getLongKey());
            ChunkHolder chunkHolder = (ChunkHolder)entry.getValue();
            Optional<IChunk> optional = Optional.ofNullable(chunkHolder.func_219287_e());
            Optional<Object> optional2 = optional.flatMap(ChunkManager::lambda$func_225406_a$40);
            cSVWriter.func_225426_a(new Object[]{chunkPos.x, chunkPos.z, chunkHolder.getChunkLevel(), optional.isPresent(), optional.map(IChunk::getStatus).orElse(null), optional2.map(Chunk::getLocationType).orElse(null), ChunkManager.func_225402_a(chunkHolder.getBorderFuture()), ChunkManager.func_225402_a(chunkHolder.getTickingFuture()), ChunkManager.func_225402_a(chunkHolder.getEntityTickingFuture()), this.ticketManager.func_225413_c(entry.getLongKey()), !this.isOutsideSpawningRadius(chunkPos), optional2.map(ChunkManager::lambda$func_225406_a$41).orElse(0), optional2.map(ChunkManager::lambda$func_225406_a$42).orElse(0)});
        }
    }

    private static String func_225402_a(CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completableFuture) {
        try {
            Either<Chunk, ChunkHolder.IChunkLoadingError> either = completableFuture.getNow(null);
            return either != null ? either.map(ChunkManager::lambda$func_225402_a$43, ChunkManager::lambda$func_225402_a$44) : "not completed";
        } catch (CompletionException completionException) {
            return "failed " + completionException.getCause().getMessage();
        } catch (CancellationException cancellationException) {
            return "cancelled";
        }
    }

    @Nullable
    private CompoundNBT loadChunkData(ChunkPos chunkPos) throws IOException {
        CompoundNBT compoundNBT = this.readChunk(chunkPos);
        return compoundNBT == null ? null : this.func_235968_a_(this.world.getDimensionKey(), this.field_219259_m, compoundNBT);
    }

    boolean isOutsideSpawningRadius(ChunkPos chunkPos) {
        long l = chunkPos.asLong();
        return !this.ticketManager.isOutsideSpawningRadius(l) ? true : this.playerGenerationTracker.getGeneratingPlayers(l).noneMatch(arg_0 -> ChunkManager.lambda$isOutsideSpawningRadius$45(chunkPos, arg_0));
    }

    private boolean cannotGenerateChunks(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity.isSpectator() && !this.world.getGameRules().getBoolean(GameRules.SPECTATORS_GENERATE_CHUNKS);
    }

    void setPlayerTracking(ServerPlayerEntity serverPlayerEntity, boolean bl) {
        boolean bl2 = this.cannotGenerateChunks(serverPlayerEntity);
        boolean bl3 = this.playerGenerationTracker.cannotGenerateChunks(serverPlayerEntity);
        int n = MathHelper.floor(serverPlayerEntity.getPosX()) >> 4;
        int n2 = MathHelper.floor(serverPlayerEntity.getPosZ()) >> 4;
        if (bl) {
            this.playerGenerationTracker.addPlayer(ChunkPos.asLong(n, n2), serverPlayerEntity, bl2);
            this.func_223489_c(serverPlayerEntity);
            if (!bl2) {
                this.ticketManager.updatePlayerPosition(SectionPos.from(serverPlayerEntity), serverPlayerEntity);
            }
        } else {
            SectionPos sectionPos = serverPlayerEntity.getManagedSectionPos();
            this.playerGenerationTracker.removePlayer(sectionPos.asChunkPos().asLong(), serverPlayerEntity);
            if (!bl3) {
                this.ticketManager.removePlayer(sectionPos, serverPlayerEntity);
            }
        }
        for (int i = n - this.viewDistance; i <= n + this.viewDistance; ++i) {
            for (int j = n2 - this.viewDistance; j <= n2 + this.viewDistance; ++j) {
                ChunkPos chunkPos = new ChunkPos(i, j);
                this.setChunkLoadedAtClient(serverPlayerEntity, chunkPos, new IPacket[2], !bl, bl);
            }
        }
    }

    private SectionPos func_223489_c(ServerPlayerEntity serverPlayerEntity) {
        SectionPos sectionPos = SectionPos.from(serverPlayerEntity);
        serverPlayerEntity.setManagedSectionPos(sectionPos);
        serverPlayerEntity.connection.sendPacket(new SUpdateChunkPositionPacket(sectionPos.getSectionX(), sectionPos.getSectionZ()));
        return sectionPos;
    }

    public void updatePlayerPosition(ServerPlayerEntity serverPlayerEntity) {
        boolean bl;
        for (EntityTracker entityTracker : this.entities.values()) {
            if (entityTracker.entity == serverPlayerEntity) {
                entityTracker.updateTrackingState(this.world.getPlayers());
                continue;
            }
            entityTracker.updateTrackingState(serverPlayerEntity);
        }
        int n = MathHelper.floor(serverPlayerEntity.getPosX()) >> 4;
        int n2 = MathHelper.floor(serverPlayerEntity.getPosZ()) >> 4;
        SectionPos sectionPos = serverPlayerEntity.getManagedSectionPos();
        SectionPos sectionPos2 = SectionPos.from(serverPlayerEntity);
        long l = sectionPos.asChunkPos().asLong();
        long l2 = sectionPos2.asChunkPos().asLong();
        boolean bl2 = this.playerGenerationTracker.canGeneratePlayer(serverPlayerEntity);
        boolean bl3 = this.cannotGenerateChunks(serverPlayerEntity);
        boolean bl4 = bl = sectionPos.asLong() != sectionPos2.asLong();
        if (bl || bl2 != bl3) {
            this.func_223489_c(serverPlayerEntity);
            if (!bl2) {
                this.ticketManager.removePlayer(sectionPos, serverPlayerEntity);
            }
            if (!bl3) {
                this.ticketManager.updatePlayerPosition(sectionPos2, serverPlayerEntity);
            }
            if (!bl2 && bl3) {
                this.playerGenerationTracker.disableGeneration(serverPlayerEntity);
            }
            if (bl2 && !bl3) {
                this.playerGenerationTracker.enableGeneration(serverPlayerEntity);
            }
            if (l != l2) {
                this.playerGenerationTracker.updatePlayerPosition(l, l2, serverPlayerEntity);
            }
        }
        int n3 = sectionPos.getSectionX();
        int n4 = sectionPos.getSectionZ();
        if (Math.abs(n3 - n) <= this.viewDistance * 2 && Math.abs(n4 - n2) <= this.viewDistance * 2) {
            int n5 = Math.min(n, n3) - this.viewDistance;
            int n6 = Math.min(n2, n4) - this.viewDistance;
            int n7 = Math.max(n, n3) + this.viewDistance;
            int n8 = Math.max(n2, n4) + this.viewDistance;
            for (int i = n5; i <= n7; ++i) {
                for (int j = n6; j <= n8; ++j) {
                    ChunkPos chunkPos = new ChunkPos(i, j);
                    boolean bl5 = ChunkManager.getChunkDistance(chunkPos, n3, n4) <= this.viewDistance;
                    boolean bl6 = ChunkManager.getChunkDistance(chunkPos, n, n2) <= this.viewDistance;
                    this.setChunkLoadedAtClient(serverPlayerEntity, chunkPos, new IPacket[2], bl5, bl6);
                }
            }
        } else {
            boolean bl7;
            boolean bl8;
            ChunkPos chunkPos;
            int n9;
            int n10;
            for (n10 = n3 - this.viewDistance; n10 <= n3 + this.viewDistance; ++n10) {
                for (n9 = n4 - this.viewDistance; n9 <= n4 + this.viewDistance; ++n9) {
                    chunkPos = new ChunkPos(n10, n9);
                    bl8 = true;
                    bl7 = false;
                    this.setChunkLoadedAtClient(serverPlayerEntity, chunkPos, new IPacket[2], true, true);
                }
            }
            for (n10 = n - this.viewDistance; n10 <= n + this.viewDistance; ++n10) {
                for (n9 = n2 - this.viewDistance; n9 <= n2 + this.viewDistance; ++n9) {
                    chunkPos = new ChunkPos(n10, n9);
                    bl8 = false;
                    bl7 = true;
                    this.setChunkLoadedAtClient(serverPlayerEntity, chunkPos, new IPacket[2], false, false);
                }
            }
        }
    }

    @Override
    public Stream<ServerPlayerEntity> getTrackingPlayers(ChunkPos chunkPos, boolean bl) {
        return this.playerGenerationTracker.getGeneratingPlayers(chunkPos.asLong()).filter(arg_0 -> this.lambda$getTrackingPlayers$46(chunkPos, bl, arg_0));
    }

    protected void track(Entity entity2) {
        boolean bl = entity2 instanceof EnderDragonPartEntity;
        if (Reflector.PartEntity.exists()) {
            bl = Reflector.PartEntity.isInstance(entity2);
        }
        if (!bl) {
            EntityType<?> entityType = entity2.getType();
            int n = entityType.func_233602_m_() * 16;
            int n2 = entityType.getUpdateFrequency();
            if (this.entities.containsKey(entity2.getEntityId())) {
                throw Util.pauseDevMode(new IllegalStateException("Entity is already tracked!"));
            }
            EntityTracker entityTracker = new EntityTracker(this, entity2, n, n2, entityType.shouldSendVelocityUpdates());
            this.entities.put(entity2.getEntityId(), entityTracker);
            entityTracker.updateTrackingState(this.world.getPlayers());
            if (entity2 instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                this.setPlayerTracking(serverPlayerEntity, false);
                for (EntityTracker entityTracker2 : this.entities.values()) {
                    if (entityTracker2.entity == serverPlayerEntity) continue;
                    entityTracker2.updateTrackingState(serverPlayerEntity);
                }
            }
        }
    }

    protected void untrack(Entity entity2) {
        Object object;
        if (entity2 instanceof ServerPlayerEntity) {
            object = (ServerPlayerEntity)entity2;
            this.setPlayerTracking((ServerPlayerEntity)object, true);
            for (EntityTracker entityTracker : this.entities.values()) {
                entityTracker.removeTracker((ServerPlayerEntity)object);
            }
        }
        if ((object = (EntityTracker)this.entities.remove(entity2.getEntityId())) != null) {
            ((EntityTracker)object).removeAllTrackers();
        }
    }

    protected void tickEntityTracker() {
        ArrayList<ServerPlayerEntity> arrayList = Lists.newArrayList();
        List<ServerPlayerEntity> list = this.world.getPlayers();
        for (EntityTracker entityTracker : this.entities.values()) {
            SectionPos sectionPos = entityTracker.pos;
            SectionPos sectionPos2 = SectionPos.from(entityTracker.entity);
            if (!Objects.equals(sectionPos, sectionPos2)) {
                entityTracker.updateTrackingState(list);
                Entity entity2 = entityTracker.entity;
                if (entity2 instanceof ServerPlayerEntity) {
                    arrayList.add((ServerPlayerEntity)entity2);
                }
                entityTracker.pos = sectionPos2;
            }
            entityTracker.entry.tick();
        }
        if (!arrayList.isEmpty()) {
            for (EntityTracker entityTracker : this.entities.values()) {
                entityTracker.updateTrackingState(arrayList);
            }
        }
    }

    protected void sendToAllTracking(Entity entity2, IPacket<?> iPacket) {
        EntityTracker entityTracker = (EntityTracker)this.entities.get(entity2.getEntityId());
        if (entityTracker != null) {
            entityTracker.sendToAllTracking(iPacket);
        }
    }

    protected void sendToTrackingAndSelf(Entity entity2, IPacket<?> iPacket) {
        EntityTracker entityTracker = (EntityTracker)this.entities.get(entity2.getEntityId());
        if (entityTracker != null) {
            entityTracker.sendToTrackingAndSelf(iPacket);
        }
    }

    private void sendChunkData(ServerPlayerEntity serverPlayerEntity, IPacket<?>[] iPacketArray, Chunk chunk) {
        if (iPacketArray[0] == null) {
            iPacketArray[0] = new SChunkDataPacket(chunk, 65535);
            iPacketArray[1] = new SUpdateLightPacket(chunk.getPos(), this.lightManager, true);
        }
        serverPlayerEntity.sendChunkLoad(chunk.getPos(), iPacketArray[0], iPacketArray[5]);
        DebugPacketSender.sendChuckPos(this.world, chunk.getPos());
        ArrayList<Entity> arrayList = Lists.newArrayList();
        ArrayList<Entity> arrayList2 = Lists.newArrayList();
        for (EntityTracker object : this.entities.values()) {
            Entity entity2 = object.entity;
            if (entity2 == serverPlayerEntity || entity2.chunkCoordX != chunk.getPos().x || entity2.chunkCoordZ != chunk.getPos().z) continue;
            object.updateTrackingState(serverPlayerEntity);
            if (entity2 instanceof MobEntity && ((MobEntity)entity2).getLeashHolder() != null) {
                arrayList.add(entity2);
            }
            if (entity2.getPassengers().isEmpty()) continue;
            arrayList2.add(entity2);
        }
        if (!arrayList.isEmpty()) {
            for (Entity entity3 : arrayList) {
                serverPlayerEntity.connection.sendPacket(new SMountEntityPacket(entity3, ((MobEntity)entity3).getLeashHolder()));
            }
        }
        if (!arrayList2.isEmpty()) {
            for (Entity entity4 : arrayList2) {
                serverPlayerEntity.connection.sendPacket(new SSetPassengersPacket(entity4));
            }
        }
    }

    protected PointOfInterestManager getPointOfInterestManager() {
        return this.pointOfInterestManager;
    }

    public CompletableFuture<Void> func_222973_a(Chunk chunk) {
        return this.mainThread.runAsync(() -> this.lambda$func_222973_a$47(chunk));
    }

    private void lambda$func_222973_a$47(Chunk chunk) {
        chunk.saveScheduledTicks(this.world);
    }

    private boolean lambda$getTrackingPlayers$46(ChunkPos chunkPos, boolean bl, ServerPlayerEntity serverPlayerEntity) {
        int n = ChunkManager.func_219215_b(chunkPos, serverPlayerEntity, true);
        if (n > this.viewDistance) {
            return true;
        }
        return !bl || n == this.viewDistance;
    }

    private static boolean lambda$isOutsideSpawningRadius$45(ChunkPos chunkPos, ServerPlayerEntity serverPlayerEntity) {
        return !serverPlayerEntity.isSpectator() && ChunkManager.getDistanceSquaredToChunk(chunkPos, serverPlayerEntity) < 16384.0;
    }

    private static String lambda$func_225402_a$44(ChunkHolder.IChunkLoadingError iChunkLoadingError) {
        return "unloaded";
    }

    private static String lambda$func_225402_a$43(Chunk chunk) {
        return "done";
    }

    private static Integer lambda$func_225406_a$42(Chunk chunk) {
        return chunk.getTileEntityMap().size();
    }

    private static Integer lambda$func_225406_a$41(Chunk chunk) {
        return Stream.of(chunk.getEntityLists()).mapToInt(ClassInheritanceMultiMap::size).sum();
    }

    private static Optional lambda$func_225406_a$40(IChunk iChunk) {
        return iChunk instanceof Chunk ? Optional.of((Chunk)iChunk) : Optional.empty();
    }

    private void lambda$setViewDistance$39(ChunkPos chunkPos, int n, IPacket[] iPacketArray, ServerPlayerEntity serverPlayerEntity) {
        int n2 = ChunkManager.func_219215_b(chunkPos, serverPlayerEntity, true);
        boolean bl = n2 <= n;
        boolean bl2 = n2 <= this.viewDistance;
        this.setChunkLoadedAtClient(serverPlayerEntity, chunkPos, iPacketArray, bl, bl2);
    }

    private void lambda$func_222961_b$38(ChunkHolder chunkHolder, Runnable runnable) {
        this.field_219265_s.enqueue(ChunkTaskPriorityQueueSorter.func_219081_a(chunkHolder, runnable));
    }

    private static Either lambda$func_222961_b$37(Either either) {
        return either.mapLeft(ChunkManager::lambda$func_222961_b$36);
    }

    private static Chunk lambda$func_222961_b$36(IChunk iChunk) {
        Chunk chunk = (Chunk)iChunk;
        chunk.rescheduleTicks();
        return chunk;
    }

    private void lambda$func_219179_a$35(ChunkHolder chunkHolder, Runnable runnable) {
        this.field_219265_s.enqueue(ChunkTaskPriorityQueueSorter.func_219081_a(chunkHolder, runnable));
    }

    private void lambda$func_219179_a$34(ChunkPos chunkPos, Either either) {
        either.mapLeft(arg_0 -> this.lambda$func_219179_a$33(chunkPos, arg_0));
    }

    private Either lambda$func_219179_a$33(ChunkPos chunkPos, Chunk chunk) {
        this.field_219268_v.getAndIncrement();
        IPacket[] iPacketArray = new IPacket[2];
        this.getTrackingPlayers(chunkPos, true).forEach(arg_0 -> this.lambda$func_219179_a$32(iPacketArray, chunk, arg_0));
        return Either.left(chunk);
    }

    private void lambda$func_219179_a$32(IPacket[] iPacketArray, Chunk chunk, ServerPlayerEntity serverPlayerEntity) {
        this.sendChunkData(serverPlayerEntity, iPacketArray, chunk);
    }

    private void lambda$func_219179_a$31(ChunkHolder chunkHolder, Runnable runnable) {
        this.field_219265_s.enqueue(ChunkTaskPriorityQueueSorter.func_219081_a(chunkHolder, runnable));
    }

    private static Either lambda$func_219179_a$30(Either either) {
        return either.flatMap(ChunkManager::lambda$func_219179_a$29);
    }

    private static Either lambda$func_219179_a$29(List list) {
        Chunk chunk = (Chunk)list.get(list.size() / 2);
        chunk.postProcess();
        return Either.left(chunk);
    }

    private static ChunkStatus lambda$func_219179_a$28(int n) {
        return ChunkStatus.FULL;
    }

    private void lambda$func_219200_b$27(ChunkHolder chunkHolder, Runnable runnable) {
        this.field_219265_s.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(runnable, chunkHolder.getPosition().asLong(), chunkHolder::getChunkLevel));
    }

    private Either lambda$func_219200_b$26(ChunkHolder chunkHolder, Either either) {
        ChunkStatus chunkStatus = ChunkHolder.getChunkStatusFromLevel(chunkHolder.getChunkLevel());
        return !chunkStatus.isAtLeast(ChunkStatus.FULL) ? ChunkHolder.MISSING_CHUNK : either.mapLeft(arg_0 -> this.lambda$func_219200_b$25(chunkHolder, arg_0));
    }

    private IChunk lambda$func_219200_b$25(ChunkHolder chunkHolder, IChunk iChunk) {
        Chunk chunk;
        ChunkPos chunkPos = chunkHolder.getPosition();
        if (iChunk instanceof ChunkPrimerWrapper) {
            chunk = ((ChunkPrimerWrapper)iChunk).getChunk();
        } else {
            chunk = new Chunk(this.world, (ChunkPrimer)iChunk);
            chunkHolder.func_219294_a(new ChunkPrimerWrapper(chunk));
        }
        chunk.setLocationType(() -> ChunkManager.lambda$func_219200_b$24(chunkHolder));
        chunk.postLoad();
        if (this.loadedPositions.add(chunkPos.asLong())) {
            chunk.setLoaded(false);
            this.world.addTileEntities(chunk.getTileEntityMap().values());
            ArrayList<Entity> arrayList = null;
            ClassInheritanceMultiMap<Entity>[] classInheritanceMultiMapArray = chunk.getEntityLists();
            int n = classInheritanceMultiMapArray.length;
            for (int i = 0; i < n; ++i) {
                for (Entity entity2 : classInheritanceMultiMapArray[i]) {
                    if (entity2 instanceof PlayerEntity || this.world.addEntityIfNotDuplicate(entity2)) continue;
                    if (arrayList == null) {
                        arrayList = Lists.newArrayList(entity2);
                        continue;
                    }
                    arrayList.add(entity2);
                }
            }
            if (arrayList != null) {
                arrayList.forEach(chunk::removeEntity);
            }
            if (Reflector.ChunkEvent_Load_Constructor.exists()) {
                Reflector.postForgeBusEvent(Reflector.ChunkEvent_Load_Constructor, chunk);
            }
        }
        return chunk;
    }

    private static ChunkHolder.LocationType lambda$func_219200_b$24(ChunkHolder chunkHolder) {
        return ChunkHolder.getLocationTypeFromLevel(chunkHolder.getChunkLevel());
    }

    private static String lambda$func_219209_c$23(ChunkPos chunkPos) {
        return "release light ticket " + chunkPos;
    }

    private void lambda$func_219209_c$22(ChunkPos chunkPos) {
        this.ticketManager.releaseWithLevel(TicketType.LIGHT, chunkPos, 33 + ChunkStatus.getDistance(ChunkStatus.FEATURES), chunkPos);
    }

    private void lambda$chunkGenerate$21(ChunkHolder chunkHolder, Runnable runnable) {
        this.field_219264_r.enqueue(ChunkTaskPriorityQueueSorter.func_219081_a(chunkHolder, runnable));
    }

    private CompletionStage lambda$chunkGenerate$20(ChunkStatus chunkStatus, ChunkHolder chunkHolder, ChunkPos chunkPos, Either either) {
        return either.map(arg_0 -> this.lambda$chunkGenerate$18(chunkStatus, chunkHolder, chunkPos, arg_0), arg_0 -> this.lambda$chunkGenerate$19(chunkPos, arg_0));
    }

    private CompletableFuture lambda$chunkGenerate$19(ChunkPos chunkPos, ChunkHolder.IChunkLoadingError iChunkLoadingError) {
        this.func_219209_c(chunkPos);
        return CompletableFuture.completedFuture(Either.right(iChunkLoadingError));
    }

    private CompletableFuture lambda$chunkGenerate$18(ChunkStatus chunkStatus, ChunkHolder chunkHolder, ChunkPos chunkPos, List list) {
        try {
            CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkStatus.doGenerationWork(this.world, this.generator, this.templateManager, this.lightManager, arg_0 -> this.lambda$chunkGenerate$17(chunkHolder, arg_0), list);
            this.field_219266_t.statusChanged(chunkPos, chunkStatus);
            return completableFuture;
        } catch (Exception exception) {
            CrashReport crashReport = CrashReport.makeCrashReport(exception, "Exception generating new chunk");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Chunk to be generated");
            crashReportCategory.addDetail("Location", String.format("%d,%d", chunkPos.x, chunkPos.z));
            crashReportCategory.addDetail("Position hash", ChunkPos.asLong(chunkPos.x, chunkPos.z));
            crashReportCategory.addDetail("Generator", this.generator);
            throw new ReportedException(crashReport);
        }
    }

    private CompletableFuture lambda$chunkGenerate$17(ChunkHolder chunkHolder, IChunk iChunk) {
        return this.func_219200_b(chunkHolder);
    }

    private static String lambda$chunkGenerate$16(ChunkStatus chunkStatus) {
        return "chunkGenerate " + chunkStatus.getName();
    }

    private ChunkStatus lambda$chunkGenerate$15(ChunkStatus chunkStatus, int n) {
        return this.func_219205_a(chunkStatus, n);
    }

    private Either lambda$chunkLoad$14(ChunkPos chunkPos) {
        try {
            this.world.getProfiler().func_230035_c_("chunkLoad");
            CompoundNBT compoundNBT = this.loadChunkData(chunkPos);
            if (compoundNBT != null) {
                boolean bl;
                boolean bl2 = bl = compoundNBT.contains("Level", 1) && compoundNBT.getCompound("Level").contains("Status", 1);
                if (bl) {
                    ChunkPrimer chunkPrimer = ChunkSerializer.read(this.world, this.templateManager, this.pointOfInterestManager, chunkPos, compoundNBT);
                    chunkPrimer.setLastSaveTime(this.world.getGameTime());
                    this.func_241088_a_(chunkPos, chunkPrimer.getStatus().getType());
                    return Either.left(chunkPrimer);
                }
                LOGGER.error("Chunk file at {} is missing level data, skipping", (Object)chunkPos);
            }
        } catch (ReportedException reportedException) {
            Throwable throwable = reportedException.getCause();
            if (!(throwable instanceof IOException)) {
                this.func_241089_g_(chunkPos);
                throw reportedException;
            }
            LOGGER.error("Couldn't load chunk {}", (Object)chunkPos, (Object)throwable);
        } catch (Exception exception) {
            LOGGER.error("Couldn't load chunk {}", (Object)chunkPos, (Object)exception);
        }
        this.func_241089_g_(chunkPos);
        return Either.left(new ChunkPrimer(chunkPos, UpgradeData.EMPTY));
    }

    private CompletionStage lambda$func_219244_a$13(ChunkStatus chunkStatus, ChunkPos chunkPos, ChunkHolder chunkHolder, Either either) {
        IChunk iChunk;
        Optional optional = either.left();
        if (!optional.isPresent()) {
            return CompletableFuture.completedFuture(either);
        }
        if (chunkStatus == ChunkStatus.LIGHT) {
            this.ticketManager.registerWithLevel(TicketType.LIGHT, chunkPos, 33 + ChunkStatus.getDistance(ChunkStatus.FEATURES), chunkPos);
        }
        if ((iChunk = (IChunk)optional.get()).getStatus().isAtLeast(chunkStatus)) {
            CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completableFuture = chunkStatus == ChunkStatus.LIGHT ? this.chunkGenerate(chunkHolder, chunkStatus) : chunkStatus.doLoadingWork(this.world, this.templateManager, this.lightManager, arg_0 -> this.lambda$func_219244_a$12(chunkHolder, arg_0), iChunk);
            this.field_219266_t.statusChanged(chunkPos, chunkStatus);
            return completableFuture;
        }
        return this.chunkGenerate(chunkHolder, chunkStatus);
    }

    private CompletableFuture lambda$func_219244_a$12(ChunkHolder chunkHolder, IChunk iChunk) {
        return this.func_219200_b(chunkHolder);
    }

    private static void lambda$scheduleSave$11(ChunkHolder chunkHolder, Void void_, Throwable throwable) {
        if (throwable != null) {
            LOGGER.error("Failed to save chunk " + chunkHolder.getPosition(), throwable);
        }
    }

    private void lambda$scheduleSave$10(ChunkHolder chunkHolder, CompletableFuture completableFuture, long l, IChunk iChunk) {
        CompletableFuture<IChunk> completableFuture2 = chunkHolder.func_219302_f();
        if (completableFuture2 != completableFuture) {
            this.scheduleSave(l, chunkHolder);
        } else if (this.chunksToUnload.remove(l, (Object)chunkHolder) && iChunk != null) {
            if (iChunk instanceof Chunk) {
                ((Chunk)iChunk).setLoaded(true);
                if (Reflector.ChunkEvent_Unload_Constructor.exists()) {
                    Reflector.postForgeBusEvent(Reflector.ChunkEvent_Unload_Constructor, iChunk);
                }
            }
            this.chunkSave(iChunk);
            if (this.loadedPositions.remove(l) && iChunk instanceof Chunk) {
                Chunk chunk = (Chunk)iChunk;
                this.world.onChunkUnloading(chunk);
            }
            this.lightManager.updateChunkStatus(iChunk.getPos());
            this.lightManager.func_215588_z_();
            this.field_219266_t.statusChanged(iChunk.getPos(), null);
        }
    }

    private void lambda$save$9(ChunkHolder chunkHolder) {
        IChunk iChunk = chunkHolder.func_219302_f().getNow(null);
        if (iChunk instanceof ChunkPrimerWrapper || iChunk instanceof Chunk) {
            this.chunkSave(iChunk);
            chunkHolder.updateAccessible();
        }
    }

    private static boolean lambda$save$8() {
        return false;
    }

    private static void lambda$save$7(MutableBoolean mutableBoolean, IChunk iChunk) {
        mutableBoolean.setTrue();
    }

    private static boolean lambda$save$6(IChunk iChunk) {
        return iChunk instanceof ChunkPrimerWrapper || iChunk instanceof Chunk;
    }

    private IChunk lambda$save$5(ChunkHolder chunkHolder) {
        CompletableFuture<IChunk> completableFuture;
        do {
            completableFuture = chunkHolder.func_219302_f();
            this.mainThread.driveUntil(completableFuture::isDone);
        } while (completableFuture != chunkHolder.func_219302_f());
        return completableFuture.join();
    }

    private static Either lambda$func_219188_b$4(Either either) {
        return either.mapLeft(ChunkManager::lambda$func_219188_b$3);
    }

    private static Chunk lambda$func_219188_b$3(List list) {
        return (Chunk)list.get(list.size() / 2);
    }

    private static ChunkStatus lambda$func_219188_b$2(int n) {
        return ChunkStatus.FULL;
    }

    private Either lambda$func_219236_a$1(int n, int n2, int n3, List list) {
        ArrayList<IChunk> arrayList = Lists.newArrayList();
        int n4 = 0;
        for (Either either : list) {
            Optional optional = either.left();
            if (!optional.isPresent()) {
                int n5 = n4;
                return Either.right(new ChunkHolder.IChunkLoadingError(){
                    final int val$i;
                    final int val$l1;
                    final int val$p_219236_2_;
                    final int val$j;
                    final Either val$either;
                    final ChunkManager this$0;
                    {
                        this.this$0 = chunkManager;
                        this.val$i = n;
                        this.val$l1 = n2;
                        this.val$p_219236_2_ = n3;
                        this.val$j = n4;
                        this.val$either = either;
                    }

                    public String toString() {
                        return "Unloaded " + new ChunkPos(this.val$i + this.val$l1 % (this.val$p_219236_2_ * 2 + 1), this.val$j + this.val$l1 / (this.val$p_219236_2_ * 2 + 1)) + " " + ((ChunkHolder.IChunkLoadingError)this.val$either.right().get()).toString();
                    }
                });
            }
            arrayList.add((IChunk)optional.get());
            ++n4;
        }
        return Either.left(arrayList);
    }

    private int lambda$func_219191_c$0(long l) {
        ChunkHolder chunkHolder = this.func_219219_b(l);
        return chunkHolder == null ? ChunkTaskPriorityQueue.MAX_LOADED_LEVELS - 1 : Math.min(chunkHolder.func_219281_j(), ChunkTaskPriorityQueue.MAX_LOADED_LEVELS - 1);
    }

    class ProxyTicketManager
    extends TicketManager {
        final ChunkManager this$0;

        protected ProxyTicketManager(ChunkManager chunkManager, Executor executor, Executor executor2) {
            this.this$0 = chunkManager;
            super(executor, executor2);
        }

        @Override
        protected boolean contains(long l) {
            return this.this$0.unloadableChunks.contains(l);
        }

        @Override
        @Nullable
        protected ChunkHolder getChunkHolder(long l) {
            return this.this$0.func_219220_a(l);
        }

        @Override
        @Nullable
        protected ChunkHolder setChunkLevel(long l, int n, @Nullable ChunkHolder chunkHolder, int n2) {
            return this.this$0.setChunkLevel(l, n, chunkHolder, n2);
        }
    }

    class EntityTracker {
        private final TrackedEntity entry;
        private final Entity entity;
        private final int range;
        private SectionPos pos;
        private final Set<ServerPlayerEntity> trackingPlayers;
        final ChunkManager this$0;

        public EntityTracker(ChunkManager chunkManager, Entity entity2, int n, int n2, boolean bl) {
            this.this$0 = chunkManager;
            this.trackingPlayers = Sets.newHashSet();
            this.entry = new TrackedEntity(chunkManager.world, entity2, n2, bl, this::sendToAllTracking);
            this.entity = entity2;
            this.range = n;
            this.pos = SectionPos.from(entity2);
        }

        public boolean equals(Object object) {
            if (object instanceof EntityTracker) {
                return ((EntityTracker)object).entity.getEntityId() == this.entity.getEntityId();
            }
            return true;
        }

        public int hashCode() {
            return this.entity.getEntityId();
        }

        public void sendToAllTracking(IPacket<?> iPacket) {
            for (ServerPlayerEntity serverPlayerEntity : this.trackingPlayers) {
                serverPlayerEntity.connection.sendPacket(iPacket);
            }
        }

        public void sendToTrackingAndSelf(IPacket<?> iPacket) {
            this.sendToAllTracking(iPacket);
            if (this.entity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity)this.entity).connection.sendPacket(iPacket);
            }
        }

        public void removeAllTrackers() {
            for (ServerPlayerEntity serverPlayerEntity : this.trackingPlayers) {
                this.entry.untrack(serverPlayerEntity);
            }
        }

        public void removeTracker(ServerPlayerEntity serverPlayerEntity) {
            if (this.trackingPlayers.remove(serverPlayerEntity)) {
                this.entry.untrack(serverPlayerEntity);
            }
        }

        public void updateTrackingState(ServerPlayerEntity serverPlayerEntity) {
            if (serverPlayerEntity != this.entity) {
                boolean bl;
                Vector3d vector3d = serverPlayerEntity.getPositionVec().subtract(this.entry.getDecodedPosition());
                int n = Math.min(this.func_229843_b_(), (this.this$0.viewDistance - 1) * 16);
                boolean bl2 = bl = vector3d.x >= (double)(-n) && vector3d.x <= (double)n && vector3d.z >= (double)(-n) && vector3d.z <= (double)n && this.entity.isSpectatedByPlayer(serverPlayerEntity);
                if (bl) {
                    ChunkPos chunkPos;
                    ChunkHolder chunkHolder;
                    boolean bl3 = this.entity.forceSpawn;
                    if (!bl3 && (chunkHolder = this.this$0.func_219219_b((chunkPos = new ChunkPos(this.entity.chunkCoordX, this.entity.chunkCoordZ)).asLong())) != null && chunkHolder.getChunkIfComplete() != null) {
                        boolean bl4 = bl3 = ChunkManager.func_219215_b(chunkPos, serverPlayerEntity, false) <= this.this$0.viewDistance;
                    }
                    if (bl3 && this.trackingPlayers.add(serverPlayerEntity)) {
                        this.entry.track(serverPlayerEntity);
                    }
                } else if (this.trackingPlayers.remove(serverPlayerEntity)) {
                    this.entry.untrack(serverPlayerEntity);
                }
            }
        }

        private int func_241091_a_(int n) {
            return this.this$0.world.getServer().func_230512_b_(n);
        }

        private int func_229843_b_() {
            Collection<Entity> collection = this.entity.getRecursivePassengers();
            int n = this.range;
            for (Entity entity2 : collection) {
                int n2 = entity2.getType().func_233602_m_() * 16;
                if (n2 <= n) continue;
                n = n2;
            }
            return this.func_241091_a_(n);
        }

        public void updateTrackingState(List<ServerPlayerEntity> list) {
            for (ServerPlayerEntity serverPlayerEntity : list) {
                this.updateTrackingState(serverPlayerEntity);
            }
        }
    }
}

