/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public abstract class Listenable<L> {
    private final List<L> listeners = new CopyOnWriteArrayList<L>();

    protected final void forEachListener(@NotNull Consumer<L> consumer) {
        Iterator<L> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            L listener = iterator.next();
            consumer.accept(listener);
        }
    }

    protected final void addListener0(@NotNull L listener) {
        this.listeners.add(listener);
    }

    protected final void removeListener0(@NotNull L listener) {
        this.listeners.remove(listener);
    }
}

