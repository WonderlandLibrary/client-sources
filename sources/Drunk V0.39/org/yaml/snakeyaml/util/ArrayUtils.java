/*
 * Decompiled with CFR 0.152.
 */
package org.yaml.snakeyaml.util;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
    private ArrayUtils() {
    }

    public static <E> List<E> toUnmodifiableList(E[] elements) {
        UnmodifiableArrayList<E> unmodifiableArrayList;
        if (elements.length == 0) {
            unmodifiableArrayList = Collections.emptyList();
            return unmodifiableArrayList;
        }
        unmodifiableArrayList = new UnmodifiableArrayList<E>(elements);
        return unmodifiableArrayList;
    }

    public static <E> List<E> toUnmodifiableCompositeList(E[] array1, E[] array2) {
        if (array1.length == 0) {
            return ArrayUtils.toUnmodifiableList(array2);
        }
        if (array2.length != 0) return new CompositeUnmodifiableArrayList<E>(array1, array2);
        return ArrayUtils.toUnmodifiableList(array1);
    }

    private static class CompositeUnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array1;
        private final E[] array2;

        CompositeUnmodifiableArrayList(E[] array1, E[] array2) {
            this.array1 = array1;
            this.array2 = array2;
        }

        @Override
        public E get(int index) {
            E element;
            if (index < this.array1.length) {
                element = this.array1[index];
                return element;
            }
            if (index - this.array1.length >= this.array2.length) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
            element = this.array2[index - this.array1.length];
            return element;
        }

        @Override
        public int size() {
            return this.array1.length + this.array2.length;
        }
    }

    private static class UnmodifiableArrayList<E>
    extends AbstractList<E> {
        private final E[] array;

        UnmodifiableArrayList(E[] array) {
            this.array = array;
        }

        @Override
        public E get(int index) {
            if (index < this.array.length) return this.array[index];
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size());
        }

        @Override
        public int size() {
            return this.array.length;
        }
    }
}

