/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Serialization;
import com.google.common.collect.WellBehavedMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class EnumMultiset<E extends Enum<E>>
extends AbstractMapBasedMultiset<E> {
    private transient Class<E> type;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> clazz) {
        return new EnumMultiset<E>(clazz);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable) {
        Iterator<E> iterator2 = iterable.iterator();
        Preconditions.checkArgument(iterator2.hasNext(), "EnumMultiset constructor passed empty Iterable");
        EnumMultiset enumMultiset = new EnumMultiset(((Enum)iterator2.next()).getDeclaringClass());
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable, Class<E> clazz) {
        EnumMultiset<E> enumMultiset = EnumMultiset.create(clazz);
        Iterables.addAll(enumMultiset, iterable);
        return enumMultiset;
    }

    private EnumMultiset(Class<E> clazz) {
        super(WellBehavedMap.wrap(new EnumMap(clazz)));
        this.type = clazz;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.type);
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Class clazz;
        objectInputStream.defaultReadObject();
        this.type = clazz = (Class)objectInputStream.readObject();
        this.setBackingMap(WellBehavedMap.wrap(new EnumMap(this.type)));
        Serialization.populateMultiset(this, objectInputStream);
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        return super.remove(object, n);
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
    public boolean remove(@Nullable Object object) {
        return super.remove(object);
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

