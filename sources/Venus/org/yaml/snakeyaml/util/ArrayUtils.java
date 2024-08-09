/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.util;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
    private ArrayUtils() {
    }

    public static <E> List<E> toUnmodifiableList(E[] EArray) {
        return EArray.length == 0 ? Collections.emptyList() : new UnmodifiableArrayList<E>(EArray);
    }

    public static <E> List<E> toUnmodifiableCompositeList(E[] EArray, E[] EArray2) {
        List<E> list = EArray.length == 0 ? ArrayUtils.toUnmodifiableList(EArray2) : (EArray2.length == 0 ? ArrayUtils.toUnmodifiableList(EArray) : new CompositeUnmodifiableArrayList<E>(EArray, EArray2));
        return list;
    }

    private static class CompositeUnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array1;
        private final E[] array2;

        CompositeUnmodifiableArrayList(E[] EArray, E[] EArray2) {
            this.array1 = EArray;
            this.array2 = EArray2;
        }

        @Override
        public E get(int n) {
            E e;
            if (n < this.array1.length) {
                e = this.array1[n];
            } else if (n - this.array1.length < this.array2.length) {
                e = this.array2[n - this.array1.length];
            } else {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.size());
            }
            return e;
        }

        @Override
        public int size() {
            return this.array1.length + this.array2.length;
        }
    }

    private static class UnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array;

        UnmodifiableArrayList(E[] EArray) {
            this.array = EArray;
        }

        @Override
        public E get(int n) {
            if (n >= this.array.length) {
                throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + this.size());
            }
            return this.array[n];
        }

        @Override
        public int size() {
            return this.array.length;
        }
    }
}

