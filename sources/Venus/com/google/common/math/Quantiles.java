/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Beta
@GwtIncompatible
public final class Quantiles {
    public static ScaleAndIndex median() {
        return Quantiles.scale(2).index(1);
    }

    public static Scale quartiles() {
        return Quantiles.scale(4);
    }

    public static Scale percentiles() {
        return Quantiles.scale(100);
    }

    public static Scale scale(int n) {
        return new Scale(n, null);
    }

    private static boolean containsNaN(double ... dArray) {
        for (double d : dArray) {
            if (!Double.isNaN(d)) continue;
            return false;
        }
        return true;
    }

    private static double interpolate(double d, double d2, double d3, double d4) {
        if (d == Double.NEGATIVE_INFINITY) {
            if (d2 == Double.POSITIVE_INFINITY) {
                return Double.NaN;
            }
            return Double.NEGATIVE_INFINITY;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }
        return d + (d2 - d) * d3 / d4;
    }

    private static void checkIndex(int n, int n2) {
        if (n < 0 || n > n2) {
            throw new IllegalArgumentException("Quantile indexes must be between 0 and the scale, which is " + n2);
        }
    }

    private static double[] longsToDoubles(long[] lArray) {
        int n = lArray.length;
        double[] dArray = new double[n];
        for (int i = 0; i < n; ++i) {
            dArray[i] = lArray[i];
        }
        return dArray;
    }

    private static double[] intsToDoubles(int[] nArray) {
        int n = nArray.length;
        double[] dArray = new double[n];
        for (int i = 0; i < n; ++i) {
            dArray[i] = nArray[i];
        }
        return dArray;
    }

    private static void selectInPlace(int n, double[] dArray, int n2, int n3) {
        if (n == n2) {
            int n4 = n2;
            for (int i = n2 + 1; i <= n3; ++i) {
                if (!(dArray[n4] > dArray[i])) continue;
                n4 = i;
            }
            if (n4 != n2) {
                Quantiles.swap(dArray, n4, n2);
            }
            return;
        }
        while (n3 > n2) {
            int n5 = Quantiles.partition(dArray, n2, n3);
            if (n5 >= n) {
                n3 = n5 - 1;
            }
            if (n5 > n) continue;
            n2 = n5 + 1;
        }
    }

    private static int partition(double[] dArray, int n, int n2) {
        Quantiles.movePivotToStartOfSlice(dArray, n, n2);
        double d = dArray[n];
        int n3 = n2;
        for (int i = n2; i > n; --i) {
            if (!(dArray[i] > d)) continue;
            Quantiles.swap(dArray, n3, i);
            --n3;
        }
        Quantiles.swap(dArray, n, n3);
        return n3;
    }

    private static void movePivotToStartOfSlice(double[] dArray, int n, int n2) {
        boolean bl;
        int n3 = n + n2 >>> 1;
        boolean bl2 = dArray[n2] < dArray[n3];
        boolean bl3 = dArray[n3] < dArray[n];
        boolean bl4 = bl = dArray[n2] < dArray[n];
        if (bl2 == bl3) {
            Quantiles.swap(dArray, n3, n);
        } else if (bl2 != bl) {
            Quantiles.swap(dArray, n, n2);
        }
    }

    private static void selectAllInPlace(int[] nArray, int n, int n2, double[] dArray, int n3, int n4) {
        int n5;
        int n6;
        int n7 = Quantiles.chooseNextSelection(nArray, n, n2, n3, n4);
        int n8 = nArray[n7];
        Quantiles.selectInPlace(n8, dArray, n3, n4);
        for (n6 = n7 - 1; n6 >= n && nArray[n6] == n8; --n6) {
        }
        if (n6 >= n) {
            Quantiles.selectAllInPlace(nArray, n, n6, dArray, n3, n8 - 1);
        }
        for (n5 = n7 + 1; n5 <= n2 && nArray[n5] == n8; ++n5) {
        }
        if (n5 <= n2) {
            Quantiles.selectAllInPlace(nArray, n5, n2, dArray, n8 + 1, n4);
        }
    }

    private static int chooseNextSelection(int[] nArray, int n, int n2, int n3, int n4) {
        if (n == n2) {
            return n;
        }
        int n5 = n3 + n4 >>> 1;
        int n6 = n;
        int n7 = n2;
        while (n7 > n6 + 1) {
            int n8 = n6 + n7 >>> 1;
            if (nArray[n8] > n5) {
                n7 = n8;
                continue;
            }
            if (nArray[n8] < n5) {
                n6 = n8;
                continue;
            }
            return n8;
        }
        if (n3 + n4 - nArray[n6] - nArray[n7] > 0) {
            return n7;
        }
        return n6;
    }

    private static void swap(double[] dArray, int n, int n2) {
        double d = dArray[n];
        dArray[n] = dArray[n2];
        dArray[n2] = d;
    }

    static void access$300(int n, int n2) {
        Quantiles.checkIndex(n, n2);
    }

    static double[] access$400(long[] lArray) {
        return Quantiles.longsToDoubles(lArray);
    }

    static double[] access$500(int[] nArray) {
        return Quantiles.intsToDoubles(nArray);
    }

    static boolean access$600(double[] dArray) {
        return Quantiles.containsNaN(dArray);
    }

    static void access$700(int n, double[] dArray, int n2, int n3) {
        Quantiles.selectInPlace(n, dArray, n2, n3);
    }

    static double access$800(double d, double d2, double d3, double d4) {
        return Quantiles.interpolate(d, d2, d3, d4);
    }

    static void access$900(int[] nArray, int n, int n2, double[] dArray, int n3, int n4) {
        Quantiles.selectAllInPlace(nArray, n, n2, dArray, n3, n4);
    }

    public static final class ScaleAndIndexes {
        private final int scale;
        private final int[] indexes;

        private ScaleAndIndexes(int n, int[] nArray) {
            for (int n2 : nArray) {
                Quantiles.access$300(n2, n);
            }
            this.scale = n;
            this.indexes = nArray;
        }

        public Map<Integer, Double> compute(Collection<? extends Number> collection) {
            return this.computeInPlace(Doubles.toArray(collection));
        }

        public Map<Integer, Double> compute(double ... dArray) {
            return this.computeInPlace((double[])dArray.clone());
        }

        public Map<Integer, Double> compute(long ... lArray) {
            return this.computeInPlace(Quantiles.access$400(lArray));
        }

        public Map<Integer, Double> compute(int ... nArray) {
            return this.computeInPlace(Quantiles.access$500(nArray));
        }

        public Map<Integer, Double> computeInPlace(double ... dArray) {
            int n;
            Preconditions.checkArgument(dArray.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.access$600(dArray)) {
                HashMap<Integer, Double> hashMap = new HashMap<Integer, Double>();
                for (int n2 : this.indexes) {
                    hashMap.put(n2, Double.NaN);
                }
                return Collections.unmodifiableMap(hashMap);
            }
            int[] nArray = new int[this.indexes.length];
            int[] nArray2 = new int[this.indexes.length];
            int[] nArray3 = new int[this.indexes.length * 2];
            int n3 = 0;
            for (int i = 0; i < this.indexes.length; ++i) {
                long l = (long)this.indexes[i] * (long)(dArray.length - 1);
                n = (int)LongMath.divide(l, this.scale, RoundingMode.DOWN);
                int n4 = (int)(l - (long)n * (long)this.scale);
                nArray[i] = n;
                nArray2[i] = n4;
                nArray3[n3] = n;
                ++n3;
                if (n4 == 0) continue;
                nArray3[n3] = n + 1;
                ++n3;
            }
            Arrays.sort(nArray3, 0, n3);
            Quantiles.access$900(nArray3, 0, n3 - 1, dArray, 0, dArray.length - 1);
            HashMap<Integer, Double> hashMap = new HashMap<Integer, Double>();
            for (int i = 0; i < this.indexes.length; ++i) {
                int n5 = nArray[i];
                n = nArray2[i];
                if (n == 0) {
                    hashMap.put(this.indexes[i], dArray[n5]);
                    continue;
                }
                hashMap.put(this.indexes[i], Quantiles.access$800(dArray[n5], dArray[n5 + 1], n, this.scale));
            }
            return Collections.unmodifiableMap(hashMap);
        }

        ScaleAndIndexes(int n, int[] nArray, 1 var3_3) {
            this(n, nArray);
        }
    }

    public static final class ScaleAndIndex {
        private final int scale;
        private final int index;

        private ScaleAndIndex(int n, int n2) {
            Quantiles.access$300(n2, n);
            this.scale = n;
            this.index = n2;
        }

        public double compute(Collection<? extends Number> collection) {
            return this.computeInPlace(Doubles.toArray(collection));
        }

        public double compute(double ... dArray) {
            return this.computeInPlace((double[])dArray.clone());
        }

        public double compute(long ... lArray) {
            return this.computeInPlace(Quantiles.access$400(lArray));
        }

        public double compute(int ... nArray) {
            return this.computeInPlace(Quantiles.access$500(nArray));
        }

        public double computeInPlace(double ... dArray) {
            Preconditions.checkArgument(dArray.length > 0, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.access$600(dArray)) {
                return Double.NaN;
            }
            long l = (long)this.index * (long)(dArray.length - 1);
            int n = (int)LongMath.divide(l, this.scale, RoundingMode.DOWN);
            int n2 = (int)(l - (long)n * (long)this.scale);
            Quantiles.access$700(n, dArray, 0, dArray.length - 1);
            if (n2 == 0) {
                return dArray[n];
            }
            Quantiles.access$700(n + 1, dArray, n + 1, dArray.length - 1);
            return Quantiles.access$800(dArray[n], dArray[n + 1], n2, this.scale);
        }

        ScaleAndIndex(int n, int n2, 1 var3_3) {
            this(n, n2);
        }
    }

    public static final class Scale {
        private final int scale;

        private Scale(int n) {
            Preconditions.checkArgument(n > 0, "Quantile scale must be positive");
            this.scale = n;
        }

        public ScaleAndIndex index(int n) {
            return new ScaleAndIndex(this.scale, n, null);
        }

        public ScaleAndIndexes indexes(int ... nArray) {
            return new ScaleAndIndexes(this.scale, (int[])nArray.clone(), null);
        }

        public ScaleAndIndexes indexes(Collection<Integer> collection) {
            return new ScaleAndIndexes(this.scale, Ints.toArray(collection), null);
        }

        Scale(int n, 1 var2_2) {
            this(n);
        }
    }
}

