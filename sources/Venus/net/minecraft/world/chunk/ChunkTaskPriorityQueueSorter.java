/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.concurrent.ITaskQueue;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkTaskPriorityQueue;
import net.minecraft.world.server.ChunkHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskPriorityQueueSorter
implements AutoCloseable,
ChunkHolder.IListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ITaskExecutor<?>, ChunkTaskPriorityQueue<? extends Function<ITaskExecutor<Unit>, ?>>> queues;
    private final Set<ITaskExecutor<?>> field_219095_c;
    private final DelegatedTaskExecutor<ITaskQueue.RunnableWithPriority> sorter;

    public ChunkTaskPriorityQueueSorter(List<ITaskExecutor<?>> list, Executor executor, int n) {
        this.queues = list.stream().collect(Collectors.toMap(Function.identity(), arg_0 -> ChunkTaskPriorityQueueSorter.lambda$new$0(n, arg_0)));
        this.field_219095_c = Sets.newHashSet(list);
        this.sorter = new DelegatedTaskExecutor<ITaskQueue.RunnableWithPriority>(new ITaskQueue.Priority(4), executor, "sorter");
    }

    public static FunctionEntry<Runnable> func_219069_a(Runnable runnable, long l, IntSupplier intSupplier) {
        return new FunctionEntry<Runnable>(arg_0 -> ChunkTaskPriorityQueueSorter.lambda$func_219069_a$2(runnable, arg_0), l, intSupplier);
    }

    public static FunctionEntry<Runnable> func_219081_a(ChunkHolder chunkHolder, Runnable runnable) {
        return ChunkTaskPriorityQueueSorter.func_219069_a(runnable, chunkHolder.getPosition().asLong(), chunkHolder::func_219281_j);
    }

    public static RunnableEntry func_219073_a(Runnable runnable, long l, boolean bl) {
        return new RunnableEntry(runnable, l, bl);
    }

    public <T> ITaskExecutor<FunctionEntry<T>> func_219087_a(ITaskExecutor<T> iTaskExecutor, boolean bl) {
        return (ITaskExecutor)this.sorter.func_213141_a(arg_0 -> this.lambda$func_219087_a$5(iTaskExecutor, bl, arg_0)).join();
    }

    public ITaskExecutor<RunnableEntry> func_219091_a(ITaskExecutor<Runnable> iTaskExecutor) {
        return (ITaskExecutor)this.sorter.func_213141_a(arg_0 -> this.lambda$func_219091_a$8(iTaskExecutor, arg_0)).join();
    }

    @Override
    public void func_219066_a(ChunkPos chunkPos, IntSupplier intSupplier, int n, IntConsumer intConsumer) {
        this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(0, () -> this.lambda$func_219066_a$10(intSupplier, chunkPos, n, intConsumer)));
    }

    private <T> void func_219074_a(ITaskExecutor<T> iTaskExecutor, long l, Runnable runnable, boolean bl) {
        this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(1, () -> this.lambda$func_219074_a$11(iTaskExecutor, l, bl, runnable)));
    }

    private <T> void func_219067_a(ITaskExecutor<T> iTaskExecutor, Function<ITaskExecutor<Unit>, T> function, long l, IntSupplier intSupplier, boolean bl) {
        this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(2, () -> this.lambda$func_219067_a$12(iTaskExecutor, intSupplier, function, l, bl)));
    }

    private <T> void func_219078_a(ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> chunkTaskPriorityQueue, ITaskExecutor<T> iTaskExecutor) {
        this.sorter.enqueue(new ITaskQueue.RunnableWithPriority(3, () -> this.lambda$func_219078_a$16(chunkTaskPriorityQueue, iTaskExecutor)));
    }

    private <T> ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> getQueue(ITaskExecutor<T> iTaskExecutor) {
        ChunkTaskPriorityQueue<Function<ITaskExecutor<Unit>, T>> chunkTaskPriorityQueue = this.queues.get(iTaskExecutor);
        if (chunkTaskPriorityQueue == null) {
            throw Util.pauseDevMode(new IllegalArgumentException("No queue for: " + iTaskExecutor));
        }
        return chunkTaskPriorityQueue;
    }

    @VisibleForTesting
    public String func_225396_a() {
        return this.queues.entrySet().stream().map(ChunkTaskPriorityQueueSorter::lambda$func_225396_a$18).collect(Collectors.joining(",")) + ", s=" + this.field_219095_c.size();
    }

    @Override
    public void close() {
        this.queues.keySet().forEach(ITaskExecutor::close);
    }

    private static String lambda$func_225396_a$18(Map.Entry entry) {
        return ((ITaskExecutor)entry.getKey()).getName() + "=[" + ((ChunkTaskPriorityQueue)entry.getValue()).getLoadedChunks().stream().map(ChunkTaskPriorityQueueSorter::lambda$func_225396_a$17).collect(Collectors.joining(",")) + "]";
    }

    private static String lambda$func_225396_a$17(Long l) {
        return l + ":" + new ChunkPos(l);
    }

    private void lambda$func_219078_a$16(ChunkTaskPriorityQueue chunkTaskPriorityQueue, ITaskExecutor iTaskExecutor) {
        Stream<Object> stream = chunkTaskPriorityQueue.func_219417_a();
        if (stream == null) {
            this.field_219095_c.add(iTaskExecutor);
        } else {
            Util.gather(stream.map(arg_0 -> ChunkTaskPriorityQueueSorter.lambda$func_219078_a$14(iTaskExecutor, arg_0)).collect(Collectors.toList())).thenAccept(arg_0 -> this.lambda$func_219078_a$15(chunkTaskPriorityQueue, iTaskExecutor, arg_0));
        }
    }

    private void lambda$func_219078_a$15(ChunkTaskPriorityQueue chunkTaskPriorityQueue, ITaskExecutor iTaskExecutor, List list) {
        this.func_219078_a(chunkTaskPriorityQueue, iTaskExecutor);
    }

    private static CompletableFuture lambda$func_219078_a$14(ITaskExecutor iTaskExecutor, Either either) {
        return either.map(iTaskExecutor::func_213141_a, ChunkTaskPriorityQueueSorter::lambda$func_219078_a$13);
    }

    private static CompletableFuture lambda$func_219078_a$13(Runnable runnable) {
        runnable.run();
        return CompletableFuture.completedFuture(Unit.INSTANCE);
    }

    private void lambda$func_219067_a$12(ITaskExecutor iTaskExecutor, IntSupplier intSupplier, Function function, long l, boolean bl) {
        ChunkTaskPriorityQueue chunkTaskPriorityQueue = this.getQueue(iTaskExecutor);
        int n = intSupplier.getAsInt();
        chunkTaskPriorityQueue.addTaskToChunk(Optional.of(function), l, n);
        if (bl) {
            chunkTaskPriorityQueue.addTaskToChunk(Optional.empty(), l, n);
        }
        if (this.field_219095_c.remove(iTaskExecutor)) {
            this.func_219078_a(chunkTaskPriorityQueue, iTaskExecutor);
        }
    }

    private void lambda$func_219074_a$11(ITaskExecutor iTaskExecutor, long l, boolean bl, Runnable runnable) {
        ChunkTaskPriorityQueue chunkTaskPriorityQueue = this.getQueue(iTaskExecutor);
        chunkTaskPriorityQueue.clearChunkFromQueue(l, bl);
        if (this.field_219095_c.remove(iTaskExecutor)) {
            this.func_219078_a(chunkTaskPriorityQueue, iTaskExecutor);
        }
        runnable.run();
    }

    private void lambda$func_219066_a$10(IntSupplier intSupplier, ChunkPos chunkPos, int n, IntConsumer intConsumer) {
        int n2 = intSupplier.getAsInt();
        this.queues.values().forEach(arg_0 -> ChunkTaskPriorityQueueSorter.lambda$func_219066_a$9(n2, chunkPos, n, arg_0));
        intConsumer.accept(n);
    }

    private static void lambda$func_219066_a$9(int n, ChunkPos chunkPos, int n2, ChunkTaskPriorityQueue chunkTaskPriorityQueue) {
        chunkTaskPriorityQueue.func_219407_a(n, chunkPos, n2);
    }

    private ITaskQueue.RunnableWithPriority lambda$func_219091_a$8(ITaskExecutor iTaskExecutor, ITaskExecutor iTaskExecutor2) {
        return new ITaskQueue.RunnableWithPriority(0, () -> this.lambda$func_219091_a$7(iTaskExecutor2, iTaskExecutor));
    }

    private void lambda$func_219091_a$7(ITaskExecutor iTaskExecutor, ITaskExecutor iTaskExecutor2) {
        iTaskExecutor.enqueue(ITaskExecutor.inline("chunk priority sorter around " + iTaskExecutor2.getName(), arg_0 -> this.lambda$func_219091_a$6(iTaskExecutor2, arg_0)));
    }

    private void lambda$func_219091_a$6(ITaskExecutor iTaskExecutor, RunnableEntry runnableEntry) {
        this.func_219074_a(iTaskExecutor, runnableEntry.field_219435_b, runnableEntry.field_219434_a, runnableEntry.field_219436_c);
    }

    private ITaskQueue.RunnableWithPriority lambda$func_219087_a$5(ITaskExecutor iTaskExecutor, boolean bl, ITaskExecutor iTaskExecutor2) {
        return new ITaskQueue.RunnableWithPriority(0, () -> this.lambda$func_219087_a$4(iTaskExecutor, iTaskExecutor2, bl));
    }

    private void lambda$func_219087_a$4(ITaskExecutor iTaskExecutor, ITaskExecutor iTaskExecutor2, boolean bl) {
        this.getQueue(iTaskExecutor);
        iTaskExecutor2.enqueue(ITaskExecutor.inline("chunk priority sorter around " + iTaskExecutor.getName(), arg_0 -> this.lambda$func_219087_a$3(iTaskExecutor, bl, arg_0)));
    }

    private void lambda$func_219087_a$3(ITaskExecutor iTaskExecutor, boolean bl, FunctionEntry functionEntry) {
        this.func_219067_a(iTaskExecutor, functionEntry.task, functionEntry.chunkPos, functionEntry.field_219430_c, bl);
    }

    private static Runnable lambda$func_219069_a$2(Runnable runnable, ITaskExecutor iTaskExecutor) {
        return () -> ChunkTaskPriorityQueueSorter.lambda$func_219069_a$1(runnable, iTaskExecutor);
    }

    private static void lambda$func_219069_a$1(Runnable runnable, ITaskExecutor iTaskExecutor) {
        runnable.run();
        iTaskExecutor.enqueue(Unit.INSTANCE);
    }

    private static ChunkTaskPriorityQueue lambda$new$0(int n, ITaskExecutor iTaskExecutor) {
        return new ChunkTaskPriorityQueue(iTaskExecutor.getName() + "_queue", n);
    }

    public static final class FunctionEntry<T> {
        private final Function<ITaskExecutor<Unit>, T> task;
        private final long chunkPos;
        private final IntSupplier field_219430_c;

        private FunctionEntry(Function<ITaskExecutor<Unit>, T> function, long l, IntSupplier intSupplier) {
            this.task = function;
            this.chunkPos = l;
            this.field_219430_c = intSupplier;
        }
    }

    public static final class RunnableEntry {
        private final Runnable field_219434_a;
        private final long field_219435_b;
        private final boolean field_219436_c;

        private RunnableEntry(Runnable runnable, long l, boolean bl) {
            this.field_219434_a = runnable;
            this.field_219435_b = l;
            this.field_219436_c = bl;
        }
    }
}

