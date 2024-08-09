/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunk;

public class Region
implements IBlockReader,
ICollisionReader {
    protected final int chunkX;
    protected final int chunkZ;
    protected final IChunk[][] chunks;
    protected boolean empty;
    protected final World world;

    public Region(World world, BlockPos blockPos, BlockPos blockPos2) {
        int n;
        int n2;
        this.world = world;
        this.chunkX = blockPos.getX() >> 4;
        this.chunkZ = blockPos.getZ() >> 4;
        int n3 = blockPos2.getX() >> 4;
        int n4 = blockPos2.getZ() >> 4;
        this.chunks = new IChunk[n3 - this.chunkX + 1][n4 - this.chunkZ + 1];
        AbstractChunkProvider abstractChunkProvider = world.getChunkProvider();
        this.empty = true;
        for (n2 = this.chunkX; n2 <= n3; ++n2) {
            for (n = this.chunkZ; n <= n4; ++n) {
                this.chunks[n2 - this.chunkX][n - this.chunkZ] = abstractChunkProvider.getChunkNow(n2, n);
            }
        }
        for (n2 = blockPos.getX() >> 4; n2 <= blockPos2.getX() >> 4; ++n2) {
            for (n = blockPos.getZ() >> 4; n <= blockPos2.getZ() >> 4; ++n) {
                IChunk iChunk = this.chunks[n2 - this.chunkX][n - this.chunkZ];
                if (iChunk == null || iChunk.isEmptyBetween(blockPos.getY(), blockPos2.getY())) continue;
                this.empty = false;
                return;
            }
        }
    }

    private IChunk getChunk(BlockPos blockPos) {
        return this.getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    private IChunk getChunk(int n, int n2) {
        int n3 = n - this.chunkX;
        int n4 = n2 - this.chunkZ;
        if (n3 >= 0 && n3 < this.chunks.length && n4 >= 0 && n4 < this.chunks[n3].length) {
            IChunk iChunk = this.chunks[n3][n4];
            return iChunk != null ? iChunk : new EmptyChunk(this.world, new ChunkPos(n, n2));
        }
        return new EmptyChunk(this.world, new ChunkPos(n, n2));
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.world.getWorldBorder();
    }

    @Override
    public IBlockReader getBlockReader(int n, int n2) {
        return this.getChunk(n, n2);
    }

    @Override
    @Nullable
    public TileEntity getTileEntity(BlockPos blockPos) {
        IChunk iChunk = this.getChunk(blockPos);
        return iChunk.getTileEntity(blockPos);
    }

    @Override
    public BlockState getBlockState(BlockPos blockPos) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        IChunk iChunk = this.getChunk(blockPos);
        return iChunk.getBlockState(blockPos);
    }

    @Override
    public Stream<VoxelShape> func_230318_c_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        return Stream.empty();
    }

    @Override
    public Stream<VoxelShape> func_234867_d_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        return this.getCollisionShapes(entity2, axisAlignedBB);
    }

    @Override
    public FluidState getFluidState(BlockPos blockPos) {
        if (World.isOutsideBuildHeight(blockPos)) {
            return Fluids.EMPTY.getDefaultState();
        }
        IChunk iChunk = this.getChunk(blockPos);
        return iChunk.getFluidState(blockPos);
    }
}

