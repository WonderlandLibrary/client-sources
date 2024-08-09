/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.IWorldLightListener;
import net.minecraft.world.lighting.LevelBasedGraph;
import net.minecraft.world.lighting.LightDataMap;
import net.minecraft.world.lighting.SectionLightStorage;
import org.apache.commons.lang3.mutable.MutableInt;

public abstract class LightEngine<M extends LightDataMap<M>, S extends SectionLightStorage<M>>
extends LevelBasedGraph
implements IWorldLightListener {
    private static final Direction[] DIRECTIONS = Direction.values();
    protected final IChunkLightProvider chunkProvider;
    protected final LightType type;
    protected final S storage;
    private boolean field_215629_e;
    protected final BlockPos.Mutable scratchPos = new BlockPos.Mutable();
    private final long[] recentPositions = new long[2];
    private final IBlockReader[] recentChunks = new IBlockReader[2];

    public LightEngine(IChunkLightProvider iChunkLightProvider, LightType lightType, S s) {
        super(16, 256, 8192);
        this.chunkProvider = iChunkLightProvider;
        this.type = lightType;
        this.storage = s;
        this.invalidateCaches();
    }

    @Override
    protected void scheduleUpdate(long l) {
        ((SectionLightStorage)this.storage).processAllLevelUpdates();
        if (((SectionLightStorage)this.storage).hasSection(SectionPos.worldToSection(l))) {
            super.scheduleUpdate(l);
        }
    }

    @Nullable
    private IBlockReader getChunkReader(int n, int n2) {
        long l = ChunkPos.asLong(n, n2);
        for (int i = 0; i < 2; ++i) {
            if (l != this.recentPositions[i]) continue;
            return this.recentChunks[i];
        }
        IBlockReader iBlockReader = this.chunkProvider.getChunkForLight(n, n2);
        for (int i = 1; i > 0; --i) {
            this.recentPositions[i] = this.recentPositions[i - 1];
            this.recentChunks[i] = this.recentChunks[i - 1];
        }
        this.recentPositions[0] = l;
        this.recentChunks[0] = iBlockReader;
        return iBlockReader;
    }

    private void invalidateCaches() {
        Arrays.fill(this.recentPositions, ChunkPos.SENTINEL);
        Arrays.fill(this.recentChunks, null);
    }

    protected BlockState getBlockAndOpacity(long l, @Nullable MutableInt mutableInt) {
        boolean bl;
        int n;
        if (l == Long.MAX_VALUE) {
            if (mutableInt != null) {
                mutableInt.setValue(0);
            }
            return Blocks.AIR.getDefaultState();
        }
        int n2 = SectionPos.toChunk(BlockPos.unpackX(l));
        IBlockReader iBlockReader = this.getChunkReader(n2, n = SectionPos.toChunk(BlockPos.unpackZ(l)));
        if (iBlockReader == null) {
            if (mutableInt != null) {
                mutableInt.setValue(16);
            }
            return Blocks.BEDROCK.getDefaultState();
        }
        this.scratchPos.setPos(l);
        BlockState blockState = iBlockReader.getBlockState(this.scratchPos);
        boolean bl2 = bl = blockState.isSolid() && blockState.isTransparent();
        if (mutableInt != null) {
            mutableInt.setValue(blockState.getOpacity(this.chunkProvider.getWorld(), this.scratchPos));
        }
        return bl ? blockState : Blocks.AIR.getDefaultState();
    }

    protected VoxelShape getVoxelShape(BlockState blockState, long l, Direction direction) {
        return blockState.isSolid() ? blockState.getFaceOcclusionShape(this.chunkProvider.getWorld(), this.scratchPos.setPos(l), direction) : VoxelShapes.empty();
    }

    public static int func_215613_a(IBlockReader iBlockReader, BlockState blockState, BlockPos blockPos, BlockState blockState2, BlockPos blockPos2, Direction direction, int n) {
        boolean bl;
        boolean bl2 = blockState.isSolid() && blockState.isTransparent();
        boolean bl3 = bl = blockState2.isSolid() && blockState2.isTransparent();
        if (!bl2 && !bl) {
            return n;
        }
        VoxelShape voxelShape = bl2 ? blockState.getRenderShapeTrue(iBlockReader, blockPos) : VoxelShapes.empty();
        VoxelShape voxelShape2 = bl ? blockState2.getRenderShapeTrue(iBlockReader, blockPos2) : VoxelShapes.empty();
        return VoxelShapes.doAdjacentCubeSidesFillSquare(voxelShape, voxelShape2, direction) ? 16 : n;
    }

    @Override
    protected boolean isRoot(long l) {
        return l == Long.MAX_VALUE;
    }

    @Override
    protected int computeLevel(long l, long l2, int n) {
        return 1;
    }

    @Override
    protected int getLevel(long l) {
        return l == Long.MAX_VALUE ? 0 : 15 - ((SectionLightStorage)this.storage).getLight(l);
    }

    protected int getLevelFromArray(NibbleArray nibbleArray, long l) {
        return 15 - nibbleArray.get(SectionPos.mask(BlockPos.unpackX(l)), SectionPos.mask(BlockPos.unpackY(l)), SectionPos.mask(BlockPos.unpackZ(l)));
    }

    @Override
    protected void setLevel(long l, int n) {
        ((SectionLightStorage)this.storage).setLight(l, Math.min(15, 15 - n));
    }

    @Override
    protected int getEdgeLevel(long l, long l2, int n) {
        return 1;
    }

    public boolean func_215619_a() {
        return this.needsUpdate() || ((LevelBasedGraph)this.storage).needsUpdate() || ((SectionLightStorage)this.storage).hasSectionsToUpdate();
    }

    public int tick(int n, boolean bl, boolean bl2) {
        if (!this.field_215629_e) {
            if (((LevelBasedGraph)this.storage).needsUpdate() && (n = ((LevelBasedGraph)this.storage).processUpdates(n)) == 0) {
                return n;
            }
            ((SectionLightStorage)this.storage).updateSections(this, bl, bl2);
        }
        this.field_215629_e = true;
        if (this.needsUpdate()) {
            n = this.processUpdates(n);
            this.invalidateCaches();
            if (n == 0) {
                return n;
            }
        }
        this.field_215629_e = false;
        ((SectionLightStorage)this.storage).updateAndNotify();
        return n;
    }

    protected void setData(long l, @Nullable NibbleArray nibbleArray, boolean bl) {
        ((SectionLightStorage)this.storage).setData(l, nibbleArray, bl);
    }

    @Override
    @Nullable
    public NibbleArray getData(SectionPos sectionPos) {
        return ((SectionLightStorage)this.storage).getArray(sectionPos.asLong());
    }

    @Override
    public int getLightFor(BlockPos blockPos) {
        return ((SectionLightStorage)this.storage).getLightOrDefault(blockPos.toLong());
    }

    public String getDebugString(long l) {
        return "" + ((SectionLightStorage)this.storage).getLevel(l);
    }

    public void checkLight(BlockPos blockPos) {
        long l = blockPos.toLong();
        this.scheduleUpdate(l);
        for (Direction direction : DIRECTIONS) {
            this.scheduleUpdate(BlockPos.offset(l, direction));
        }
    }

    public void func_215623_a(BlockPos blockPos, int n) {
    }

    @Override
    public void updateSectionStatus(SectionPos sectionPos, boolean bl) {
        ((SectionLightStorage)this.storage).updateSectionStatus(sectionPos.asLong(), bl);
    }

    public void func_215620_a(ChunkPos chunkPos, boolean bl) {
        long l = SectionPos.toSectionColumnPos(SectionPos.asLong(chunkPos.x, 0, chunkPos.z));
        ((SectionLightStorage)this.storage).setColumnEnabled(l, bl);
    }

    public void retainChunkData(ChunkPos chunkPos, boolean bl) {
        long l = SectionPos.toSectionColumnPos(SectionPos.asLong(chunkPos.x, 0, chunkPos.z));
        ((SectionLightStorage)this.storage).retainChunkData(l, bl);
    }
}

