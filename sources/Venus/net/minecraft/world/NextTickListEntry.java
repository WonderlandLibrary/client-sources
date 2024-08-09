/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Comparator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TickPriority;

public class NextTickListEntry<T> {
    private static long nextTickEntryID;
    private final T target;
    public final BlockPos position;
    public final long field_235017_b_;
    public final TickPriority priority;
    private final long tickEntryID = nextTickEntryID++;

    public NextTickListEntry(BlockPos blockPos, T t) {
        this(blockPos, t, 0L, TickPriority.NORMAL);
    }

    public NextTickListEntry(BlockPos blockPos, T t, long l, TickPriority tickPriority) {
        this.position = blockPos.toImmutable();
        this.target = t;
        this.field_235017_b_ = l;
        this.priority = tickPriority;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return true;
        }
        NextTickListEntry nextTickListEntry = (NextTickListEntry)object;
        return this.position.equals(nextTickListEntry.position) && this.target == nextTickListEntry.target;
    }

    public int hashCode() {
        return this.position.hashCode();
    }

    public static <T> Comparator<NextTickListEntry<T>> func_223192_a() {
        return Comparator.comparingLong(NextTickListEntry::lambda$func_223192_a$0).thenComparing(NextTickListEntry::lambda$func_223192_a$1).thenComparingLong(NextTickListEntry::lambda$func_223192_a$2);
    }

    public String toString() {
        return this.target + ": " + this.position + ", " + this.field_235017_b_ + ", " + this.priority + ", " + this.tickEntryID;
    }

    public T getTarget() {
        return this.target;
    }

    private static long lambda$func_223192_a$2(NextTickListEntry nextTickListEntry) {
        return nextTickListEntry.tickEntryID;
    }

    private static TickPriority lambda$func_223192_a$1(NextTickListEntry nextTickListEntry) {
        return nextTickListEntry.priority;
    }

    private static long lambda$func_223192_a$0(NextTickListEntry nextTickListEntry) {
        return nextTickListEntry.field_235017_b_;
    }
}

