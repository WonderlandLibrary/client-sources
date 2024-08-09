/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jagrosh.discordipc.entities;

import com.jagrosh.discordipc.entities.Packet;
import java.util.function.Consumer;

public class Callback {
    private final Consumer<Packet> success;
    private final Consumer<String> failure;

    public Callback() {
        this((Consumer<Packet>)null, null);
    }

    public Callback(Consumer<Packet> consumer) {
        this(consumer, null);
    }

    public Callback(Consumer<Packet> consumer, Consumer<String> consumer2) {
        this.success = consumer;
        this.failure = consumer2;
    }

    @Deprecated
    public Callback(Runnable runnable, Consumer<String> consumer) {
        this(arg_0 -> Callback.lambda$new$0(runnable, arg_0), consumer);
    }

    @Deprecated
    public Callback(Runnable runnable) {
        this(arg_0 -> Callback.lambda$new$1(runnable, arg_0), null);
    }

    public boolean isEmpty() {
        return this.success == null && this.failure == null;
    }

    public void succeed(Packet packet) {
        if (this.success != null) {
            this.success.accept(packet);
        }
    }

    public void fail(String string) {
        if (this.failure != null) {
            this.failure.accept(string);
        }
    }

    private static void lambda$new$1(Runnable runnable, Packet packet) {
        runnable.run();
    }

    private static void lambda$new$0(Runnable runnable, Packet packet) {
        runnable.run();
    }
}

