/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderCache;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraftforge.client.extensions.IForgeRenderChunk;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomBlockLayers;
import net.optifine.override.ChunkCacheOF;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.render.AabbFrame;
import net.optifine.render.ChunkLayerMap;
import net.optifine.render.ChunkLayerSet;
import net.optifine.render.ICamera;
import net.optifine.render.RenderEnv;
import net.optifine.render.RenderTypes;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;
import net.optifine.util.ChunkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderDispatcher {
    private static final Logger LOGGER = LogManager.getLogger();
    private final PriorityQueue<ChunkRender.ChunkRenderTask> renderTasks = Queues.newPriorityQueue();
    private final Queue<RegionRenderCacheBuilder> freeBuilders;
    private final Queue<Runnable> uploadTasks = Queues.newConcurrentLinkedQueue();
    private volatile int countRenderTasks;
    private volatile int countFreeBuilders;
    private final RegionRenderCacheBuilder fixedBuilder;
    private final DelegatedTaskExecutor<Runnable> delegatedTaskExecutor;
    private final Executor executor;
    private World world;
    private final WorldRenderer worldRenderer;
    private Vector3d renderPosition = Vector3d.ZERO;
    private int countRenderBuilders;
    private List<RegionRenderCacheBuilder> listPausedBuilders = new ArrayList<RegionRenderCacheBuilder>();
    public static final RenderType[] BLOCK_RENDER_LAYERS = RenderType.getBlockRenderTypes().toArray(new RenderType[0]);
    private static final boolean FORGE = Reflector.ForgeHooksClient.exists();
    private static final boolean FORGE_CAN_RENDER_IN_LAYER_BS = Reflector.ForgeRenderTypeLookup_canRenderInLayerBs.exists();
    private static final boolean FORGE_CAN_RENDER_IN_LAYER_FS = Reflector.ForgeRenderTypeLookup_canRenderInLayerBs.exists();
    private static final boolean FORGE_SET_RENDER_LAYER = Reflector.ForgeHooksClient_setRenderLayer.exists();
    public static int renderChunksUpdated;

    public ChunkRenderDispatcher(World world, WorldRenderer worldRenderer, Executor executor, boolean bl, RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this(world, worldRenderer, executor, bl, regionRenderCacheBuilder, -1);
    }

    public ChunkRenderDispatcher(World world, WorldRenderer worldRenderer, Executor executor, boolean bl, RegionRenderCacheBuilder regionRenderCacheBuilder, int n) {
        this.world = world;
        this.worldRenderer = worldRenderer;
        int n2 = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (RenderType.getBlockRenderTypes().stream().mapToInt(RenderType::getBufferSize).sum() * 4) - 1);
        int n3 = Runtime.getRuntime().availableProcessors();
        int n4 = bl ? n3 : Math.min(n3, 4);
        int n5 = Math.max(1, Math.min(n4, n2));
        if (n > 0) {
            n5 = n;
        }
        this.fixedBuilder = regionRenderCacheBuilder;
        ArrayList<RegionRenderCacheBuilder> arrayList = Lists.newArrayListWithExpectedSize(n5);
        try {
            for (int i = 0; i < n5; ++i) {
                arrayList.add(new RegionRenderCacheBuilder());
            }
        } catch (OutOfMemoryError outOfMemoryError) {
            LOGGER.warn("Allocated only {}/{} buffers", (Object)arrayList.size(), (Object)n5);
            int n6 = Math.min(arrayList.size() * 2 / 3, arrayList.size() - 1);
            for (int i = 0; i < n6; ++i) {
                arrayList.remove(arrayList.size() - 1);
            }
            System.gc();
        }
        this.freeBuilders = Queues.newConcurrentLinkedQueue(arrayList);
        this.countRenderBuilders = this.countFreeBuilders = this.freeBuilders.size();
        this.executor = executor;
        this.delegatedTaskExecutor = DelegatedTaskExecutor.create(executor, "Chunk Renderer");
        this.delegatedTaskExecutor.enqueue(this::runTask);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private void runTask() {
        ChunkRender.ChunkRenderTask chunkRenderTask;
        if (!this.freeBuilders.isEmpty() && (chunkRenderTask = this.renderTasks.poll()) != null) {
            RegionRenderCacheBuilder regionRenderCacheBuilder = this.freeBuilders.poll();
            if (regionRenderCacheBuilder == null) {
                this.renderTasks.add(chunkRenderTask);
                return;
            }
            this.countRenderTasks = this.renderTasks.size();
            this.countFreeBuilders = this.freeBuilders.size();
            ((CompletableFuture)CompletableFuture.runAsync(ChunkRenderDispatcher::lambda$runTask$0, this.executor).thenCompose(arg_0 -> ChunkRenderDispatcher.lambda$runTask$1(chunkRenderTask, regionRenderCacheBuilder, arg_0))).whenComplete((arg_0, arg_1) -> this.lambda$runTask$3(regionRenderCacheBuilder, arg_0, arg_1));
        }
    }

    public String getDebugInfo() {
        return String.format("pC: %03d, pU: %02d, aB: %02d", this.countRenderTasks, this.uploadTasks.size(), this.countFreeBuilders);
    }

    public void setRenderPosition(Vector3d vector3d) {
        this.renderPosition = vector3d;
    }

    public Vector3d getRenderPosition() {
        return this.renderPosition;
    }

    public boolean runChunkUploads() {
        Runnable runnable;
        boolean bl = false;
        while ((runnable = this.uploadTasks.poll()) != null) {
            runnable.run();
            bl = true;
        }
        return bl;
    }

    public void rebuildChunk(ChunkRender chunkRender) {
        chunkRender.rebuildChunk();
    }

    public void stopChunkUpdates() {
        this.clearChunkUpdates();
    }

    public void schedule(ChunkRender.ChunkRenderTask chunkRenderTask) {
        this.delegatedTaskExecutor.enqueue(() -> this.lambda$schedule$4(chunkRenderTask));
    }

    public CompletableFuture<Void> uploadChunkLayer(BufferBuilder bufferBuilder, VertexBuffer vertexBuffer) {
        return CompletableFuture.runAsync(ChunkRenderDispatcher::lambda$uploadChunkLayer$5, this.uploadTasks::add).thenCompose(arg_0 -> this.lambda$uploadChunkLayer$6(bufferBuilder, vertexBuffer, arg_0));
    }

    private CompletableFuture<Void> uploadChunkLayerRaw(BufferBuilder bufferBuilder, VertexBuffer vertexBuffer) {
        return vertexBuffer.uploadLater(bufferBuilder);
    }

    private void clearChunkUpdates() {
        while (!this.renderTasks.isEmpty()) {
            ChunkRender.ChunkRenderTask chunkRenderTask = this.renderTasks.poll();
            if (chunkRenderTask == null) continue;
            chunkRenderTask.cancel();
        }
        this.countRenderTasks = 0;
    }

    public boolean hasNoChunkUpdates() {
        return this.countRenderTasks == 0 && this.uploadTasks.isEmpty();
    }

    public void stopWorkerThreads() {
        this.clearChunkUpdates();
        this.delegatedTaskExecutor.close();
        this.freeBuilders.clear();
    }

    public void pauseChunkUpdates() {
        long l = System.currentTimeMillis();
        if (this.listPausedBuilders.size() <= 0) {
            while (this.listPausedBuilders.size() != this.countRenderBuilders) {
                this.runChunkUploads();
                RegionRenderCacheBuilder regionRenderCacheBuilder = this.freeBuilders.poll();
                if (regionRenderCacheBuilder != null) {
                    this.listPausedBuilders.add(regionRenderCacheBuilder);
                }
                if (System.currentTimeMillis() <= l + 1000L) continue;
                break;
            }
        }
    }

    public void resumeChunkUpdates() {
        this.freeBuilders.addAll(this.listPausedBuilders);
        this.listPausedBuilders.clear();
    }

    public boolean updateChunkNow(ChunkRender chunkRender) {
        this.rebuildChunk(chunkRender);
        return false;
    }

    public boolean updateChunkLater(ChunkRender chunkRender) {
        if (this.freeBuilders.isEmpty()) {
            return true;
        }
        chunkRender.rebuildChunkLater(this);
        return false;
    }

    public boolean updateTransparencyLater(ChunkRender chunkRender) {
        return this.freeBuilders.isEmpty() ? false : chunkRender.resortTransparency(RenderTypes.TRANSLUCENT, this);
    }

    private CompletionStage lambda$uploadChunkLayer$6(BufferBuilder bufferBuilder, VertexBuffer vertexBuffer, Void void_) {
        return this.uploadChunkLayerRaw(bufferBuilder, vertexBuffer);
    }

    private static void lambda$uploadChunkLayer$5() {
    }

    private void lambda$schedule$4(ChunkRender.ChunkRenderTask chunkRenderTask) {
        this.renderTasks.offer(chunkRenderTask);
        this.countRenderTasks = this.renderTasks.size();
        this.runTask();
    }

    private void lambda$runTask$3(RegionRenderCacheBuilder regionRenderCacheBuilder, ChunkTaskResult chunkTaskResult, Throwable throwable) {
        if (throwable != null) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Batching chunks");
            Minecraft.getInstance().crashed(Minecraft.getInstance().addGraphicsAndWorldToCrashReport(crashReport));
        } else {
            this.delegatedTaskExecutor.enqueue(() -> this.lambda$runTask$2(chunkTaskResult, regionRenderCacheBuilder));
        }
    }

    private void lambda$runTask$2(ChunkTaskResult chunkTaskResult, RegionRenderCacheBuilder regionRenderCacheBuilder) {
        if (chunkTaskResult == ChunkTaskResult.SUCCESSFUL) {
            regionRenderCacheBuilder.resetBuilders();
        } else {
            regionRenderCacheBuilder.discardBuilders();
        }
        this.freeBuilders.add(regionRenderCacheBuilder);
        this.countFreeBuilders = this.freeBuilders.size();
        this.runTask();
    }

    private static CompletionStage lambda$runTask$1(ChunkRender.ChunkRenderTask chunkRenderTask, RegionRenderCacheBuilder regionRenderCacheBuilder, Void void_) {
        return chunkRenderTask.execute(regionRenderCacheBuilder);
    }

    private static void lambda$runTask$0() {
    }

    public class ChunkRender
    implements IForgeRenderChunk {
        public final AtomicReference<CompiledChunk> compiledChunk;
        @Nullable
        private RebuildTask lastRebuildTask;
        @Nullable
        private SortTransparencyTask lastResortTransparencyTask;
        private final Set<TileEntity> globalTileEntities;
        private final ChunkLayerMap<VertexBuffer> vertexBuffers;
        public AxisAlignedBB boundingBox;
        private int frameIndex;
        private boolean needsUpdate;
        private final BlockPos.Mutable position;
        private final BlockPos.Mutable[] mapEnumFacing;
        private boolean needsImmediateUpdate;
        private final boolean isMipmaps;
        private final boolean fixBlockLayer;
        private boolean playerUpdate;
        private boolean renderRegions;
        public int regionX;
        public int regionZ;
        private int regionDX;
        private int regionDY;
        private int regionDZ;
        private final ChunkRender[] renderChunksOfset16;
        private boolean renderChunksOffset16Updated;
        private Chunk chunk;
        private ChunkRender[] renderChunkNeighbours;
        private ChunkRender[] renderChunkNeighboursValid;
        private boolean renderChunkNeighboursUpated;
        private WorldRenderer.LocalRenderInformationContainer renderInfo;
        public AabbFrame boundingBoxParent;
        final ChunkRenderDispatcher this$0;

        public ChunkRender(ChunkRenderDispatcher chunkRenderDispatcher) {
            this.this$0 = chunkRenderDispatcher;
            this.compiledChunk = new AtomicReference<CompiledChunk>(CompiledChunk.DUMMY);
            this.globalTileEntities = Sets.newHashSet();
            this.vertexBuffers = new ChunkLayerMap<VertexBuffer>(ChunkRender::lambda$new$0);
            this.frameIndex = -1;
            this.needsUpdate = true;
            this.position = new BlockPos.Mutable(-1, -1, -1);
            this.mapEnumFacing = Util.make(new BlockPos.Mutable[6], ChunkRender::lambda$new$1);
            this.isMipmaps = Config.isMipmaps();
            this.fixBlockLayer = !Reflector.BetterFoliageClient.exists();
            this.playerUpdate = false;
            this.renderRegions = Config.isRenderRegions();
            this.renderChunksOfset16 = new ChunkRender[6];
            this.renderChunksOffset16Updated = false;
            this.renderChunkNeighbours = new ChunkRender[Direction.VALUES.length];
            this.renderChunkNeighboursValid = new ChunkRender[Direction.VALUES.length];
            this.renderChunkNeighboursUpated = false;
            this.renderInfo = new WorldRenderer.LocalRenderInformationContainer(this, null, 0);
        }

        private boolean isChunkLoaded(BlockPos blockPos) {
            return this.this$0.world.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.FULL, true) != null;
        }

        public boolean shouldStayLoaded() {
            int n = 24;
            if (!(this.getDistanceSq() > 576.0)) {
                return false;
            }
            return this.isChunkLoaded(this.mapEnumFacing[Direction.WEST.ordinal()]) && this.isChunkLoaded(this.mapEnumFacing[Direction.NORTH.ordinal()]) && this.isChunkLoaded(this.mapEnumFacing[Direction.EAST.ordinal()]) && this.isChunkLoaded(this.mapEnumFacing[Direction.SOUTH.ordinal()]);
        }

        public boolean setFrameIndex(int n) {
            if (this.frameIndex == n) {
                return true;
            }
            this.frameIndex = n;
            return false;
        }

        public VertexBuffer getVertexBuffer(RenderType renderType) {
            return this.vertexBuffers.get(renderType);
        }

        public void setPosition(int n, int n2, int n3) {
            if (n != this.position.getX() || n2 != this.position.getY() || n3 != this.position.getZ()) {
                this.stopCompileTask();
                this.position.setPos(n, n2, n3);
                if (this.renderRegions) {
                    int n4 = 8;
                    this.regionX = n >> n4 << n4;
                    this.regionZ = n3 >> n4 << n4;
                    this.regionDX = n - this.regionX;
                    this.regionDY = n2;
                    this.regionDZ = n3 - this.regionZ;
                }
                this.boundingBox = new AxisAlignedBB(n, n2, n3, n + 16, n2 + 16, n3 + 16);
                for (Direction direction : Direction.VALUES) {
                    this.mapEnumFacing[direction.ordinal()].setPos(this.position).move(direction, 16);
                }
                this.renderChunksOffset16Updated = false;
                this.renderChunkNeighboursUpated = false;
                for (int i = 0; i < this.renderChunkNeighbours.length; ++i) {
                    ChunkRender chunkRender = this.renderChunkNeighbours[i];
                    if (chunkRender == null) continue;
                    chunkRender.renderChunkNeighboursUpated = false;
                }
                this.chunk = null;
                this.boundingBoxParent = null;
            }
        }

        protected double getDistanceSq() {
            ActiveRenderInfo activeRenderInfo = Minecraft.getInstance().gameRenderer.getActiveRenderInfo();
            double d = this.boundingBox.minX + 8.0 - activeRenderInfo.getProjectedView().x;
            double d2 = this.boundingBox.minY + 8.0 - activeRenderInfo.getProjectedView().y;
            double d3 = this.boundingBox.minZ + 8.0 - activeRenderInfo.getProjectedView().z;
            return d * d + d2 * d2 + d3 * d3;
        }

        private void beginLayer(BufferBuilder bufferBuilder) {
            bufferBuilder.begin(7, DefaultVertexFormats.BLOCK);
        }

        public CompiledChunk getCompiledChunk() {
            return this.compiledChunk.get();
        }

        private void stopCompileTask() {
            this.stopTasks();
            this.compiledChunk.set(CompiledChunk.DUMMY);
            this.needsUpdate = true;
        }

        public void deleteGlResources() {
            this.stopCompileTask();
            this.vertexBuffers.values().forEach(VertexBuffer::close);
        }

        public BlockPos getPosition() {
            return this.position;
        }

        public void setNeedsUpdate(boolean bl) {
            boolean bl2 = this.needsUpdate;
            this.needsUpdate = true;
            this.needsImmediateUpdate = bl | (bl2 && this.needsImmediateUpdate);
            if (this.isWorldPlayerUpdate()) {
                this.playerUpdate = true;
            }
        }

        public void clearNeedsUpdate() {
            this.needsUpdate = false;
            this.needsImmediateUpdate = false;
            this.playerUpdate = false;
        }

        public boolean needsUpdate() {
            return this.needsUpdate;
        }

        public boolean needsImmediateUpdate() {
            return this.needsUpdate && this.needsImmediateUpdate;
        }

        public BlockPos getBlockPosOffset16(Direction direction) {
            return this.mapEnumFacing[direction.ordinal()];
        }

        public boolean resortTransparency(RenderType renderType, ChunkRenderDispatcher chunkRenderDispatcher) {
            CompiledChunk compiledChunk = this.getCompiledChunk();
            if (this.lastResortTransparencyTask != null) {
                this.lastResortTransparencyTask.cancel();
            }
            if (!compiledChunk.layersStarted.contains(renderType)) {
                return true;
            }
            this.lastResortTransparencyTask = FORGE ? new SortTransparencyTask(this, new ChunkPos(this.getPosition()), this.getDistanceSq(), compiledChunk) : new SortTransparencyTask(this, this.getDistanceSq(), compiledChunk);
            chunkRenderDispatcher.schedule(this.lastResortTransparencyTask);
            return false;
        }

        protected void stopTasks() {
            if (this.lastRebuildTask != null) {
                this.lastRebuildTask.cancel();
                this.lastRebuildTask = null;
            }
            if (this.lastResortTransparencyTask != null) {
                this.lastResortTransparencyTask.cancel();
                this.lastResortTransparencyTask = null;
            }
        }

        public ChunkRenderTask makeCompileTaskChunk() {
            this.stopTasks();
            BlockPos blockPos = this.position.toImmutable();
            boolean bl = true;
            ChunkRenderCache chunkRenderCache = null;
            this.lastRebuildTask = FORGE ? new RebuildTask(this, new ChunkPos(this.getPosition()), this.getDistanceSq(), chunkRenderCache) : new RebuildTask(this, this.getDistanceSq(), chunkRenderCache);
            return this.lastRebuildTask;
        }

        public void rebuildChunkLater(ChunkRenderDispatcher chunkRenderDispatcher) {
            ChunkRenderTask chunkRenderTask = this.makeCompileTaskChunk();
            chunkRenderDispatcher.schedule(chunkRenderTask);
        }

        private void updateGlobalTileEntities(Set<TileEntity> set) {
            HashSet<TileEntity> hashSet = Sets.newHashSet(set);
            HashSet<TileEntity> hashSet2 = Sets.newHashSet(this.globalTileEntities);
            hashSet.removeAll(this.globalTileEntities);
            hashSet2.removeAll(set);
            this.globalTileEntities.clear();
            this.globalTileEntities.addAll(set);
            this.this$0.worldRenderer.updateTileEntities(hashSet2, hashSet);
        }

        public void rebuildChunk() {
            ChunkRenderTask chunkRenderTask = this.makeCompileTaskChunk();
            chunkRenderTask.execute(this.this$0.fixedBuilder);
        }

        private boolean isWorldPlayerUpdate() {
            if (this.this$0.world instanceof ClientWorld) {
                ClientWorld clientWorld = (ClientWorld)this.this$0.world;
                return clientWorld.isPlayerUpdate();
            }
            return true;
        }

        public boolean isPlayerUpdate() {
            return this.playerUpdate;
        }

        private RenderType[] getFluidRenderLayers(FluidState fluidState, RenderType[] renderTypeArray) {
            if (FORGE_CAN_RENDER_IN_LAYER_FS) {
                return BLOCK_RENDER_LAYERS;
            }
            renderTypeArray[0] = RenderTypeLookup.getRenderType(fluidState);
            return renderTypeArray;
        }

        private RenderType[] getBlockRenderLayers(BlockState blockState, RenderType[] renderTypeArray) {
            if (FORGE_CAN_RENDER_IN_LAYER_BS) {
                return BLOCK_RENDER_LAYERS;
            }
            renderTypeArray[0] = RenderTypeLookup.getChunkRenderType(blockState);
            return renderTypeArray;
        }

        private RenderType fixBlockLayer(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, RenderType renderType) {
            Object object;
            if (CustomBlockLayers.isActive() && (object = CustomBlockLayers.getRenderLayer(iBlockReader, blockState, blockPos)) != null) {
                return object;
            }
            if (!this.fixBlockLayer) {
                return renderType;
            }
            if (this.isMipmaps) {
                if (renderType == RenderTypes.CUTOUT) {
                    object = blockState.getBlock();
                    if (object instanceof RedstoneWireBlock) {
                        return renderType;
                    }
                    if (object instanceof CactusBlock) {
                        return renderType;
                    }
                    return RenderTypes.CUTOUT_MIPPED;
                }
            } else if (renderType == RenderTypes.CUTOUT_MIPPED) {
                return RenderTypes.CUTOUT;
            }
            return renderType;
        }

        private void postRenderOverlays(RegionRenderCacheBuilder regionRenderCacheBuilder, CompiledChunk compiledChunk) {
            this.postRenderOverlay(RenderTypes.CUTOUT, regionRenderCacheBuilder, compiledChunk);
            this.postRenderOverlay(RenderTypes.CUTOUT_MIPPED, regionRenderCacheBuilder, compiledChunk);
            this.postRenderOverlay(RenderTypes.TRANSLUCENT, regionRenderCacheBuilder, compiledChunk);
        }

        private void postRenderOverlay(RenderType renderType, RegionRenderCacheBuilder regionRenderCacheBuilder, CompiledChunk compiledChunk) {
            BufferBuilder bufferBuilder = regionRenderCacheBuilder.getBuilder(renderType);
            if (bufferBuilder.isDrawing()) {
                compiledChunk.setLayerStarted(renderType);
                if (bufferBuilder.getVertexCount() > 0) {
                    compiledChunk.setLayerUsed(renderType);
                }
            }
        }

        private ChunkCacheOF makeChunkCacheOF(BlockPos blockPos) {
            BlockPos blockPos2 = blockPos.add(-1, -1, -1);
            BlockPos blockPos3 = blockPos.add(16, 16, 16);
            ChunkRenderCache chunkRenderCache = this.createRegionRenderCache(this.this$0.world, blockPos2, blockPos3, 1);
            return new ChunkCacheOF(chunkRenderCache, blockPos2, blockPos3, 1);
        }

        @Override
        public ChunkRenderCache createRegionRenderCache(World world, BlockPos blockPos, BlockPos blockPos2, int n) {
            return ChunkRenderCache.generateCache(world, blockPos, blockPos2, n, false);
        }

        public ChunkRender getRenderChunkOffset16(ViewFrustum viewFrustum, Direction direction) {
            if (!this.renderChunksOffset16Updated) {
                for (int i = 0; i < Direction.VALUES.length; ++i) {
                    Direction direction2 = Direction.VALUES[i];
                    BlockPos blockPos = this.getBlockPosOffset16(direction2);
                    this.renderChunksOfset16[i] = viewFrustum.getRenderChunk(blockPos);
                }
                this.renderChunksOffset16Updated = true;
            }
            return this.renderChunksOfset16[direction.ordinal()];
        }

        public Chunk getChunk() {
            return this.getChunk(this.position);
        }

        private Chunk getChunk(BlockPos blockPos) {
            Chunk chunk = this.chunk;
            if (chunk != null && ChunkUtils.isLoaded(chunk)) {
                return chunk;
            }
            this.chunk = chunk = this.this$0.world.getChunkAt(blockPos);
            return chunk;
        }

        public boolean isChunkRegionEmpty() {
            return this.isChunkRegionEmpty(this.position);
        }

        private boolean isChunkRegionEmpty(BlockPos blockPos) {
            int n = blockPos.getY();
            int n2 = n + 15;
            return this.getChunk(blockPos).isEmptyBetween(n, n2);
        }

        public void setRenderChunkNeighbour(Direction direction, ChunkRender chunkRender) {
            this.renderChunkNeighbours[direction.ordinal()] = chunkRender;
            this.renderChunkNeighboursValid[direction.ordinal()] = chunkRender;
        }

        public ChunkRender getRenderChunkNeighbour(Direction direction) {
            if (!this.renderChunkNeighboursUpated) {
                this.updateRenderChunkNeighboursValid();
            }
            return this.renderChunkNeighboursValid[direction.ordinal()];
        }

        public WorldRenderer.LocalRenderInformationContainer getRenderInfo() {
            return this.renderInfo;
        }

        private void updateRenderChunkNeighboursValid() {
            int n = this.getPosition().getX();
            int n2 = this.getPosition().getZ();
            int n3 = Direction.NORTH.ordinal();
            int n4 = Direction.SOUTH.ordinal();
            int n5 = Direction.WEST.ordinal();
            int n6 = Direction.EAST.ordinal();
            this.renderChunkNeighboursValid[n3] = this.renderChunkNeighbours[n3].getPosition().getZ() == n2 - 16 ? this.renderChunkNeighbours[n3] : null;
            this.renderChunkNeighboursValid[n4] = this.renderChunkNeighbours[n4].getPosition().getZ() == n2 + 16 ? this.renderChunkNeighbours[n4] : null;
            this.renderChunkNeighboursValid[n5] = this.renderChunkNeighbours[n5].getPosition().getX() == n - 16 ? this.renderChunkNeighbours[n5] : null;
            this.renderChunkNeighboursValid[n6] = this.renderChunkNeighbours[n6].getPosition().getX() == n + 16 ? this.renderChunkNeighbours[n6] : null;
            this.renderChunkNeighboursUpated = true;
        }

        public boolean isBoundingBoxInFrustum(ICamera iCamera, int n) {
            return this.getBoundingBoxParent().isBoundingBoxInFrustumFully(iCamera, n) ? true : iCamera.isBoundingBoxInFrustum(this.boundingBox);
        }

        public AabbFrame getBoundingBoxParent() {
            if (this.boundingBoxParent == null) {
                AabbFrame aabbFrame;
                BlockPos blockPos = this.getPosition();
                int n = blockPos.getX();
                int n2 = blockPos.getY();
                int n3 = blockPos.getZ();
                int n4 = 5;
                int n5 = n >> n4 << n4;
                int n6 = n2 >> n4 << n4;
                int n7 = n3 >> n4 << n4;
                if ((n5 != n || n6 != n2 || n7 != n3) && (aabbFrame = this.this$0.worldRenderer.getRenderChunk(new BlockPos(n5, n6, n7)).getBoundingBoxParent()) != null && aabbFrame.minX == (double)n5 && aabbFrame.minY == (double)n6 && aabbFrame.minZ == (double)n7) {
                    this.boundingBoxParent = aabbFrame;
                }
                if (this.boundingBoxParent == null) {
                    int n8 = 1 << n4;
                    this.boundingBoxParent = new AabbFrame(n5, n6, n7, n5 + n8, n6 + n8, n7 + n8);
                }
            }
            return this.boundingBoxParent;
        }

        public String toString() {
            return "pos: " + this.getPosition() + ", frameIndex: " + this.frameIndex;
        }

        private static void lambda$new$1(BlockPos.Mutable[] mutableArray) {
            for (int i = 0; i < mutableArray.length; ++i) {
                mutableArray[i] = new BlockPos.Mutable();
            }
        }

        private static VertexBuffer lambda$new$0(RenderType renderType) {
            return new VertexBuffer(DefaultVertexFormats.BLOCK);
        }

        class SortTransparencyTask
        extends ChunkRenderTask {
            private final CompiledChunk sortCompiledChunk;
            final ChunkRender this$1;

            public SortTransparencyTask(ChunkRender chunkRender, double d, CompiledChunk compiledChunk) {
                this(chunkRender, null, d, compiledChunk);
            }

            public SortTransparencyTask(ChunkRender chunkRender, ChunkPos chunkPos, double d, CompiledChunk compiledChunk) {
                this.this$1 = chunkRender;
                super(chunkRender, chunkPos, d);
                this.sortCompiledChunk = compiledChunk;
            }

            @Override
            public CompletableFuture<ChunkTaskResult> execute(RegionRenderCacheBuilder regionRenderCacheBuilder) {
                if (this.finished.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (!this.this$1.shouldStayLoaded()) {
                    this.finished.set(false);
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (this.finished.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                Vector3d vector3d = this.this$1.this$0.getRenderPosition();
                float f = (float)vector3d.x;
                float f2 = (float)vector3d.y;
                float f3 = (float)vector3d.z;
                BufferBuilder.State state = this.sortCompiledChunk.state;
                if (state != null && this.sortCompiledChunk.layersUsed.contains(RenderType.getTranslucent())) {
                    BufferBuilder bufferBuilder = regionRenderCacheBuilder.getBuilder(RenderType.getTranslucent());
                    bufferBuilder.setBlockLayer(RenderType.getTranslucent());
                    this.this$1.beginLayer(bufferBuilder);
                    bufferBuilder.setVertexState(state);
                    bufferBuilder.sortVertexData((float)this.this$1.regionDX + f - (float)this.this$1.position.getX(), (float)this.this$1.regionDY + f2 - (float)this.this$1.position.getY(), (float)this.this$1.regionDZ + f3 - (float)this.this$1.position.getZ());
                    this.sortCompiledChunk.state = bufferBuilder.getVertexState();
                    bufferBuilder.finishDrawing();
                    if (this.finished.get()) {
                        return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                    }
                    CompletionStage completionStage = this.this$1.this$0.uploadChunkLayer(regionRenderCacheBuilder.getBuilder(RenderType.getTranslucent()), this.this$1.getVertexBuffer(RenderType.getTranslucent())).thenApply(SortTransparencyTask::lambda$execute$0);
                    return ((CompletableFuture)completionStage).handle(this::lambda$execute$1);
                }
                return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
            }

            @Override
            public void cancel() {
                this.finished.set(false);
            }

            private ChunkTaskResult lambda$execute$1(ChunkTaskResult chunkTaskResult, Throwable throwable) {
                if (throwable != null && !(throwable instanceof CancellationException) && !(throwable instanceof InterruptedException)) {
                    Minecraft.getInstance().crashed(CrashReport.makeCrashReport(throwable, "Rendering chunk"));
                }
                return this.finished.get() ? ChunkTaskResult.CANCELLED : ChunkTaskResult.SUCCESSFUL;
            }

            private static ChunkTaskResult lambda$execute$0(Void void_) {
                return ChunkTaskResult.CANCELLED;
            }
        }

        abstract class ChunkRenderTask
        implements Comparable<ChunkRenderTask> {
            protected final double distanceSq;
            protected final AtomicBoolean finished;
            protected Map<BlockPos, IModelData> modelData;
            final ChunkRender this$1;

            public ChunkRenderTask(ChunkRender chunkRender, double d) {
                this(chunkRender, null, d);
            }

            public ChunkRenderTask(ChunkRender chunkRender, ChunkPos chunkPos, double d) {
                this.this$1 = chunkRender;
                this.finished = new AtomicBoolean(false);
                this.distanceSq = d;
                this.modelData = chunkPos == null ? Collections.emptyMap() : ModelDataManager.getModelData(Minecraft.getInstance().world, chunkPos);
            }

            public abstract CompletableFuture<ChunkTaskResult> execute(RegionRenderCacheBuilder var1);

            public abstract void cancel();

            @Override
            public int compareTo(ChunkRenderTask chunkRenderTask) {
                return Doubles.compare(this.distanceSq, chunkRenderTask.distanceSq);
            }

            public IModelData getModelData(BlockPos blockPos) {
                return this.modelData.getOrDefault(blockPos, EmptyModelData.INSTANCE);
            }

            @Override
            public int compareTo(Object object) {
                return this.compareTo((ChunkRenderTask)object);
            }
        }

        class RebuildTask
        extends ChunkRenderTask {
            @Nullable
            protected ChunkRenderCache chunkRenderCache;
            final ChunkRender this$1;

            public RebuildTask(ChunkRender chunkRender, double d, ChunkRenderCache chunkRenderCache) {
                this(chunkRender, null, d, chunkRenderCache);
            }

            public RebuildTask(ChunkRender chunkRender, ChunkPos chunkPos, double d, ChunkRenderCache chunkRenderCache) {
                this.this$1 = chunkRender;
                super(chunkRender, chunkPos, d);
                this.chunkRenderCache = chunkRenderCache;
            }

            @Override
            public CompletableFuture<ChunkTaskResult> execute(RegionRenderCacheBuilder regionRenderCacheBuilder) {
                if (this.finished.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (!this.this$1.shouldStayLoaded()) {
                    this.chunkRenderCache = null;
                    this.this$1.setNeedsUpdate(true);
                    this.finished.set(false);
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                if (this.finished.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                Vector3d vector3d = this.this$1.this$0.getRenderPosition();
                float f = (float)vector3d.x;
                float f2 = (float)vector3d.y;
                float f3 = (float)vector3d.z;
                CompiledChunk compiledChunk = new CompiledChunk();
                Set<TileEntity> set = this.compile(f, f2, f3, compiledChunk, regionRenderCacheBuilder);
                this.this$1.updateGlobalTileEntities(set);
                if (this.finished.get()) {
                    return CompletableFuture.completedFuture(ChunkTaskResult.CANCELLED);
                }
                ArrayList arrayList = Lists.newArrayList();
                compiledChunk.layersStarted.forEach(arg_0 -> this.lambda$execute$0(arrayList, regionRenderCacheBuilder, arg_0));
                return Util.gather(arrayList).handle((arg_0, arg_1) -> this.lambda$execute$1(compiledChunk, arg_0, arg_1));
            }

            private Set<TileEntity> compile(float f, float f2, float f3, CompiledChunk compiledChunk, RegionRenderCacheBuilder regionRenderCacheBuilder) {
                boolean bl = true;
                BlockPos blockPos = this.this$1.position.toImmutable();
                BlockPos blockPos2 = blockPos.add(15, 15, 15);
                VisGraph visGraph = new VisGraph();
                HashSet<TileEntity> hashSet = Sets.newHashSet();
                this.chunkRenderCache = null;
                MatrixStack matrixStack = new MatrixStack();
                if (!this.this$1.isChunkRegionEmpty(blockPos)) {
                    ++renderChunksUpdated;
                    ChunkCacheOF chunkCacheOF = this.this$1.makeChunkCacheOF(blockPos);
                    chunkCacheOF.renderStart();
                    RenderType[] renderTypeArray = new RenderType[1];
                    boolean bl2 = Config.isShaders();
                    boolean bl3 = bl2 && Shaders.useMidBlockAttrib;
                    BlockModelRenderer.enableCache();
                    Random random2 = new Random();
                    BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
                    for (BlockPosM blockPosM : BlockPosM.getAllInBoxMutable(blockPos, blockPos2)) {
                        RenderEnv renderEnv;
                        BufferBuilder bufferBuilder;
                        RenderType renderType;
                        int n;
                        RenderType[] renderTypeArray2;
                        IModelData iModelData;
                        Object object;
                        BlockState blockState = chunkCacheOF.getBlockState(blockPosM);
                        if (blockState.isAir()) continue;
                        Block object2 = blockState.getBlock();
                        if (blockState.isOpaqueCube(chunkCacheOF, blockPosM)) {
                            visGraph.setOpaqueCube(blockPosM);
                        }
                        if (ReflectorForge.blockHasTileEntity(blockState) && (object = chunkCacheOF.getTileEntity(blockPosM, Chunk.CreateEntityType.CHECK)) != null) {
                            this.handleTileEntity(compiledChunk, hashSet, object);
                        }
                        object = blockState.getFluidState();
                        IModelData iModelData2 = iModelData = FORGE ? this.getModelData(blockPosM) : null;
                        if (!((FluidState)object).isEmpty()) {
                            renderTypeArray2 = this.this$1.getFluidRenderLayers((FluidState)object, renderTypeArray);
                            for (n = 0; n < renderTypeArray2.length; ++n) {
                                renderType = renderTypeArray2[n];
                                if (FORGE_CAN_RENDER_IN_LAYER_FS && !Reflector.callBoolean(Reflector.ForgeRenderTypeLookup_canRenderInLayerFs, object, renderType)) continue;
                                if (FORGE_SET_RENDER_LAYER) {
                                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, renderType);
                                }
                                bufferBuilder = regionRenderCacheBuilder.getBuilder(renderType);
                                bufferBuilder.setBlockLayer(renderType);
                                renderEnv = bufferBuilder.getRenderEnv(blockState, blockPosM);
                                renderEnv.setRegionRenderCacheBuilder(regionRenderCacheBuilder);
                                chunkCacheOF.setRenderEnv(renderEnv);
                                if (compiledChunk.layersStarted.add(renderType)) {
                                    this.this$1.beginLayer(bufferBuilder);
                                }
                                if (!blockRendererDispatcher.renderFluid(blockPosM, chunkCacheOF, bufferBuilder, (FluidState)object)) continue;
                                compiledChunk.empty = false;
                                compiledChunk.layersUsed.add(renderType);
                            }
                        }
                        if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                            renderTypeArray2 = this.this$1.getBlockRenderLayers(blockState, renderTypeArray);
                            for (n = 0; n < renderTypeArray2.length; ++n) {
                                renderType = renderTypeArray2[n];
                                if (FORGE_CAN_RENDER_IN_LAYER_BS && !Reflector.callBoolean(Reflector.ForgeRenderTypeLookup_canRenderInLayerBs, blockState, renderType)) continue;
                                if (FORGE_SET_RENDER_LAYER) {
                                    Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, renderType);
                                }
                                renderType = this.this$1.fixBlockLayer(chunkCacheOF, blockState, blockPosM, renderType);
                                bufferBuilder = regionRenderCacheBuilder.getBuilder(renderType);
                                bufferBuilder.setBlockLayer(renderType);
                                renderEnv = bufferBuilder.getRenderEnv(blockState, blockPosM);
                                renderEnv.setRegionRenderCacheBuilder(regionRenderCacheBuilder);
                                chunkCacheOF.setRenderEnv(renderEnv);
                                if (compiledChunk.layersStarted.add(renderType)) {
                                    this.this$1.beginLayer(bufferBuilder);
                                }
                                matrixStack.push();
                                matrixStack.translate((double)this.this$1.regionDX + (double)(blockPosM.getX() & 0xF), (double)this.this$1.regionDY + (double)(blockPosM.getY() & 0xF), (double)this.this$1.regionDZ + (double)(blockPosM.getZ() & 0xF));
                                if (bl3) {
                                    bufferBuilder.setMidBlock(0.5f + (float)this.this$1.regionDX + (float)(blockPosM.getX() & 0xF), 0.5f + (float)this.this$1.regionDY + (float)(blockPosM.getY() & 0xF), 0.5f + (float)this.this$1.regionDZ + (float)(blockPosM.getZ() & 0xF));
                                }
                                if (blockRendererDispatcher.renderModel(blockState, blockPosM, chunkCacheOF, matrixStack, bufferBuilder, true, random2, iModelData)) {
                                    compiledChunk.empty = false;
                                    compiledChunk.layersUsed.add(renderType);
                                    if (renderEnv.isOverlaysRendered()) {
                                        this.this$1.postRenderOverlays(regionRenderCacheBuilder, compiledChunk);
                                        renderEnv.setOverlaysRendered(true);
                                    }
                                }
                                matrixStack.pop();
                            }
                        }
                        if (!FORGE_SET_RENDER_LAYER) continue;
                        Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[]{null});
                    }
                    if (compiledChunk.layersUsed.contains(RenderType.getTranslucent())) {
                        BufferBuilder bufferBuilder = regionRenderCacheBuilder.getBuilder(RenderType.getTranslucent());
                        bufferBuilder.sortVertexData((float)this.this$1.regionDX + f - (float)blockPos.getX(), (float)this.this$1.regionDY + f2 - (float)blockPos.getY(), (float)this.this$1.regionDZ + f3 - (float)blockPos.getZ());
                        compiledChunk.state = bufferBuilder.getVertexState();
                    }
                    compiledChunk.layersStarted.stream().map(regionRenderCacheBuilder::getBuilder).forEach(BufferBuilder::finishDrawing);
                    for (RenderType renderType : BLOCK_RENDER_LAYERS) {
                        compiledChunk.setAnimatedSprites(renderType, null);
                    }
                    for (RenderType renderType : compiledChunk.layersStarted) {
                        if (Config.isShaders()) {
                            SVertexBuilder.calcNormalChunkLayer(regionRenderCacheBuilder.getBuilder(renderType));
                        }
                        BufferBuilder bufferBuilder = regionRenderCacheBuilder.getBuilder(renderType);
                        if (bufferBuilder.animatedSprites == null || bufferBuilder.animatedSprites.isEmpty()) continue;
                        compiledChunk.setAnimatedSprites(renderType, (BitSet)bufferBuilder.animatedSprites.clone());
                    }
                    chunkCacheOF.renderFinish();
                    BlockModelRenderer.disableCache();
                }
                compiledChunk.setVisibility = visGraph.computeVisibility();
                return hashSet;
            }

            private <E extends TileEntity> void handleTileEntity(CompiledChunk compiledChunk, Set<TileEntity> set, E e) {
                TileEntityRenderer<E> tileEntityRenderer = TileEntityRendererDispatcher.instance.getRenderer(e);
                if (tileEntityRenderer != null) {
                    if (tileEntityRenderer.isGlobalRenderer(e)) {
                        set.add(e);
                    } else {
                        compiledChunk.tileEntities.add(e);
                    }
                }
            }

            @Override
            public void cancel() {
                this.chunkRenderCache = null;
                if (this.finished.compareAndSet(false, false)) {
                    this.this$1.setNeedsUpdate(true);
                }
            }

            private ChunkTaskResult lambda$execute$1(CompiledChunk compiledChunk, List list, Throwable throwable) {
                if (throwable != null && !(throwable instanceof CancellationException) && !(throwable instanceof InterruptedException)) {
                    Minecraft.getInstance().crashed(CrashReport.makeCrashReport(throwable, "Rendering chunk"));
                }
                if (this.finished.get()) {
                    return ChunkTaskResult.CANCELLED;
                }
                this.this$1.compiledChunk.set(compiledChunk);
                return ChunkTaskResult.SUCCESSFUL;
            }

            private void lambda$execute$0(List list, RegionRenderCacheBuilder regionRenderCacheBuilder, RenderType renderType) {
                list.add(this.this$1.this$0.uploadChunkLayer(regionRenderCacheBuilder.getBuilder(renderType), this.this$1.getVertexBuffer(renderType)));
            }
        }
    }

    static enum ChunkTaskResult {
        SUCCESSFUL,
        CANCELLED;

    }

    public static class CompiledChunk {
        public static final CompiledChunk DUMMY = new CompiledChunk(){

            @Override
            public boolean isVisible(Direction direction, Direction direction2) {
                return true;
            }

            @Override
            public void setAnimatedSprites(RenderType renderType, BitSet bitSet) {
                throw new UnsupportedOperationException();
            }
        };
        private final ChunkLayerSet layersUsed = new ChunkLayerSet();
        private final Set<RenderType> layersStarted = new ObjectArraySet<RenderType>();
        private boolean empty = true;
        private final List<TileEntity> tileEntities = Lists.newArrayList();
        private SetVisibility setVisibility = new SetVisibility();
        @Nullable
        private BufferBuilder.State state;
        private BitSet[] animatedSprites = new BitSet[RenderType.CHUNK_RENDER_TYPES.length];

        public boolean isEmpty() {
            return this.empty;
        }

        public boolean isLayerEmpty(RenderType renderType) {
            return !this.layersUsed.contains(renderType);
        }

        public List<TileEntity> getTileEntities() {
            return this.tileEntities;
        }

        public boolean isVisible(Direction direction, Direction direction2) {
            return this.setVisibility.isVisible(direction, direction2);
        }

        public BitSet getAnimatedSprites(RenderType renderType) {
            return this.animatedSprites[renderType.ordinal()];
        }

        public void setAnimatedSprites(RenderType renderType, BitSet bitSet) {
            this.animatedSprites[renderType.ordinal()] = bitSet;
        }

        public boolean isLayerStarted(RenderType renderType) {
            return this.layersStarted.contains(renderType);
        }

        public void setLayerStarted(RenderType renderType) {
            this.layersStarted.add(renderType);
        }

        public void setLayerUsed(RenderType renderType) {
            this.layersUsed.add(renderType);
        }
    }
}

