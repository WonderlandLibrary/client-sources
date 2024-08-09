/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ITaskExecutor<Msg>
extends AutoCloseable {
    public String getName();

    public void enqueue(Msg var1);

    @Override
    default public void close() {
    }

    default public <Source> CompletableFuture<Source> func_213141_a(Function<? super ITaskExecutor<Source>, ? extends Msg> function) {
        CompletableFuture completableFuture = new CompletableFuture();
        Msg Msg2 = function.apply(ITaskExecutor.inline("ask future procesor handle", completableFuture::complete));
        this.enqueue(Msg2);
        return completableFuture;
    }

    default public <Source> CompletableFuture<Source> func_233528_c_(Function<? super ITaskExecutor<Either<Source, Exception>>, ? extends Msg> function) {
        CompletableFuture completableFuture = new CompletableFuture();
        Msg Msg2 = function.apply(ITaskExecutor.inline("ask future procesor handle", arg_0 -> ITaskExecutor.lambda$func_233528_c_$0(completableFuture, arg_0)));
        this.enqueue(Msg2);
        return completableFuture;
    }

    public static <Msg> ITaskExecutor<Msg> inline(String string, Consumer<Msg> consumer) {
        return new ITaskExecutor<Msg>(){
            final String val$name;
            final Consumer val$p_213140_1_;
            {
                this.val$name = string;
                this.val$p_213140_1_ = consumer;
            }

            @Override
            public String getName() {
                return this.val$name;
            }

            @Override
            public void enqueue(Msg Msg2) {
                this.val$p_213140_1_.accept(Msg2);
            }

            public String toString() {
                return this.val$name;
            }
        };
    }

    private static void lambda$func_233528_c_$0(CompletableFuture completableFuture, Either either) {
        either.ifLeft(completableFuture::complete);
        either.ifRight(completableFuture::completeExceptionally);
    }
}

