/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public final class LinkedHashMultiset<E>
extends AbstractMapBasedMultiset<E> {
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <E> LinkedHashMultiset<E> create() {
        return new LinkedHashMultiset<E>();
    }

    public static <E> LinkedHashMultiset<E> create(int n) {
        return new LinkedHashMultiset<E>(n);
    }

    public static <E> LinkedHashMultiset<E> create(Iterable<? extends E> iterable) {
        LinkedHashMultiset<E> linkedHashMultiset = LinkedHashMultiset.create(Multisets.inferDistinctElements(iterable));
        Iterables.addAll(linkedHashMultiset, iterable);
        return linkedHashMultiset;
    }

    private LinkedHashMultiset() {
        super(new LinkedHashMap());
    }

    private LinkedHashMultiset(int n) {
        super(Maps.newLinkedHashMapWithExpectedSize(n));
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = Serialization.readCount(objectInputStream);
        this.setBackingMap(new LinkedHashMap());
        Serialization.populateMultiset(this, objectInputStream, n);
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(@Nullable Object object, int n) {
        return super.setCount(object, n);
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        return super.remove(object, n);
    }

    @Override
    @CanIgnoreReturnValue
    public int add(@Nullable Object object, int n) {
        return super.add(object, n);
    }

    @Override
    public int count(@Nullable Object object) {
        return super.count(object);
    }

    @Override
    public Iterator iterator() {
        return super.iterator();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void forEachEntry(ObjIntConsumer objIntConsumer) {
        super.forEachEntry(objIntConsumer);
    }

    @Override
    public Set entrySet() {
        return super.entrySet();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    @Override
    public Set elementSet() {
        return super.elementSet();
    }

    @Override
    @CanIgnoreReturnValue
    public boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean setCount(@Nullable Object object, int n, int n2) {
        return super.setCount(object, n, n2);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean remove(@Nullable Object object) {
        return super.remove(object);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean add(@Nullable Object object) {
        return super.add(object);
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return super.contains(object);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

