/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.network.play.server.SMultiBlockChangePacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.ChunkPrimerWrapper;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkManager;

public class ChunkHolder {
    public static final Either<IChunk, IChunkLoadingError> MISSING_CHUNK = Either.right(IChunkLoadingError.UNLOADED);
    public static final CompletableFuture<Either<IChunk, IChunkLoadingError>> MISSING_CHUNK_FUTURE = CompletableFuture.completedFuture(MISSING_CHUNK);
    public static final Either<Chunk, IChunkLoadingError> UNLOADED_CHUNK = Either.right(IChunkLoadingError.UNLOADED);
    private static final CompletableFuture<Either<Chunk, IChunkLoadingError>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_CHUNK);
    private static final List<ChunkStatus> CHUNK_STATUS_LIST = ChunkStatus.getAll();
    private static final LocationType[] LOCATION_TYPES = LocationType.values();
    private final AtomicReferenceArray<CompletableFuture<Either<IChunk, IChunkLoadingError>>> field_219312_g = new AtomicReferenceArray(CHUNK_STATUS_LIST.size());
    private volatile CompletableFuture<Either<Chunk, IChunkLoadingError>> borderFuture = UNLOADED_CHUNK_FUTURE;
    private volatile CompletableFuture<Either<Chunk, IChunkLoadingError>> tickingFuture = UNLOADED_CHUNK_FUTURE;
    private volatile CompletableFuture<Either<Chunk, IChunkLoadingError>> entityTickingFuture = UNLOADED_CHUNK_FUTURE;
    private CompletableFuture<IChunk> field_219315_j = CompletableFuture.completedFuture(null);
    private int prevChunkLevel;
    private int chunkLevel;
    private int field_219318_m;
    private final ChunkPos pos;
    private boolean field_244382_p;
    private final ShortSet[] field_244383_q = new ShortSet[16];
    private int blockLightChangeMask;
    private int skyLightChangeMask;
    private final WorldLightManager lightManager;
    private final IListener field_219327_v;
    private final IPlayerProvider playerProvider;
    private boolean accessible;
    private boolean field_244384_x;

    public ChunkHolder(ChunkPos chunkPos, int n, WorldLightManager worldLightManager, IListener iListener, IPlayerProvider iPlayerProvider) {
        this.pos = chunkPos;
        this.lightManager = worldLightManager;
        this.field_219327_v = iListener;
        this.playerProvider = iPlayerProvider;
        this.chunkLevel = this.prevChunkLevel = ChunkManager.MAX_LOADED_LEVEL + 1;
        this.field_219318_m = this.prevChunkLevel;
        this.setChunkLevel(n);
    }

    public CompletableFuture<Either<IChunk, IChunkLoadingError>> func_219301_a(ChunkStatus chunkStatus) {
        CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture = this.field_219312_g.get(chunkStatus.ordinal());
        return completableFuture == null ? MISSING_CHUNK_FUTURE : completableFuture;
    }

    public CompletableFuture<Either<IChunk, IChunkLoadingError>> func_225410_b(ChunkStatus chunkStatus) {
        return ChunkHolder.getChunkStatusFromLevel(this.chunkLevel).isAtLeast(chunkStatus) ? this.func_219301_a(chunkStatus) : MISSING_CHUNK_FUTURE;
    }

    public CompletableFuture<Either<Chunk, IChunkLoadingError>> getTickingFuture() {
        return this.tickingFuture;
    }

    public CompletableFuture<Either<Chunk, IChunkLoadingError>> getEntityTickingFuture() {
        return this.entityTickingFuture;
    }

    public CompletableFuture<Either<Chunk, IChunkLoadingError>> getBorderFuture() {
        return this.borderFuture;
    }

    @Nullable
    public Chunk getChunkIfComplete() {
        CompletableFuture<Either<Chunk, IChunkLoadingError>> completableFuture = this.getTickingFuture();
        Either<Chunk, IChunkLoadingError> either = completableFuture.getNow(null);
        return either == null ? null : either.left().orElse(null);
    }

    @Nullable
    public ChunkStatus func_219285_d() {
        for (int i = CHUNK_STATUS_LIST.size() - 1; i >= 0; --i) {
            ChunkStatus chunkStatus = CHUNK_STATUS_LIST.get(i);
            CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture = this.func_219301_a(chunkStatus);
            if (!completableFuture.getNow(MISSING_CHUNK).left().isPresent()) continue;
            return chunkStatus;
        }
        return null;
    }

    @Nullable
    public IChunk func_219287_e() {
        for (int i = CHUNK_STATUS_LIST.size() - 1; i >= 0; --i) {
            Optional<IChunk> optional;
            ChunkStatus chunkStatus = CHUNK_STATUS_LIST.get(i);
            CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture = this.func_219301_a(chunkStatus);
            if (completableFuture.isCompletedExceptionally() || !(optional = completableFuture.getNow(MISSING_CHUNK).left()).isPresent()) continue;
            return optional.get();
        }
        return null;
    }

    public CompletableFuture<IChunk> func_219302_f() {
        return this.field_219315_j;
    }

    public void func_244386_a(BlockPos blockPos) {
        Chunk chunk = this.getChunkIfComplete();
        if (chunk != null) {
            byte by = (byte)SectionPos.toChunk(blockPos.getY());
            if (this.field_244383_q[by] == null) {
                this.field_244382_p = true;
                this.field_244383_q[by] = new ShortArraySet();
            }
            this.field_244383_q[by].add(SectionPos.toRelativeOffset(blockPos));
        }
    }

    public void markLightChanged(LightType lightType, int n) {
        Chunk chunk = this.getChunkIfComplete();
        if (chunk != null) {
            chunk.setModified(false);
            if (lightType == LightType.SKY) {
                this.skyLightChangeMask |= 1 << n - -1;
            } else {
                this.blockLightChangeMask |= 1 << n - -1;
            }
        }
    }

    public void sendChanges(Chunk chunk) {
        if (this.field_244382_p || this.skyLightChangeMask != 0 || this.blockLightChangeMask != 0) {
            int n;
            World world = chunk.getWorld();
            int n2 = 0;
            for (n = 0; n < this.field_244383_q.length; ++n) {
                n2 += this.field_244383_q[n] != null ? this.field_244383_q[n].size() : 0;
            }
            this.field_244384_x |= n2 >= 64;
            if (this.skyLightChangeMask != 0 || this.blockLightChangeMask != 0) {
                this.sendToTracking(new SUpdateLightPacket(chunk.getPos(), this.lightManager, this.skyLightChangeMask, this.blockLightChangeMask, true), !this.field_244384_x);
                this.skyLightChangeMask = 0;
                this.blockLightChangeMask = 0;
            }
            for (n = 0; n < this.field_244383_q.length; ++n) {
                ShortSet shortSet = this.field_244383_q[n];
                if (shortSet == null) continue;
                SectionPos sectionPos = SectionPos.from(chunk.getPos(), n);
                if (shortSet.size() == 1) {
                    var7_7 = sectionPos.func_243647_g(shortSet.iterator().nextShort());
                    var8_8 = world.getBlockState((BlockPos)var7_7);
                    this.sendToTracking(new SChangeBlockPacket((BlockPos)var7_7, (BlockState)var8_8), true);
                    this.func_244385_a(world, (BlockPos)var7_7, (BlockState)var8_8);
                } else {
                    var7_7 = chunk.getSections()[sectionPos.getY()];
                    var8_8 = new SMultiBlockChangePacket(sectionPos, shortSet, (ChunkSection)var7_7, this.field_244384_x);
                    this.sendToTracking((IPacket<?>)var8_8, true);
                    ((SMultiBlockChangePacket)var8_8).func_244310_a((arg_0, arg_1) -> this.lambda$sendChanges$0(world, arg_0, arg_1));
                }
                this.field_244383_q[n] = null;
            }
            this.field_244382_p = false;
        }
    }

    private void func_244385_a(World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock().isTileEntityProvider()) {
            this.sendTileEntity(world, blockPos);
        }
    }

    private void sendTileEntity(World world, BlockPos blockPos) {
        SUpdateTileEntityPacket sUpdateTileEntityPacket;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity != null && (sUpdateTileEntityPacket = tileEntity.getUpdatePacket()) != null) {
            this.sendToTracking(sUpdateTileEntityPacket, true);
        }
    }

    private void sendToTracking(IPacket<?> iPacket, boolean bl) {
        this.playerProvider.getTrackingPlayers(this.pos, bl).forEach(arg_0 -> ChunkHolder.lambda$sendToTracking$1(iPacket, arg_0));
    }

    public CompletableFuture<Either<IChunk, IChunkLoadingError>> func_219276_a(ChunkStatus chunkStatus, ChunkManager chunkManager) {
        Object object;
        int n = chunkStatus.ordinal();
        CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture = this.field_219312_g.get(n);
        if (completableFuture != null && ((object = completableFuture.getNow(null)) == null || ((Either)object).left().isPresent())) {
            return completableFuture;
        }
        if (ChunkHolder.getChunkStatusFromLevel(this.chunkLevel).isAtLeast(chunkStatus)) {
            object = chunkManager.func_219244_a(this, chunkStatus);
            this.chain((CompletableFuture<? extends Either<? extends IChunk, IChunkLoadingError>>)object);
            this.field_219312_g.set(n, (CompletableFuture<Either<IChunk, IChunkLoadingError>>)object);
            return object;
        }
        return completableFuture == null ? MISSING_CHUNK_FUTURE : completableFuture;
    }

    private void chain(CompletableFuture<? extends Either<? extends IChunk, IChunkLoadingError>> completableFuture) {
        this.field_219315_j = this.field_219315_j.thenCombine(completableFuture, ChunkHolder::lambda$chain$4);
    }

    public LocationType func_219300_g() {
        return ChunkHolder.getLocationTypeFromLevel(this.chunkLevel);
    }

    public ChunkPos getPosition() {
        return this.pos;
    }

    public int getChunkLevel() {
        return this.chunkLevel;
    }

    public int func_219281_j() {
        return this.field_219318_m;
    }

    private void func_219275_d(int n) {
        this.field_219318_m = n;
    }

    public void setChunkLevel(int n) {
        this.chunkLevel = n;
    }

    protected void processUpdates(ChunkManager chunkManager) {
        CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture;
        int n;
        ChunkStatus chunkStatus = ChunkHolder.getChunkStatusFromLevel(this.prevChunkLevel);
        ChunkStatus chunkStatus2 = ChunkHolder.getChunkStatusFromLevel(this.chunkLevel);
        boolean bl = this.prevChunkLevel <= ChunkManager.MAX_LOADED_LEVEL;
        boolean bl2 = this.chunkLevel <= ChunkManager.MAX_LOADED_LEVEL;
        LocationType locationType = ChunkHolder.getLocationTypeFromLevel(this.prevChunkLevel);
        LocationType locationType2 = ChunkHolder.getLocationTypeFromLevel(this.chunkLevel);
        if (bl) {
            Either either = Either.right(new IChunkLoadingError(this){
                final ChunkHolder this$0;
                {
                    this.this$0 = chunkHolder;
                }

                public String toString() {
                    return "Unloaded ticket level " + this.this$0.pos.toString();
                }
            });
            int n2 = n = bl2 ? chunkStatus2.ordinal() + 1 : 0;
            while (n <= chunkStatus.ordinal()) {
                completableFuture = this.field_219312_g.get(n);
                if (completableFuture != null) {
                    completableFuture.complete(either);
                } else {
                    this.field_219312_g.set(n, CompletableFuture.completedFuture(either));
                }
                ++n;
            }
        }
        boolean bl3 = locationType.isAtLeast(LocationType.BORDER);
        n = locationType2.isAtLeast(LocationType.BORDER);
        this.accessible |= n;
        if (!bl3 && n != 0) {
            this.borderFuture = chunkManager.func_222961_b(this);
            this.chain(this.borderFuture);
        }
        if (bl3 && n == 0) {
            completableFuture = this.borderFuture;
            this.borderFuture = UNLOADED_CHUNK_FUTURE;
            this.chain((CompletableFuture<? extends Either<? extends IChunk, IChunkLoadingError>>)completableFuture.thenApply(arg_0 -> ChunkHolder.lambda$processUpdates$5(chunkManager, arg_0)));
        }
        boolean bl4 = locationType.isAtLeast(LocationType.TICKING);
        boolean bl5 = locationType2.isAtLeast(LocationType.TICKING);
        if (!bl4 && bl5) {
            this.tickingFuture = chunkManager.func_219179_a(this);
            this.chain(this.tickingFuture);
        }
        if (bl4 && !bl5) {
            this.tickingFuture.complete(UNLOADED_CHUNK);
            this.tickingFuture = UNLOADED_CHUNK_FUTURE;
        }
        boolean bl6 = locationType.isAtLeast(LocationType.ENTITY_TICKING);
        boolean bl7 = locationType2.isAtLeast(LocationType.ENTITY_TICKING);
        if (!bl6 && bl7) {
            if (this.entityTickingFuture != UNLOADED_CHUNK_FUTURE) {
                throw Util.pauseDevMode(new IllegalStateException());
            }
            this.entityTickingFuture = chunkManager.func_219188_b(this.pos);
            this.chain(this.entityTickingFuture);
        }
        if (bl6 && !bl7) {
            this.entityTickingFuture.complete(UNLOADED_CHUNK);
            this.entityTickingFuture = UNLOADED_CHUNK_FUTURE;
        }
        this.field_219327_v.func_219066_a(this.pos, this::func_219281_j, this.chunkLevel, this::func_219275_d);
        this.prevChunkLevel = this.chunkLevel;
    }

    public static ChunkStatus getChunkStatusFromLevel(int n) {
        return n < 33 ? ChunkStatus.FULL : ChunkStatus.getStatus(n - 33);
    }

    public static LocationType getLocationTypeFromLevel(int n) {
        return LOCATION_TYPES[MathHelper.clamp(33 - n + 1, 0, LOCATION_TYPES.length - 1)];
    }

    public boolean isAccessible() {
        return this.accessible;
    }

    public void updateAccessible() {
        this.accessible = ChunkHolder.getLocationTypeFromLevel(this.chunkLevel).isAtLeast(LocationType.BORDER);
    }

    public void func_219294_a(ChunkPrimerWrapper chunkPrimerWrapper) {
        for (int i = 0; i < this.field_219312_g.length(); ++i) {
            Optional<IChunk> optional;
            CompletableFuture<Either<IChunk, IChunkLoadingError>> completableFuture = this.field_219312_g.get(i);
            if (completableFuture == null || !(optional = completableFuture.getNow(MISSING_CHUNK).left()).isPresent() || !(optional.get() instanceof ChunkPrimer)) continue;
            this.field_219312_g.set(i, CompletableFuture.completedFuture(Either.left(chunkPrimerWrapper)));
        }
        this.chain(CompletableFuture.completedFuture(Either.left(chunkPrimerWrapper.getChunk())));
    }

    private static Either lambda$processUpdates$5(ChunkManager chunkManager, Either either) {
        return either.ifLeft(chunkManager::func_222973_a);
    }

    private static IChunk lambda$chain$4(IChunk iChunk, Either either) {
        return either.map(ChunkHolder::lambda$chain$2, arg_0 -> ChunkHolder.lambda$chain$3(iChunk, arg_0));
    }

    private static IChunk lambda$chain$3(IChunk iChunk, IChunkLoadingError iChunkLoadingError) {
        return iChunk;
    }

    private static IChunk lambda$chain$2(IChunk iChunk) {
        return iChunk;
    }

    private static void lambda$sendToTracking$1(IPacket iPacket, ServerPlayerEntity serverPlayerEntity) {
        serverPlayerEntity.connection.sendPacket(iPacket);
    }

    private void lambda$sendChanges$0(World world, BlockPos blockPos, BlockState blockState) {
        this.func_244385_a(world, blockPos, blockState);
    }

    public static interface IListener {
        public void func_219066_a(ChunkPos var1, IntSupplier var2, int var3, IntConsumer var4);
    }

    public static interface IPlayerProvider {
        public Stream<ServerPlayerEntity> getTrackingPlayers(ChunkPos var1, boolean var2);
    }

    public static enum LocationType {
        INACCESSIBLE,
        BORDER,
        TICKING,
        ENTITY_TICKING;


        public boolean isAtLeast(LocationType locationType) {
            return this.ordinal() >= locationType.ordinal();
        }
    }

    public static interface IChunkLoadingError {
        public static final IChunkLoadingError UNLOADED = new IChunkLoadingError(){

            public String toString() {
                return "UNLOADED";
            }
        };
    }
}

