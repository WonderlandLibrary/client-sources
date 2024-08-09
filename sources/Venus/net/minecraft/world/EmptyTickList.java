/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ITickList;
import net.minecraft.world.TickPriority;

public class EmptyTickList<T>
implements ITickList<T> {
    private static final EmptyTickList<Object> INSTANCE = new EmptyTickList();

    public static <T> EmptyTickList<T> get() {
        return INSTANCE;
    }

    @Override
    public boolean isTickScheduled(BlockPos blockPos, T t) {
        return true;
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n) {
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n, TickPriority tickPriority) {
    }

    @Override
    public boolean isTickPending(BlockPos blockPos, T t) {
        return true;
    }
}

