/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CachedBlockInfo {
    private final IWorldReader world;
    private final BlockPos pos;
    private final boolean forceLoad;
    private BlockState state;
    private TileEntity tileEntity;
    private boolean tileEntityInitialized;

    public CachedBlockInfo(IWorldReader iWorldReader, BlockPos blockPos, boolean bl) {
        this.world = iWorldReader;
        this.pos = blockPos.toImmutable();
        this.forceLoad = bl;
    }

    public BlockState getBlockState() {
        if (this.state == null && (this.forceLoad || this.world.isBlockLoaded(this.pos))) {
            this.state = this.world.getBlockState(this.pos);
        }
        return this.state;
    }

    @Nullable
    public TileEntity getTileEntity() {
        if (this.tileEntity == null && !this.tileEntityInitialized) {
            this.tileEntity = this.world.getTileEntity(this.pos);
            this.tileEntityInitialized = true;
        }
        return this.tileEntity;
    }

    public IWorldReader getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public static Predicate<CachedBlockInfo> hasState(Predicate<BlockState> predicate) {
        return arg_0 -> CachedBlockInfo.lambda$hasState$0(predicate, arg_0);
    }

    private static boolean lambda$hasState$0(Predicate predicate, CachedBlockInfo cachedBlockInfo) {
        return cachedBlockInfo != null && predicate.test(cachedBlockInfo.getBlockState());
    }
}

