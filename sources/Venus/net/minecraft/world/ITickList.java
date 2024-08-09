/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TickPriority;

public interface ITickList<T> {
    public boolean isTickScheduled(BlockPos var1, T var2);

    default public void scheduleTick(BlockPos blockPos, T t, int n) {
        this.scheduleTick(blockPos, t, n, TickPriority.NORMAL);
    }

    public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4);

    public boolean isTickPending(BlockPos var1, T var2);
}

