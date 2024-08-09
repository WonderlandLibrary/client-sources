/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ChunkManager;

public class ChunkTaskPriorityQueue<T> {
    public static final int MAX_LOADED_LEVELS = ChunkManager.MAX_LOADED_LEVEL + 2 + 32;
    private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> chunkPriorityQueue = IntStream.range(0, MAX_LOADED_LEVELS).mapToObj(ChunkTaskPriorityQueue::lambda$new$0).collect(Collectors.toList());
    private volatile int maxLoaded = MAX_LOADED_LEVELS;
    private final String queueName;
    private final LongSet loadedChunks = new LongOpenHashSet();
    private final int priority;

    public ChunkTaskPriorityQueue(String string, int n) {
        this.queueName = string;
        this.priority = n;
    }

    protected void func_219407_a(int n, ChunkPos chunkPos, int n2) {
        if (n < MAX_LOADED_LEVELS) {
            Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap = this.chunkPriorityQueue.get(n);
            List<Optional<T>> list = long2ObjectLinkedOpenHashMap.remove(chunkPos.asLong());
            if (n == this.maxLoaded) {
                while (this.maxLoaded < MAX_LOADED_LEVELS && this.chunkPriorityQueue.get(this.maxLoaded).isEmpty()) {
                    ++this.maxLoaded;
                }
            }
            if (list != null && !list.isEmpty()) {
                this.chunkPriorityQueue.get(n2).computeIfAbsent(chunkPos.asLong(), ChunkTaskPriorityQueue::lambda$func_219407_a$1).addAll(list);
                this.maxLoaded = Math.min(this.maxLoaded, n2);
            }
        }
    }

    protected void addTaskToChunk(Optional<T> optional, long l, int n) {
        this.chunkPriorityQueue.get(n).computeIfAbsent(l, ChunkTaskPriorityQueue::lambda$addTaskToChunk$2).add(optional);
        this.maxLoaded = Math.min(this.maxLoaded, n);
    }

    protected void clearChunkFromQueue(long l, boolean bl) {
        for (Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap : this.chunkPriorityQueue) {
            List<Optional<T>> list = long2ObjectLinkedOpenHashMap.get(l);
            if (list == null) continue;
            if (bl) {
                list.clear();
            } else {
                list.removeIf(ChunkTaskPriorityQueue::lambda$clearChunkFromQueue$3);
            }
            if (!list.isEmpty()) continue;
            long2ObjectLinkedOpenHashMap.remove(l);
        }
        while (this.maxLoaded < MAX_LOADED_LEVELS && this.chunkPriorityQueue.get(this.maxLoaded).isEmpty()) {
            ++this.maxLoaded;
        }
        this.loadedChunks.remove(l);
    }

    private Runnable func_219418_a(long l) {
        return () -> this.lambda$func_219418_a$4(l);
    }

    @Nullable
    public Stream<Object> func_219417_a() {
        if (this.loadedChunks.size() >= this.priority) {
            return null;
        }
        if (this.maxLoaded >= MAX_LOADED_LEVELS) {
            return null;
        }
        int n = this.maxLoaded;
        Long2ObjectLinkedOpenHashMap<List<Optional<T>>> long2ObjectLinkedOpenHashMap = this.chunkPriorityQueue.get(n);
        long l = long2ObjectLinkedOpenHashMap.firstLongKey();
        List<Optional<T>> list = long2ObjectLinkedOpenHashMap.removeFirst();
        while (this.maxLoaded < MAX_LOADED_LEVELS && this.chunkPriorityQueue.get(this.maxLoaded).isEmpty()) {
            ++this.maxLoaded;
        }
        return list.stream().map(arg_0 -> this.lambda$func_219417_a$6(l, arg_0));
    }

    public String toString() {
        return this.queueName + " " + this.maxLoaded + "...";
    }

    @VisibleForTesting
    LongSet getLoadedChunks() {
        return new LongOpenHashSet(this.loadedChunks);
    }

    private Object lambda$func_219417_a$6(long l, Optional optional) {
        return optional.map(Either::left).orElseGet(() -> this.lambda$func_219417_a$5(l));
    }

    private Either lambda$func_219417_a$5(long l) {
        return Either.right(this.func_219418_a(l));
    }

    private void lambda$func_219418_a$4(long l) {
        this.loadedChunks.add(l);
    }

    private static boolean lambda$clearChunkFromQueue$3(Optional optional) {
        return !optional.isPresent();
    }

    private static List lambda$addTaskToChunk$2(long l) {
        return Lists.newArrayList();
    }

    private static List lambda$func_219407_a$1(long l) {
        return Lists.newArrayList();
    }

    private static Long2ObjectLinkedOpenHashMap lambda$new$0(int n) {
        return new Long2ObjectLinkedOpenHashMap();
    }
}

