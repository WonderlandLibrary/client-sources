/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.AbstractObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectImmutableList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLists;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ObjectList<K>
extends List<K>,
Comparable<List<? extends K>>,
ObjectCollection<K> {
    @Override
    public ObjectListIterator<K> iterator();

    @Override
    default public ObjectSpliterator<K> spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractObjectList.IndexBasedSpliterator(this, 0);
        }
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 16464);
    }

    @Override
    public ObjectListIterator<K> listIterator();

    @Override
    public ObjectListIterator<K> listIterator(int var1);

    @Override
    public ObjectList<K> subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, Object[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, K[] var2);

    public void addElements(int var1, K[] var2, int var3, int var4);

    default public void setElements(K[] KArray) {
        this.setElements(0, KArray);
    }

    default public void setElements(int n, K[] KArray) {
        this.setElements(n, KArray, 0, KArray.length);
    }

    default public void setElements(int n, K[] KArray, int n2, int n3) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
        ObjectArrays.ensureOffsetLength(KArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        ListIterator listIterator2 = this.listIterator(n);
        int n4 = 0;
        while (n4 < n3) {
            listIterator2.next();
            listIterator2.set(KArray[n2 + n4++]);
        }
    }

    @Override
    default public boolean addAll(int n, ObjectList<? extends K> objectList) {
        return this.addAll(n, objectList);
    }

    @Override
    default public boolean addAll(ObjectList<? extends K> objectList) {
        return this.addAll(this.size(), objectList);
    }

    public static <K> ObjectList<K> of() {
        return ObjectImmutableList.of();
    }

    public static <K> ObjectList<K> of(K k) {
        return ObjectLists.singleton(k);
    }

    public static <K> ObjectList<K> of(K k, K k2) {
        return ObjectImmutableList.of(new Object[]{k, k2});
    }

    public static <K> ObjectList<K> of(K k, K k2, K k3) {
        return ObjectImmutableList.of(new Object[]{k, k2, k3});
    }

    @SafeVarargs
    public static <K> ObjectList<K> of(K ... KArray) {
        switch (KArray.length) {
            case 0: {
                return ObjectList.of();
            }
            case 1: {
                return ObjectList.of(KArray[0]);
            }
        }
        return ObjectImmutableList.of(KArray);
    }

    @Override
    default public void sort(Comparator<? super K> comparator) {
        Object[] objectArray = this.toArray();
        if (comparator == null) {
            ObjectArrays.stableSort(objectArray);
        } else {
            ObjectArrays.stableSort(objectArray, comparator);
        }
        this.setElements(objectArray);
    }

    default public void unstableSort(Comparator<? super K> comparator) {
        Object[] objectArray = this.toArray();
        if (comparator == null) {
            ObjectArrays.unstableSort(objectArray);
        } else {
            ObjectArrays.unstableSort(objectArray, comparator);
        }
        this.setElements(objectArray);
    }

    @Override
    default public Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    default public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    default public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    default public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public ObjectIterator iterator() {
        return this.iterator();
    }
}

