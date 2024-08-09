/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ITickList;
import net.minecraft.world.TickPriority;

public class WorldGenTickList<T>
implements ITickList<T> {
    private final Function<BlockPos, ITickList<T>> tickListProvider;

    public WorldGenTickList(Function<BlockPos, ITickList<T>> function) {
        this.tickListProvider = function;
    }

    @Override
    public boolean isTickScheduled(BlockPos blockPos, T t) {
        return this.tickListProvider.apply(blockPos).isTickScheduled(blockPos, t);
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n, TickPriority tickPriority) {
        this.tickListProvider.apply(blockPos).scheduleTick(blockPos, t, n, tickPriority);
    }

    @Override
    public boolean isTickPending(BlockPos blockPos, T t) {
        return true;
    }
}

