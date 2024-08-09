/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public final class ForwardingIterator<T>
implements Iterable<T> {
    private final Supplier<Iterator<T>> iterator;
    private final Supplier<Spliterator<T>> spliterator;

    public ForwardingIterator(@NotNull Supplier<Iterator<T>> supplier, @NotNull Supplier<Spliterator<T>> supplier2) {
        this.iterator = Objects.requireNonNull(supplier, "iterator");
        this.spliterator = Objects.requireNonNull(supplier2, "spliterator");
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return this.iterator.get();
    }

    @Override
    @NotNull
    public Spliterator<T> spliterator() {
        return this.spliterator.get();
    }
}

