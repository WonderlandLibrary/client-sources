/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
final class SortedLists {
    private SortedLists() {
    }

    public static <E extends Comparable> int binarySearch(List<? extends E> list, E e, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(e);
        return SortedLists.binarySearch(list, e, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E, K extends Comparable> int binarySearch(List<E> list, Function<? super E, K> function, @Nullable K k, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return SortedLists.binarySearch(list, function, k, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E, K> int binarySearch(List<E> list, Function<? super E, K> function, @Nullable K k, Comparator<? super K> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return SortedLists.binarySearch(Lists.transform(list, function), k, comparator, keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E> int binarySearch(List<? extends E> list, @Nullable E e, Comparator<? super E> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(keyPresentBehavior);
        Preconditions.checkNotNull(keyAbsentBehavior);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList(list);
        }
        int n = 0;
        int n2 = list.size() - 1;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = comparator.compare(e, list.get(n3));
            if (n4 < 0) {
                n2 = n3 - 1;
                continue;
            }
            if (n4 > 0) {
                n = n3 + 1;
                continue;
            }
            return n + keyPresentBehavior.resultIndex(comparator, e, list.subList(n, n2 + 1), n3 - n);
        }
        return keyAbsentBehavior.resultIndex(n);
    }

    public static enum KeyAbsentBehavior {
        NEXT_LOWER{

            @Override
            int resultIndex(int n) {
                return n - 1;
            }
        }
        ,
        NEXT_HIGHER{

            @Override
            public int resultIndex(int n) {
                return n;
            }
        }
        ,
        INVERTED_INSERTION_INDEX{

            @Override
            public int resultIndex(int n) {
                return ~n;
            }
        };


        private KeyAbsentBehavior() {
        }

        abstract int resultIndex(int var1);

        KeyAbsentBehavior(1 var3_3) {
            this();
        }
    }

    public static enum KeyPresentBehavior {
        ANY_PRESENT{

            @Override
            <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                return n;
            }
        }
        ,
        LAST_PRESENT{

            @Override
            <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                int n2 = n;
                int n3 = list.size() - 1;
                while (n2 < n3) {
                    int n4 = n2 + n3 + 1 >>> 1;
                    int n5 = comparator.compare(list.get(n4), e);
                    if (n5 > 0) {
                        n3 = n4 - 1;
                        continue;
                    }
                    n2 = n4;
                }
                return n2;
            }
        }
        ,
        FIRST_PRESENT{

            @Override
            <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                int n2 = 0;
                int n3 = n;
                while (n2 < n3) {
                    int n4 = n2 + n3 >>> 1;
                    int n5 = comparator.compare(list.get(n4), e);
                    if (n5 < 0) {
                        n2 = n4 + 1;
                        continue;
                    }
                    n3 = n4;
                }
                return n2;
            }
        }
        ,
        FIRST_AFTER{

            @Override
            public <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                return LAST_PRESENT.resultIndex(comparator, e, list, n) + 1;
            }
        }
        ,
        LAST_BEFORE{

            @Override
            public <E> int resultIndex(Comparator<? super E> comparator, E e, List<? extends E> list, int n) {
                return FIRST_PRESENT.resultIndex(comparator, e, list, n) - 1;
            }
        };


        private KeyPresentBehavior() {
        }

        abstract <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4);

        KeyPresentBehavior(1 var3_3) {
            this();
        }
    }
}

