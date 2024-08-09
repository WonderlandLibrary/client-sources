/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparators;
import com.viaversion.viaversion.libs.fastutil.ints.IntImmutableList;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntUnaryOperator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntList
extends List<Integer>,
Comparable<List<? extends Integer>>,
IntCollection {
    @Override
    public IntListIterator iterator();

    @Override
    default public IntSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractIntList.IndexBasedSpliterator(this, 0);
        }
        return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 16720);
    }

    public IntListIterator listIterator();

    public IntListIterator listIterator(int var1);

    public IntList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, int[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, int[] var2);

    public void addElements(int var1, int[] var2, int var3, int var4);

    default public void setElements(int[] nArray) {
        this.setElements(0, nArray);
    }

    default public void setElements(int n, int[] nArray) {
        this.setElements(n, nArray, 0, nArray.length);
    }

    default public void setElements(int n, int[] nArray, int n2, int n3) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
        IntArrays.ensureOffsetLength(nArray, n2, n3);
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        IntListIterator intListIterator = this.listIterator(n);
        int n4 = 0;
        while (n4 < n3) {
            intListIterator.nextInt();
            intListIterator.set(nArray[n2 + n4++]);
        }
    }

    @Override
    public boolean add(int var1);

    @Override
    public void add(int var1, int var2);

    @Override
    @Deprecated
    default public void add(int n, Integer n2) {
        this.add(n, (int)n2);
    }

    public boolean addAll(int var1, IntCollection var2);

    @Override
    public int set(int var1, int var2);

    default public void replaceAll(java.util.function.IntUnaryOperator intUnaryOperator) {
        IntListIterator intListIterator = this.listIterator();
        while (intListIterator.hasNext()) {
            intListIterator.set(intUnaryOperator.applyAsInt(intListIterator.nextInt()));
        }
    }

    default public void replaceAll(IntUnaryOperator intUnaryOperator) {
        this.replaceAll((java.util.function.IntUnaryOperator)intUnaryOperator);
    }

    @Override
    @Deprecated
    default public void replaceAll(UnaryOperator<Integer> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        this.replaceAll(unaryOperator instanceof java.util.function.IntUnaryOperator ? (java.util.function.IntUnaryOperator)((Object)unaryOperator) : unaryOperator::apply);
    }

    public int getInt(int var1);

    public int indexOf(int var1);

    public int lastIndexOf(int var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return IntCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Integer get(int n) {
        return this.getInt(n);
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf((Integer)object);
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf((Integer)object);
    }

    @Override
    @Deprecated
    default public boolean add(Integer n) {
        return this.add((int)n);
    }

    public int removeInt(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return IntCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Integer remove(int n) {
        return this.removeInt(n);
    }

    @Override
    @Deprecated
    default public Integer set(int n, Integer n2) {
        return this.set(n, (int)n2);
    }

    default public boolean addAll(int n, IntList intList) {
        return this.addAll(n, (IntCollection)intList);
    }

    default public boolean addAll(IntList intList) {
        return this.addAll(this.size(), intList);
    }

    public static IntList of() {
        return IntImmutableList.of();
    }

    public static IntList of(int n) {
        return IntLists.singleton(n);
    }

    public static IntList of(int n, int n2) {
        return IntImmutableList.of(new int[]{n, n2});
    }

    public static IntList of(int n, int n2, int n3) {
        return IntImmutableList.of(new int[]{n, n2, n3});
    }

    public static IntList of(int ... nArray) {
        switch (nArray.length) {
            case 0: {
                return IntList.of();
            }
            case 1: {
                return IntList.of(nArray[0]);
            }
        }
        return IntImmutableList.of(nArray);
    }

    @Override
    @Deprecated
    default public void sort(Comparator<? super Integer> comparator) {
        this.sort(IntComparators.asIntComparator(comparator));
    }

    default public void sort(IntComparator intComparator) {
        if (intComparator == null) {
            this.unstableSort(intComparator);
        } else {
            int[] nArray = this.toIntArray();
            IntArrays.stableSort(nArray, intComparator);
            this.setElements(nArray);
        }
    }

    @Deprecated
    default public void unstableSort(Comparator<? super Integer> comparator) {
        this.unstableSort(IntComparators.asIntComparator(comparator));
    }

    default public void unstableSort(IntComparator intComparator) {
        int[] nArray = this.toIntArray();
        if (intComparator == null) {
            IntArrays.unstableSort(nArray);
        } else {
            IntArrays.unstableSort(nArray, intComparator);
        }
        this.setElements(nArray);
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
    @Deprecated
    default public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    @Deprecated
    default public void add(int n, Object object) {
        this.add(n, (Integer)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Integer)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Integer)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public IntIterator iterator() {
        return this.iterator();
    }
}

