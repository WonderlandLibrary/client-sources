/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public final class ObjectBigArrays {
    public static final Object[][] EMPTY_BIG_ARRAY = new Object[0][];
    public static final Object[][] DEFAULT_EMPTY_BIG_ARRAY = new Object[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;

    private ObjectBigArrays() {
    }

    public static <K> K get(K[][] KArray, long l) {
        return KArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static <K> void set(K[][] KArray, long l, K k) {
        KArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = k;
    }

    public static <K> void swap(K[][] KArray, long l, long l2) {
        K k = KArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        KArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = KArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        KArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = k;
    }

    public static <K> long length(K[][] KArray) {
        int n = KArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)KArray[n - 1].length;
    }

    public static <K> void copy(K[][] KArray, long l, K[][] KArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(KArray[n].length - n3, KArray2[n2].length - n4));
                System.arraycopy(KArray[n], n3, KArray2[n2], n4, n5);
                if ((n3 += n5) == 0x8000000) {
                    n3 = 0;
                    ++n;
                }
                if ((n4 += n5) == 0x8000000) {
                    n4 = 0;
                    ++n2;
                }
                l3 -= (long)n5;
            }
        } else {
            int n = BigArrays.segment(l + l3);
            int n6 = BigArrays.segment(l2 + l3);
            int n7 = BigArrays.displacement(l + l3);
            int n8 = BigArrays.displacement(l2 + l3);
            while (l3 > 0L) {
                if (n7 == 0) {
                    n7 = 0x8000000;
                    --n;
                }
                if (n8 == 0) {
                    n8 = 0x8000000;
                    --n6;
                }
                int n9 = (int)Math.min(l3, (long)Math.min(n7, n8));
                System.arraycopy(KArray[n], n7 - n9, KArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static <K> void copyFromBig(K[][] KArray, long l, K[] KArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(KArray[n3].length - n4, n2);
            System.arraycopy(KArray[n3], n4, KArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static <K> void copyToBig(K[] KArray, int n, K[][] KArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(KArray2[n2].length - n3), l2);
            System.arraycopy(KArray, n, KArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static <K> K[][] newBigArray(K[][] KArray, long l) {
        return ObjectBigArrays.newBigArray(KArray.getClass().getComponentType(), l);
    }

    private static Object[][] newBigArray(Class<?> clazz, long l) {
        if (l == 0L && clazz == Object[].class) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        Object[][] objectArray = (Object[][])Array.newInstance(clazz, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                objectArray[i] = (Object[])Array.newInstance(clazz.getComponentType(), 0x8000000);
            }
            objectArray[n - 1] = (Object[])Array.newInstance(clazz.getComponentType(), n2);
        } else {
            for (int i = 0; i < n; ++i) {
                objectArray[i] = (Object[])Array.newInstance(clazz.getComponentType(), 0x8000000);
            }
        }
        return objectArray;
    }

    public static Object[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        Object[][] objectArray = new Object[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                objectArray[i] = new Object[0x8000000];
            }
            objectArray[n - 1] = new Object[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                objectArray[i] = new Object[0x8000000];
            }
        }
        return objectArray;
    }

    public static <K> K[][] wrap(K[] KArray) {
        if (KArray.length == 0 && KArray.getClass() == Object[].class) {
            return EMPTY_BIG_ARRAY;
        }
        if (KArray.length <= 0x8000000) {
            Object[][] objectArray = (Object[][])Array.newInstance(KArray.getClass(), 1);
            objectArray[0] = KArray;
            return objectArray;
        }
        Object[][] objectArray = ObjectBigArrays.newBigArray(KArray.getClass(), (long)KArray.length);
        for (int i = 0; i < objectArray.length; ++i) {
            System.arraycopy(KArray, (int)BigArrays.start(i), objectArray[i], 0, objectArray[i].length);
        }
        return objectArray;
    }

    public static <K> K[][] ensureCapacity(K[][] KArray, long l) {
        return ObjectBigArrays.ensureCapacity(KArray, l, ObjectBigArrays.length(KArray));
    }

    public static <K> K[][] forceCapacity(K[][] KArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = KArray.length - (KArray.length == 0 || KArray.length > 0 && KArray[KArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        Object[][] objectArray = (Object[][])Arrays.copyOf(KArray, n2);
        Class<?> clazz = KArray.getClass().getComponentType();
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                objectArray[i] = (Object[])Array.newInstance(clazz.getComponentType(), 0x8000000);
            }
            objectArray[n2 - 1] = (Object[])Array.newInstance(clazz.getComponentType(), n3);
        } else {
            for (int i = n; i < n2; ++i) {
                objectArray[i] = (Object[])Array.newInstance(clazz.getComponentType(), 0x8000000);
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            ObjectBigArrays.copy(KArray, (long)n * 0x8000000L, objectArray, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return objectArray;
    }

    public static <K> K[][] ensureCapacity(K[][] KArray, long l, long l2) {
        return l > ObjectBigArrays.length(KArray) ? ObjectBigArrays.forceCapacity(KArray, l, l2) : KArray;
    }

    public static <K> K[][] grow(K[][] KArray, long l) {
        long l2 = ObjectBigArrays.length(KArray);
        return l > l2 ? ObjectBigArrays.grow(KArray, l, l2) : KArray;
    }

    public static <K> K[][] grow(K[][] KArray, long l, long l2) {
        long l3 = ObjectBigArrays.length(KArray);
        return l > l3 ? ObjectBigArrays.ensureCapacity(KArray, Math.max(l3 + (l3 >> 1), l), l2) : KArray;
    }

    public static <K> K[][] trim(K[][] KArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = ObjectBigArrays.length(KArray);
        if (l >= l2) {
            return KArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        Object[][] objectArray = (Object[][])Arrays.copyOf(KArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            objectArray[n - 1] = ObjectArrays.trim(objectArray[n - 1], n2);
        }
        return objectArray;
    }

    public static <K> K[][] setLength(K[][] KArray, long l) {
        long l2 = ObjectBigArrays.length(KArray);
        if (l == l2) {
            return KArray;
        }
        if (l < l2) {
            return ObjectBigArrays.trim(KArray, l);
        }
        return ObjectBigArrays.ensureCapacity(KArray, l);
    }

    public static <K> K[][] copy(K[][] KArray, long l, long l2) {
        ObjectBigArrays.ensureOffsetLength(KArray, l, l2);
        K[][] KArray2 = ObjectBigArrays.newBigArray(KArray, l2);
        ObjectBigArrays.copy(KArray, l, KArray2, 0L, l2);
        return KArray2;
    }

    public static <K> K[][] copy(K[][] KArray) {
        Object[][] objectArray = (Object[][])KArray.clone();
        int n = objectArray.length;
        while (n-- != 0) {
            objectArray[n] = (Object[])KArray[n].clone();
        }
        return objectArray;
    }

    public static <K> void fill(K[][] KArray, K k) {
        int n = KArray.length;
        while (n-- != 0) {
            Arrays.fill(KArray[n], k);
        }
    }

    public static <K> void fill(K[][] KArray, long l, long l2, K k) {
        long l3 = ObjectBigArrays.length(KArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(KArray[n], n3, n4, k);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(KArray[n2], 0, n4, k);
        }
        while (--n2 > n) {
            Arrays.fill(KArray[n2], k);
        }
        Arrays.fill(KArray[n], n3, 0x8000000, k);
    }

    public static <K> boolean equals(K[][] KArray, K[][] KArray2) {
        if (ObjectBigArrays.length(KArray) != ObjectBigArrays.length(KArray2)) {
            return true;
        }
        int n = KArray.length;
        while (n-- != 0) {
            K[] KArray3 = KArray[n];
            K[] KArray4 = KArray2[n];
            int n2 = KArray3.length;
            while (n2-- != 0) {
                if (Objects.equals(KArray3[n2], KArray4[n2])) continue;
                return true;
            }
        }
        return false;
    }

    public static <K> String toString(K[][] KArray) {
        if (KArray == null) {
            return "null";
        }
        long l = ObjectBigArrays.length(KArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(ObjectBigArrays.get(KArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static <K> void ensureFromTo(K[][] KArray, long l, long l2) {
        BigArrays.ensureFromTo(ObjectBigArrays.length(KArray), l, l2);
    }

    public static <K> void ensureOffsetLength(K[][] KArray, long l, long l2) {
        BigArrays.ensureOffsetLength(ObjectBigArrays.length(KArray), l, l2);
    }

    private static <K> void vecSwap(K[][] KArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            ObjectBigArrays.swap(KArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static <K> long med3(K[][] KArray, long l, long l2, long l3, Comparator<K> comparator) {
        int n = comparator.compare(ObjectBigArrays.get(KArray, l), ObjectBigArrays.get(KArray, l2));
        int n2 = comparator.compare(ObjectBigArrays.get(KArray, l), ObjectBigArrays.get(KArray, l3));
        int n3 = comparator.compare(ObjectBigArrays.get(KArray, l2), ObjectBigArrays.get(KArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static <K> void selectionSort(K[][] KArray, long l, long l2, Comparator<K> comparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (comparator.compare(ObjectBigArrays.get(KArray, j), ObjectBigArrays.get(KArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ObjectBigArrays.swap(KArray, i, l3);
        }
    }

    public static <K> void quickSort(K[][] KArray, long l, long l2, Comparator<K> comparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ObjectBigArrays.selectionSort(KArray, l, l2, comparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ObjectBigArrays.med3(KArray, l7, l7 + l9, l7 + 2L * l9, comparator);
                l6 = ObjectBigArrays.med3(KArray, l6 - l9, l6, l6 + l9, comparator);
                l8 = ObjectBigArrays.med3(KArray, l8 - 2L * l9, l8 - l9, l8, comparator);
            }
            l6 = ObjectBigArrays.med3(KArray, l7, l6, l8, comparator);
        }
        K k = ObjectBigArrays.get(KArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = comparator.compare(ObjectBigArrays.get(KArray, l10), k)) <= 0) {
                if (n == 0) {
                    ObjectBigArrays.swap(KArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = comparator.compare(ObjectBigArrays.get(KArray, l3), k)) >= 0) {
                if (n == 0) {
                    ObjectBigArrays.swap(KArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ObjectBigArrays.swap(KArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ObjectBigArrays.vecSwap(KArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ObjectBigArrays.vecSwap(KArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ObjectBigArrays.quickSort(KArray, l, l + l13, comparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            ObjectBigArrays.quickSort(KArray, l12 - l13, l12, comparator);
        }
    }

    private static <K> long med3(K[][] KArray, long l, long l2, long l3) {
        int n = ((Comparable)ObjectBigArrays.get(KArray, l)).compareTo(ObjectBigArrays.get(KArray, l2));
        int n2 = ((Comparable)ObjectBigArrays.get(KArray, l)).compareTo(ObjectBigArrays.get(KArray, l3));
        int n3 = ((Comparable)ObjectBigArrays.get(KArray, l2)).compareTo(ObjectBigArrays.get(KArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static <K> void selectionSort(K[][] KArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (((Comparable)ObjectBigArrays.get(KArray, j)).compareTo(ObjectBigArrays.get(KArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ObjectBigArrays.swap(KArray, i, l3);
        }
    }

    public static <K> void quickSort(K[][] KArray, Comparator<K> comparator) {
        ObjectBigArrays.quickSort(KArray, 0L, ObjectBigArrays.length(KArray), comparator);
    }

    public static <K> void quickSort(K[][] KArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ObjectBigArrays.selectionSort(KArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ObjectBigArrays.med3(KArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = ObjectBigArrays.med3(KArray, l6 - l9, l6, l6 + l9);
                l8 = ObjectBigArrays.med3(KArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = ObjectBigArrays.med3(KArray, l7, l6, l8);
        }
        K k = ObjectBigArrays.get(KArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = ((Comparable)ObjectBigArrays.get(KArray, l10)).compareTo(k)) <= 0) {
                if (n == 0) {
                    ObjectBigArrays.swap(KArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = ((Comparable)ObjectBigArrays.get(KArray, l3)).compareTo(k)) >= 0) {
                if (n == 0) {
                    ObjectBigArrays.swap(KArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ObjectBigArrays.swap(KArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ObjectBigArrays.vecSwap(KArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ObjectBigArrays.vecSwap(KArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ObjectBigArrays.quickSort(KArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            ObjectBigArrays.quickSort(KArray, l12 - l13, l12);
        }
    }

    public static <K> void quickSort(K[][] KArray) {
        ObjectBigArrays.quickSort(KArray, 0L, ObjectBigArrays.length(KArray));
    }

    public static <K> long binarySearch(K[][] KArray, long l, long l2, K k) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            K k2 = ObjectBigArrays.get(KArray, l3);
            int n = ((Comparable)k2).compareTo(k);
            if (n < 0) {
                l = l3 + 1L;
                continue;
            }
            if (n > 0) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static <K> long binarySearch(K[][] KArray, Object object) {
        return ObjectBigArrays.binarySearch(KArray, 0L, ObjectBigArrays.length(KArray), object);
    }

    public static <K> long binarySearch(K[][] KArray, long l, long l2, K k, Comparator<K> comparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            K k2 = ObjectBigArrays.get(KArray, l3);
            int n = comparator.compare(k2, k);
            if (n < 0) {
                l = l3 + 1L;
                continue;
            }
            if (n > 0) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static <K> long binarySearch(K[][] KArray, K k, Comparator<K> comparator) {
        return ObjectBigArrays.binarySearch(KArray, 0L, ObjectBigArrays.length(KArray), k, comparator);
    }

    public static <K> K[][] shuffle(K[][] KArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            K k = ObjectBigArrays.get(KArray, l + l3);
            ObjectBigArrays.set(KArray, l + l3, ObjectBigArrays.get(KArray, l + l4));
            ObjectBigArrays.set(KArray, l + l4, k);
        }
        return KArray;
    }

    public static <K> K[][] shuffle(K[][] KArray, Random random2) {
        long l = ObjectBigArrays.length(KArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            K k = ObjectBigArrays.get(KArray, l);
            ObjectBigArrays.set(KArray, l, ObjectBigArrays.get(KArray, l2));
            ObjectBigArrays.set(KArray, l2, k);
        }
        return KArray;
    }

    private static final class BigArrayHashStrategy<K>
    implements Hash.Strategy<K[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(K[][] KArray) {
            return Arrays.deepHashCode(KArray);
        }

        @Override
        public boolean equals(K[][] KArray, K[][] KArray2) {
            return ObjectBigArrays.equals(KArray, KArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((Object[][])object, (Object[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((Object[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

