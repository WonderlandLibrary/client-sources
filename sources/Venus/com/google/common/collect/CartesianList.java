/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
final class CartesianList<E>
extends AbstractList<List<E>>
implements RandomAccess {
    private final transient ImmutableList<List<E>> axes;
    private final transient int[] axesSizeProduct;

    static <E> List<List<E>> create(List<? extends List<? extends E>> list) {
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        for (List<E> list2 : list) {
            ImmutableList<E> immutableList = ImmutableList.copyOf(list2);
            if (immutableList.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add(immutableList);
        }
        return new CartesianList<E>(builder.build());
    }

    CartesianList(ImmutableList<List<E>> immutableList) {
        this.axes = immutableList;
        int[] nArray = new int[immutableList.size() + 1];
        nArray[immutableList.size()] = 1;
        try {
            for (int i = immutableList.size() - 1; i >= 0; --i) {
                nArray[i] = IntMath.checkedMultiply(nArray[i + 1], ((List)immutableList.get(i)).size());
            }
        } catch (ArithmeticException arithmeticException) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
        this.axesSizeProduct = nArray;
    }

    private int getAxisIndexForProductIndex(int n, int n2) {
        return n / this.axesSizeProduct[n2 + 1] % ((List)this.axes.get(n2)).size();
    }

    @Override
    public ImmutableList<E> get(int n) {
        Preconditions.checkElementIndex(n, this.size());
        return new ImmutableList<E>(this, n){
            final int val$index;
            final CartesianList this$0;
            {
                this.this$0 = cartesianList;
                this.val$index = n;
            }

            @Override
            public int size() {
                return CartesianList.access$000(this.this$0).size();
            }

            @Override
            public E get(int n) {
                Preconditions.checkElementIndex(n, this.size());
                int n2 = CartesianList.access$100(this.this$0, this.val$index, n);
                return ((List)CartesianList.access$000(this.this$0).get(n)).get(n2);
            }

            @Override
            boolean isPartialView() {
                return false;
            }
        };
    }

    @Override
    public int size() {
        return this.axesSizeProduct[0];
    }

    @Override
    public boolean contains(@Nullable Object object) {
        if (!(object instanceof List)) {
            return true;
        }
        List list = (List)object;
        if (list.size() != this.axes.size()) {
            return true;
        }
        ListIterator listIterator2 = list.listIterator();
        while (listIterator2.hasNext()) {
            int n = listIterator2.nextIndex();
            if (((List)this.axes.get(n)).contains(listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }

    static ImmutableList access$000(CartesianList cartesianList) {
        return cartesianList.axes;
    }

    static int access$100(CartesianList cartesianList, int n, int n2) {
        return cartesianList.getAxisIndexForProductIndex(n, n2);
    }
}

