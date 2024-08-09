/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IdentityPalette;
import net.minecraft.util.palette.PalettedContainer;
import net.minecraft.world.chunk.Chunk;
import net.optifine.ChunkDataOF;
import net.optifine.ChunkSectionDataOF;

public class ChunkSection {
    private static final IPalette<BlockState> REGISTRY_PALETTE = new IdentityPalette<BlockState>(Block.BLOCK_STATE_IDS, Blocks.AIR.getDefaultState());
    private final int yBase;
    private short blockRefCount;
    private short blockTickRefCount;
    private short fluidRefCount;
    private final PalettedContainer<BlockState> data;
    public static final ThreadLocal<ChunkDataOF> THREAD_CHUNK_DATA_OF = new ThreadLocal();

    public ChunkSection(int n) {
        this(n, 0, 0, 0);
    }

    public ChunkSection(int n, short s, short s2, short s3) {
        this.yBase = n;
        this.blockRefCount = s;
        this.blockTickRefCount = s2;
        this.fluidRefCount = s3;
        this.data = new PalettedContainer<BlockState>(REGISTRY_PALETTE, Block.BLOCK_STATE_IDS, NBTUtil::readBlockState, NBTUtil::writeBlockState, Blocks.AIR.getDefaultState());
    }

    public BlockState getBlockState(int n, int n2, int n3) {
        return this.data.get(n, n2, n3);
    }

    public FluidState getFluidState(int n, int n2, int n3) {
        return this.data.get(n, n2, n3).getFluidState();
    }

    public void lock() {
        this.data.lock();
    }

    public void unlock() {
        this.data.unlock();
    }

    public BlockState setBlockState(int n, int n2, int n3, BlockState blockState) {
        return this.setBlockState(n, n2, n3, blockState, false);
    }

    public BlockState setBlockState(int n, int n2, int n3, BlockState blockState, boolean bl) {
        BlockState blockState2 = bl ? this.data.lockedSwap(n, n2, n3, blockState) : this.data.swap(n, n2, n3, blockState);
        FluidState fluidState = blockState2.getFluidState();
        FluidState fluidState2 = blockState.getFluidState();
        if (!blockState2.isAir()) {
            this.blockRefCount = (short)(this.blockRefCount - 1);
            if (blockState2.ticksRandomly()) {
                this.blockTickRefCount = (short)(this.blockTickRefCount - 1);
            }
        }
        if (!fluidState.isEmpty()) {
            this.fluidRefCount = (short)(this.fluidRefCount - 1);
        }
        if (!blockState.isAir()) {
            this.blockRefCount = (short)(this.blockRefCount + 1);
            if (blockState.ticksRandomly()) {
                this.blockTickRefCount = (short)(this.blockTickRefCount + 1);
            }
        }
        if (!fluidState2.isEmpty()) {
            this.fluidRefCount = (short)(this.fluidRefCount + 1);
        }
        return blockState2;
    }

    public boolean isEmpty() {
        return this.blockRefCount == 0;
    }

    public static boolean isEmpty(@Nullable ChunkSection chunkSection) {
        return chunkSection == Chunk.EMPTY_SECTION || chunkSection.isEmpty();
    }

    public boolean needsRandomTickAny() {
        return this.needsRandomTick() || this.needsRandomTickFluid();
    }

    public boolean needsRandomTick() {
        return this.blockTickRefCount > 0;
    }

    public boolean needsRandomTickFluid() {
        return this.fluidRefCount > 0;
    }

    public int getYLocation() {
        return this.yBase;
    }

    public void recalculateRefCounts() {
        ChunkSectionDataOF chunkSectionDataOF;
        int n;
        ChunkSectionDataOF[] chunkSectionDataOFArray;
        ChunkDataOF chunkDataOF = THREAD_CHUNK_DATA_OF.get();
        if (chunkDataOF != null && (chunkSectionDataOFArray = chunkDataOF.getChunkSectionDatas()) != null && (n = this.yBase >> 4) >= 0 && n < chunkSectionDataOFArray.length && (chunkSectionDataOF = chunkSectionDataOFArray[n]) != null) {
            this.blockRefCount = chunkSectionDataOF.getBlockRefCount();
            this.blockTickRefCount = chunkSectionDataOF.getTickRefCount();
            this.fluidRefCount = chunkSectionDataOF.getFluidRefCount();
            chunkSectionDataOFArray[n] = null;
            return;
        }
        this.blockRefCount = 0;
        this.blockTickRefCount = 0;
        this.fluidRefCount = 0;
        this.data.count(this::lambda$recalculateRefCounts$0);
    }

    public PalettedContainer<BlockState> getData() {
        return this.data;
    }

    public void read(PacketBuffer packetBuffer) {
        this.blockRefCount = packetBuffer.readShort();
        this.data.read(packetBuffer);
    }

    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeShort(this.blockRefCount);
        this.data.write(packetBuffer);
    }

    public int getSize() {
        return 2 + this.data.getSerializedSize();
    }

    public boolean isValidPOIState(Predicate<BlockState> predicate) {
        return this.data.func_235963_a_(predicate);
    }

    public short getBlockRefCount() {
        return this.blockRefCount;
    }

    public short getTickRefCount() {
        return this.blockTickRefCount;
    }

    public short getFluidRefCount() {
        return this.fluidRefCount;
    }

    private void lambda$recalculateRefCounts$0(BlockState blockState, int n) {
        FluidState fluidState = blockState.getFluidState();
        if (!blockState.isAir()) {
            this.blockRefCount = (short)(this.blockRefCount + n);
            if (blockState.ticksRandomly()) {
                this.blockTickRefCount = (short)(this.blockTickRefCount + n);
            }
        }
        if (!fluidState.isEmpty()) {
            this.blockRefCount = (short)(this.blockRefCount + n);
            if (fluidState.ticksRandomly()) {
                this.fluidRefCount = (short)(this.fluidRefCount + n);
            }
        }
    }
}

