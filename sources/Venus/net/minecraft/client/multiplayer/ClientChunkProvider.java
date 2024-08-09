/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.lighting.WorldLightManager;
import net.optifine.ChunkDataOF;
import net.optifine.ChunkOF;
import net.optifine.reflect.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ClientChunkProvider
extends AbstractChunkProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Chunk empty;
    private final WorldLightManager lightManager;
    private volatile ChunkArray array;
    private final ClientWorld world;

    public ClientChunkProvider(ClientWorld clientWorld, int n) {
        this.world = clientWorld;
        this.empty = new EmptyChunk((World)clientWorld, new ChunkPos(0, 0));
        this.lightManager = new WorldLightManager(this, true, clientWorld.getDimensionType().hasSkyLight());
        this.array = new ChunkArray(this, ClientChunkProvider.adjustViewDistance(n));
    }

    @Override
    public WorldLightManager getLightManager() {
        return this.lightManager;
    }

    private static boolean isValid(@Nullable Chunk chunk, int n, int n2) {
        if (chunk == null) {
            return true;
        }
        ChunkPos chunkPos = chunk.getPos();
        return chunkPos.x == n && chunkPos.z == n2;
    }

    public void unloadChunk(int n, int n2) {
        int n3;
        Chunk chunk;
        if (this.array.inView(n, n2) && ClientChunkProvider.isValid(chunk = this.array.get(n3 = this.array.getIndex(n, n2)), n, n2)) {
            if (Reflector.ChunkEvent_Unload_Constructor.exists()) {
                Reflector.postForgeBusEvent(Reflector.ChunkEvent_Unload_Constructor, chunk);
            }
            chunk.setLoaded(true);
            this.array.unload(n3, chunk, null);
        }
    }

    @Override
    @Nullable
    public Chunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        Chunk chunk;
        if (this.array.inView(n, n2) && ClientChunkProvider.isValid(chunk = this.array.get(this.array.getIndex(n, n2)), n, n2)) {
            return chunk;
        }
        return bl ? this.empty : null;
    }

    @Override
    public IBlockReader getWorld() {
        return this.world;
    }

    @Nullable
    public Chunk loadChunk(int n, int n2, @Nullable BiomeContainer biomeContainer, PacketBuffer packetBuffer, CompoundNBT compoundNBT, int n3, boolean bl) {
        Object object;
        Object object2;
        if (!this.array.inView(n, n2)) {
            LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", (Object)n, (Object)n2);
            return null;
        }
        int n4 = this.array.getIndex(n, n2);
        Chunk chunk = this.array.chunks.get(n4);
        if (!bl && ClientChunkProvider.isValid(chunk, n, n2)) {
            boolean bl2 = false;
            if (chunk instanceof ChunkOF) {
                object2 = (ChunkOF)chunk;
                Object object3 = packetBuffer.getCustomData("ChunkDataOF");
                if (object3 instanceof ChunkDataOF) {
                    object = (ChunkDataOF)object3;
                    ((ChunkOF)object2).setChunkDataOF((ChunkDataOF)object);
                    ChunkSection.THREAD_CHUNK_DATA_OF.set((ChunkDataOF)object);
                    bl2 = true;
                }
            }
            chunk.read(biomeContainer, packetBuffer, compoundNBT, n3);
            if (bl2) {
                ChunkSection.THREAD_CHUNK_DATA_OF.set(null);
            }
        } else {
            if (biomeContainer == null) {
                LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", (Object)n, (Object)n2);
                return null;
            }
            if (chunk != null) {
                chunk.setLoaded(true);
            }
            chunk = new ChunkOF(this.world, new ChunkPos(n, n2), biomeContainer);
            chunk.read(biomeContainer, packetBuffer, compoundNBT, n3);
            this.array.replace(n4, chunk);
        }
        ChunkSection[] chunkSectionArray = chunk.getSections();
        object2 = this.getLightManager();
        ((WorldLightManager)object2).enableLightSources(new ChunkPos(n, n2), false);
        for (int i = 0; i < chunkSectionArray.length; ++i) {
            object = chunkSectionArray[i];
            ((WorldLightManager)object2).updateSectionStatus(SectionPos.of(n, i, n2), ChunkSection.isEmpty((ChunkSection)object));
        }
        this.world.onChunkLoaded(n, n2);
        if (Reflector.ChunkEvent_Load_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.ChunkEvent_Load_Constructor, chunk);
        }
        chunk.setLoaded(false);
        return chunk;
    }

    public void tick(BooleanSupplier booleanSupplier) {
    }

    public void setCenter(int n, int n2) {
        this.array.centerX = n;
        this.array.centerZ = n2;
    }

    public void setViewDistance(int n) {
        int n2 = this.array.viewDistance;
        int n3 = ClientChunkProvider.adjustViewDistance(n);
        if (n2 != n3) {
            ChunkArray chunkArray = new ChunkArray(this, n3);
            chunkArray.centerX = this.array.centerX;
            chunkArray.centerZ = this.array.centerZ;
            for (int i = 0; i < this.array.chunks.length(); ++i) {
                Chunk chunk = this.array.chunks.get(i);
                if (chunk == null) continue;
                ChunkPos chunkPos = chunk.getPos();
                if (!chunkArray.inView(chunkPos.x, chunkPos.z)) continue;
                chunkArray.replace(chunkArray.getIndex(chunkPos.x, chunkPos.z), chunk);
            }
            this.array = chunkArray;
        }
    }

    private static int adjustViewDistance(int n) {
        return Math.max(2, n) + 3;
    }

    @Override
    public String makeString() {
        return "Client Chunk Cache: " + this.array.chunks.length() + ", " + this.getLoadedChunksCount();
    }

    public int getLoadedChunksCount() {
        return this.array.loaded;
    }

    @Override
    public void markLightChanged(LightType lightType, SectionPos sectionPos) {
        Minecraft.getInstance().worldRenderer.markForRerender(sectionPos.getSectionX(), sectionPos.getSectionY(), sectionPos.getSectionZ());
    }

    @Override
    public boolean canTick(BlockPos blockPos) {
        return this.chunkExists(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    @Override
    public boolean isChunkLoaded(ChunkPos chunkPos) {
        return this.chunkExists(chunkPos.x, chunkPos.z);
    }

    @Override
    public boolean isChunkLoaded(Entity entity2) {
        return this.chunkExists(MathHelper.floor(entity2.getPosX()) >> 4, MathHelper.floor(entity2.getPosZ()) >> 4);
    }

    @Override
    @Nullable
    public IChunk getChunk(int n, int n2, ChunkStatus chunkStatus, boolean bl) {
        return this.getChunk(n, n2, chunkStatus, bl);
    }

    final class ChunkArray {
        private final AtomicReferenceArray<Chunk> chunks;
        private final int viewDistance;
        private final int sideLength;
        private volatile int centerX;
        private volatile int centerZ;
        private int loaded;
        final ClientChunkProvider this$0;

        private ChunkArray(ClientChunkProvider clientChunkProvider, int n) {
            this.this$0 = clientChunkProvider;
            this.viewDistance = n;
            this.sideLength = n * 2 + 1;
            this.chunks = new AtomicReferenceArray(this.sideLength * this.sideLength);
        }

        private int getIndex(int n, int n2) {
            return Math.floorMod(n2, this.sideLength) * this.sideLength + Math.floorMod(n, this.sideLength);
        }

        protected void replace(int n, @Nullable Chunk chunk) {
            Chunk chunk2 = this.chunks.getAndSet(n, chunk);
            if (chunk2 != null) {
                --this.loaded;
                this.this$0.world.onChunkUnloaded(chunk2);
            }
            if (chunk != null) {
                ++this.loaded;
            }
        }

        protected Chunk unload(int n, Chunk chunk, @Nullable Chunk chunk2) {
            if (this.chunks.compareAndSet(n, chunk, chunk2) && chunk2 == null) {
                --this.loaded;
            }
            this.this$0.world.onChunkUnloaded(chunk);
            return chunk;
        }

        private boolean inView(int n, int n2) {
            return Math.abs(n - this.centerX) <= this.viewDistance && Math.abs(n2 - this.centerZ) <= this.viewDistance;
        }

        @Nullable
        protected Chunk get(int n) {
            return this.chunks.get(n);
        }
    }
}

