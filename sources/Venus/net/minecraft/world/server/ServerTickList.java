/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ITickList;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.TickPriority;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class ServerTickList<T>
implements ITickList<T> {
    protected final Predicate<T> filter;
    private final Function<T, ResourceLocation> serializer;
    private final Set<NextTickListEntry<T>> pendingTickListEntriesHashSet = Sets.newHashSet();
    private final TreeSet<NextTickListEntry<T>> pendingTickListEntriesTreeSet = Sets.newTreeSet(NextTickListEntry.func_223192_a());
    private final ServerWorld world;
    private final Queue<NextTickListEntry<T>> pendingTickListEntriesThisTick = Queues.newArrayDeque();
    private final List<NextTickListEntry<T>> entriesRunThisTick = Lists.newArrayList();
    private final Consumer<NextTickListEntry<T>> tickFunction;

    public ServerTickList(ServerWorld serverWorld, Predicate<T> predicate, Function<T, ResourceLocation> function, Consumer<NextTickListEntry<T>> consumer) {
        this.filter = predicate;
        this.serializer = function;
        this.world = serverWorld;
        this.tickFunction = consumer;
    }

    public void tick() {
        NextTickListEntry<T> nextTickListEntry;
        int n = this.pendingTickListEntriesTreeSet.size();
        if (n != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (n > 65536) {
            n = 65536;
        }
        ServerChunkProvider serverChunkProvider = this.world.getChunkProvider();
        Iterator<NextTickListEntry<T>> iterator2 = this.pendingTickListEntriesTreeSet.iterator();
        this.world.getProfiler().startSection("cleaning");
        while (n > 0 && iterator2.hasNext()) {
            nextTickListEntry = iterator2.next();
            if (nextTickListEntry.field_235017_b_ > this.world.getGameTime()) break;
            if (!serverChunkProvider.canTick(nextTickListEntry.position)) continue;
            iterator2.remove();
            this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
            this.pendingTickListEntriesThisTick.add(nextTickListEntry);
            --n;
        }
        this.world.getProfiler().endStartSection("ticking");
        while ((nextTickListEntry = this.pendingTickListEntriesThisTick.poll()) != null) {
            if (serverChunkProvider.canTick(nextTickListEntry.position)) {
                try {
                    this.entriesRunThisTick.add(nextTickListEntry);
                    this.tickFunction.accept(nextTickListEntry);
                    continue;
                } catch (Throwable throwable) {
                    CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Exception while ticking");
                    CrashReportCategory crashReportCategory = crashReport.makeCategory("Block being ticked");
                    CrashReportCategory.addBlockInfo(crashReportCategory, nextTickListEntry.position, null);
                    throw new ReportedException(crashReport);
                }
            }
            this.scheduleTick(nextTickListEntry.position, nextTickListEntry.getTarget(), 0);
        }
        this.world.getProfiler().endSection();
        this.entriesRunThisTick.clear();
        this.pendingTickListEntriesThisTick.clear();
    }

    @Override
    public boolean isTickPending(BlockPos blockPos, T t) {
        return this.pendingTickListEntriesThisTick.contains(new NextTickListEntry<T>(blockPos, t));
    }

    public List<NextTickListEntry<T>> getPending(ChunkPos chunkPos, boolean bl, boolean bl2) {
        int n = (chunkPos.x << 4) - 2;
        int n2 = n + 16 + 2;
        int n3 = (chunkPos.z << 4) - 2;
        int n4 = n3 + 16 + 2;
        return this.getPending(new MutableBoundingBox(n, 0, n3, n2, 256, n4), bl, bl2);
    }

    public List<NextTickListEntry<T>> getPending(MutableBoundingBox mutableBoundingBox, boolean bl, boolean bl2) {
        List<NextTickListEntry<T>> list = this.getEntries(null, this.pendingTickListEntriesTreeSet, mutableBoundingBox, bl);
        if (bl && list != null) {
            this.pendingTickListEntriesHashSet.removeAll(list);
        }
        list = this.getEntries(list, this.pendingTickListEntriesThisTick, mutableBoundingBox, bl);
        if (!bl2) {
            list = this.getEntries(list, this.entriesRunThisTick, mutableBoundingBox, bl);
        }
        return list == null ? Collections.emptyList() : list;
    }

    @Nullable
    private List<NextTickListEntry<T>> getEntries(@Nullable List<NextTickListEntry<T>> list, Collection<NextTickListEntry<T>> collection, MutableBoundingBox mutableBoundingBox, boolean bl) {
        Iterator<NextTickListEntry<T>> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            NextTickListEntry<T> nextTickListEntry = iterator2.next();
            BlockPos blockPos = nextTickListEntry.position;
            if (blockPos.getX() < mutableBoundingBox.minX || blockPos.getX() >= mutableBoundingBox.maxX || blockPos.getZ() < mutableBoundingBox.minZ || blockPos.getZ() >= mutableBoundingBox.maxZ) continue;
            if (bl) {
                iterator2.remove();
            }
            if (list == null) {
                list = Lists.newArrayList();
            }
            list.add(nextTickListEntry);
        }
        return list;
    }

    public void copyTicks(MutableBoundingBox mutableBoundingBox, BlockPos blockPos) {
        for (NextTickListEntry<T> nextTickListEntry : this.getPending(mutableBoundingBox, false, true)) {
            if (!mutableBoundingBox.isVecInside(nextTickListEntry.position)) continue;
            BlockPos blockPos2 = nextTickListEntry.position.add(blockPos);
            T t = nextTickListEntry.getTarget();
            this.addEntry(new NextTickListEntry<T>(blockPos2, t, nextTickListEntry.field_235017_b_, nextTickListEntry.priority));
        }
    }

    public ListNBT func_219503_a(ChunkPos chunkPos) {
        List<NextTickListEntry<T>> list = this.getPending(chunkPos, false, false);
        return ServerTickList.func_219502_a(this.serializer, list, this.world.getGameTime());
    }

    private static <T> ListNBT func_219502_a(Function<T, ResourceLocation> function, Iterable<NextTickListEntry<T>> iterable, long l) {
        ListNBT listNBT = new ListNBT();
        for (NextTickListEntry<T> nextTickListEntry : iterable) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("i", function.apply(nextTickListEntry.getTarget()).toString());
            compoundNBT.putInt("x", nextTickListEntry.position.getX());
            compoundNBT.putInt("y", nextTickListEntry.position.getY());
            compoundNBT.putInt("z", nextTickListEntry.position.getZ());
            compoundNBT.putInt("t", (int)(nextTickListEntry.field_235017_b_ - l));
            compoundNBT.putInt("p", nextTickListEntry.priority.getPriority());
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    @Override
    public boolean isTickScheduled(BlockPos blockPos, T t) {
        return this.pendingTickListEntriesHashSet.contains(new NextTickListEntry<T>(blockPos, t));
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n, TickPriority tickPriority) {
        if (!this.filter.test(t)) {
            this.addEntry(new NextTickListEntry<T>(blockPos, t, (long)n + this.world.getGameTime(), tickPriority));
        }
    }

    private void addEntry(NextTickListEntry<T> nextTickListEntry) {
        if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
            this.pendingTickListEntriesHashSet.add(nextTickListEntry);
            this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
        }
    }

    public int func_225420_a() {
        return this.pendingTickListEntriesHashSet.size();
    }
}

