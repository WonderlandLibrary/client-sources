/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public abstract class Listenable<L> {
    private final List<L> listeners = new CopyOnWriteArrayList<L>();

    protected final void forEachListener(@NotNull Consumer<L> consumer) {
        for (L l : this.listeners) {
            consumer.accept(l);
        }
    }

    protected final void addListener0(@NotNull L l) {
        this.listeners.add(l);
    }

    protected final void removeListener0(@NotNull L l) {
        this.listeners.remove(l);
    }
}

