/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible
public interface Multiset<E>
extends Collection<E> {
    @Override
    public int size();

    public int count(@Nullable @CompatibleWith(value="E") Object var1);

    @CanIgnoreReturnValue
    public int add(@Nullable E var1, int var2);

    @CanIgnoreReturnValue
    public int remove(@Nullable @CompatibleWith(value="E") Object var1, int var2);

    @CanIgnoreReturnValue
    public int setCount(E var1, int var2);

    @CanIgnoreReturnValue
    public boolean setCount(E var1, int var2, int var3);

    public Set<E> elementSet();

    public Set<Entry<E>> entrySet();

    @Beta
    default public void forEachEntry(ObjIntConsumer<? super E> objIntConsumer) {
        Preconditions.checkNotNull(objIntConsumer);
        this.entrySet().forEach(arg_0 -> Multiset.lambda$forEachEntry$0(objIntConsumer, arg_0));
    }

    @Override
    public boolean equals(@Nullable Object var1);

    @Override
    public int hashCode();

    public String toString();

    @Override
    public Iterator<E> iterator();

    @Override
    public boolean contains(@Nullable Object var1);

    @Override
    public boolean containsAll(Collection<?> var1);

    @Override
    @CanIgnoreReturnValue
    public boolean add(E var1);

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object var1);

    @Override
    @CanIgnoreReturnValue
    public boolean removeAll(Collection<?> var1);

    @Override
    @CanIgnoreReturnValue
    public boolean retainAll(Collection<?> var1);

    @Override
    default public void forEach(Consumer<? super E> consumer) {
        Preconditions.checkNotNull(consumer);
        this.entrySet().forEach(arg_0 -> Multiset.lambda$forEach$1(consumer, arg_0));
    }

    @Override
    default public Spliterator<E> spliterator() {
        return Multisets.spliteratorImpl(this);
    }

    private static void lambda$forEach$1(Consumer consumer, Entry entry) {
        Object e = entry.getElement();
        int n = entry.getCount();
        for (int i = 0; i < n; ++i) {
            consumer.accept(e);
        }
    }

    private static void lambda$forEachEntry$0(ObjIntConsumer objIntConsumer, Entry entry) {
        objIntConsumer.accept(entry.getElement(), entry.getCount());
    }

    public static interface Entry<E> {
        public E getElement();

        public int getCount();

        public boolean equals(Object var1);

        public int hashCode();

        public String toString();
    }
}

