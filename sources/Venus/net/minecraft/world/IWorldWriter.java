/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IWorldWriter {
    public boolean setBlockState(BlockPos var1, BlockState var2, int var3, int var4);

    default public boolean setBlockState(BlockPos blockPos, BlockState blockState, int n) {
        return this.setBlockState(blockPos, blockState, n, 512);
    }

    public boolean removeBlock(BlockPos var1, boolean var2);

    default public boolean destroyBlock(BlockPos blockPos, boolean bl) {
        return this.destroyBlock(blockPos, bl, null);
    }

    default public boolean destroyBlock(BlockPos blockPos, boolean bl, @Nullable Entity entity2) {
        return this.destroyBlock(blockPos, bl, entity2, 512);
    }

    public boolean destroyBlock(BlockPos var1, boolean var2, @Nullable Entity var3, int var4);

    default public boolean addEntity(Entity entity2) {
        return true;
    }
}

