/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.concurrent.ITaskQueue;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.RegionFileCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOWorker
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicBoolean field_227082_c_ = new AtomicBoolean();
    private final DelegatedTaskExecutor<ITaskQueue.RunnableWithPriority> field_235969_c_;
    private final RegionFileCache field_227084_e_;
    private final Map<ChunkPos, Entry> field_227085_f_ = Maps.newLinkedHashMap();

    protected IOWorker(File file, boolean bl, String string) {
        this.field_227084_e_ = new RegionFileCache(file, bl);
        this.field_235969_c_ = new DelegatedTaskExecutor<ITaskQueue.RunnableWithPriority>(new ITaskQueue.Priority(Priority.values().length), Util.getRenderingService(), "IOWorker-" + string);
    }

    public CompletableFuture<Void> func_227093_a_(ChunkPos chunkPos, CompoundNBT compoundNBT) {
        return this.func_235975_a_(() -> this.lambda$func_227093_a_$1(chunkPos, compoundNBT)).thenCompose(Function.identity());
    }

    @Nullable
    public CompoundNBT func_227090_a_(ChunkPos chunkPos) throws IOException {
        CompletableFuture completableFuture = this.func_235975_a_(() -> this.lambda$func_227090_a_$2(chunkPos));
        try {
            return (CompoundNBT)completableFuture.join();
        } catch (CompletionException completionException) {
            if (completionException.getCause() instanceof IOException) {
                throw (IOException)completionException.getCause();
            }
            throw completionException;
        }
    }

    public CompletableFuture<Void> func_227088_a_() {
        CompletionStage completionStage = this.func_235975_a_(this::lambda$func_227088_a_$5).thenCompose(Function.identity());
        return ((CompletableFuture)completionStage).thenCompose(this::lambda$func_227088_a_$7);
    }

    private <T> CompletableFuture<T> func_235975_a_(Supplier<Either<T, Exception>> supplier) {
        return this.field_235969_c_.func_233528_c_(arg_0 -> this.lambda$func_235975_a_$9(supplier, arg_0));
    }

    private void func_235978_b_() {
        Iterator<Map.Entry<ChunkPos, Entry>> iterator2 = this.field_227085_f_.entrySet().iterator();
        if (iterator2.hasNext()) {
            Map.Entry<ChunkPos, Entry> entry = iterator2.next();
            iterator2.remove();
            this.func_227091_a_(entry.getKey(), entry.getValue());
            this.func_235982_c_();
        }
    }

    private void func_235982_c_() {
        this.field_235969_c_.enqueue(new ITaskQueue.RunnableWithPriority(Priority.LOW.ordinal(), this::func_235978_b_));
    }

    private void func_227091_a_(ChunkPos chunkPos, Entry entry) {
        try {
            this.field_227084_e_.writeChunk(chunkPos, entry.field_227113_a_);
            entry.field_227114_b_.complete(null);
        } catch (Exception exception) {
            LOGGER.error("Failed to store chunk {}", (Object)chunkPos, (Object)exception);
            entry.field_227114_b_.completeExceptionally(exception);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.field_227082_c_.compareAndSet(false, false)) {
            CompletableFuture completableFuture = this.field_235969_c_.func_213141_a(IOWorker::lambda$close$11);
            try {
                completableFuture.join();
            } catch (CompletionException completionException) {
                if (completionException.getCause() instanceof IOException) {
                    throw (IOException)completionException.getCause();
                }
                throw completionException;
            }
            this.field_235969_c_.close();
            this.field_227085_f_.forEach(this::func_227091_a_);
            this.field_227085_f_.clear();
            try {
                this.field_227084_e_.close();
            } catch (Exception exception) {
                LOGGER.error("Failed to close storage", (Throwable)exception);
            }
        }
    }

    private static ITaskQueue.RunnableWithPriority lambda$close$11(ITaskExecutor iTaskExecutor) {
        return new ITaskQueue.RunnableWithPriority(Priority.HIGH.ordinal(), () -> IOWorker.lambda$close$10(iTaskExecutor));
    }

    private static void lambda$close$10(ITaskExecutor iTaskExecutor) {
        iTaskExecutor.enqueue(Unit.INSTANCE);
    }

    private ITaskQueue.RunnableWithPriority lambda$func_235975_a_$9(Supplier supplier, ITaskExecutor iTaskExecutor) {
        return new ITaskQueue.RunnableWithPriority(Priority.HIGH.ordinal(), () -> this.lambda$func_235975_a_$8(iTaskExecutor, (Supplier)supplier));
    }

    private void lambda$func_235975_a_$8(ITaskExecutor iTaskExecutor, Supplier supplier) {
        if (!this.field_227082_c_.get()) {
            iTaskExecutor.enqueue((Either)supplier.get());
        }
        this.func_235982_c_();
    }

    private CompletionStage lambda$func_227088_a_$7(Void void_) {
        return this.func_235975_a_(this::lambda$func_227088_a_$6);
    }

    private Either lambda$func_227088_a_$6() {
        try {
            this.field_227084_e_.func_235987_a_();
            return Either.left(null);
        } catch (Exception exception) {
            LOGGER.warn("Failed to synchronized chunks", (Throwable)exception);
            return Either.right(exception);
        }
    }

    private Either lambda$func_227088_a_$5() {
        return Either.left(CompletableFuture.allOf((CompletableFuture[])this.field_227085_f_.values().stream().map(IOWorker::lambda$func_227088_a_$3).toArray(IOWorker::lambda$func_227088_a_$4)));
    }

    private static CompletableFuture[] lambda$func_227088_a_$4(int n) {
        return new CompletableFuture[n];
    }

    private static CompletableFuture lambda$func_227088_a_$3(Entry entry) {
        return entry.field_227114_b_;
    }

    private Either lambda$func_227090_a_$2(ChunkPos chunkPos) {
        Entry entry = this.field_227085_f_.get(chunkPos);
        if (entry != null) {
            return Either.left(entry.field_227113_a_);
        }
        try {
            CompoundNBT compoundNBT = this.field_227084_e_.readChunk(chunkPos);
            return Either.left(compoundNBT);
        } catch (Exception exception) {
            LOGGER.warn("Failed to read chunk {}", (Object)chunkPos, (Object)exception);
            return Either.right(exception);
        }
    }

    private Either lambda$func_227093_a_$1(ChunkPos chunkPos, CompoundNBT compoundNBT) {
        Entry entry = this.field_227085_f_.computeIfAbsent(chunkPos, arg_0 -> IOWorker.lambda$func_227093_a_$0(compoundNBT, arg_0));
        entry.field_227113_a_ = compoundNBT;
        return Either.left(entry.field_227114_b_);
    }

    private static Entry lambda$func_227093_a_$0(CompoundNBT compoundNBT, ChunkPos chunkPos) {
        return new Entry(compoundNBT);
    }

    static enum Priority {
        HIGH,
        LOW;

    }

    static class Entry {
        private CompoundNBT field_227113_a_;
        private final CompletableFuture<Void> field_227114_b_ = new CompletableFuture();

        public Entry(CompoundNBT compoundNBT) {
            this.field_227113_a_ = compoundNBT;
        }
    }
}

