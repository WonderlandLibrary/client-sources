/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

@GwtCompatible(serializable=true, emulated=true)
final class ImmutableEnumSet<E extends Enum<E>>
extends ImmutableSet<E> {
    private final transient EnumSet<E> delegate;
    @LazyInit
    private transient int hashCode;

    static ImmutableSet asImmutable(EnumSet enumSet) {
        switch (enumSet.size()) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                return ImmutableSet.of(Iterables.getOnlyElement(enumSet));
            }
        }
        return new ImmutableEnumSet(enumSet);
    }

    private ImmutableEnumSet(EnumSet<E> enumSet) {
        this.delegate = enumSet;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.delegate.iterator());
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.delegate.spliterator();
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        this.delegate.forEach(consumer);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ImmutableEnumSet) {
            collection = ((ImmutableEnumSet)collection).delegate;
        }
        return this.delegate.containsAll(collection);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean equals(Object enumSet) {
        if (enumSet == this) {
            return false;
        }
        if (enumSet instanceof ImmutableEnumSet) {
            enumSet = ((ImmutableEnumSet)((Object)enumSet)).delegate;
        }
        return this.delegate.equals(enumSet);
    }

    @Override
    boolean isHashCodeFast() {
        return false;
    }

    @Override
    public int hashCode() {
        int n = this.hashCode;
        return n == 0 ? (this.hashCode = this.delegate.hashCode()) : n;
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    Object writeReplace() {
        return new EnumSerializedForm<E>(this.delegate);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    ImmutableEnumSet(EnumSet enumSet, 1 var2_2) {
        this(enumSet);
    }

    private static class EnumSerializedForm<E extends Enum<E>>
    implements Serializable {
        final EnumSet<E> delegate;
        private static final long serialVersionUID = 0L;

        EnumSerializedForm(EnumSet<E> enumSet) {
            this.delegate = enumSet;
        }

        Object readResolve() {
            return new ImmutableEnumSet((EnumSet)this.delegate.clone(), null);
        }
    }
}

