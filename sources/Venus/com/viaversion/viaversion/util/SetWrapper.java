/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SetWrapper<E>
extends ForwardingSet<E> {
    private final Set<E> set;
    private final Consumer<E> addListener;

    public SetWrapper(Set<E> set, Consumer<E> consumer) {
        this.set = set;
        this.addListener = consumer;
    }

    @Override
    public boolean add(@NonNull E e) {
        this.addListener.accept(e);
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        for (E e : collection) {
            this.addListener.accept(e);
        }
        return super.addAll(collection);
    }

    @Override
    protected Set<E> delegate() {
        return this.originalSet();
    }

    public Set<E> originalSet() {
        return this.set;
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

