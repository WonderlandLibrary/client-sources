/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E>
extends AbstractList<E> {
    private final List<E> delegate;
    private final E defaultElement;

    public static <E> NonNullList<E> create() {
        return new NonNullList<E>();
    }

    public static <E> NonNullList<E> withSize(int n, E e) {
        Validate.notNull(e);
        Object[] objectArray = new Object[n];
        Arrays.fill(objectArray, e);
        return new NonNullList<Object>(Arrays.asList(objectArray), e);
    }

    @SafeVarargs
    public static <E> NonNullList<E> from(E e, E ... EArray) {
        return new NonNullList<E>(Arrays.asList(EArray), e);
    }

    protected NonNullList() {
        this(Lists.newArrayList(), null);
    }

    protected NonNullList(List<E> list, @Nullable E e) {
        this.delegate = list;
        this.defaultElement = e;
    }

    @Override
    @Nonnull
    public E get(int n) {
        return this.delegate.get(n);
    }

    @Override
    public E set(int n, E e) {
        Validate.notNull(e);
        return this.delegate.set(n, e);
    }

    @Override
    public void add(int n, E e) {
        Validate.notNull(e);
        this.delegate.add(n, e);
    }

    @Override
    public E remove(int n) {
        return this.delegate.remove(n);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public void clear() {
        if (this.defaultElement == null) {
            super.clear();
        } else {
            for (int i = 0; i < this.size(); ++i) {
                this.set(i, this.defaultElement);
            }
        }
    }
}

