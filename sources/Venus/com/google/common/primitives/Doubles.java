/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Pattern;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Doubles {
    public static final int BYTES = 8;
    @GwtIncompatible
    static final Pattern FLOATING_POINT_PATTERN = Doubles.fpPattern();

    private Doubles() {
    }

    public static int hashCode(double d) {
        return Double.valueOf(d).hashCode();
    }

    public static int compare(double d, double d2) {
        return Double.compare(d, d2);
    }

    public static boolean isFinite(double d) {
        return Double.NEGATIVE_INFINITY < d & d < Double.POSITIVE_INFINITY;
    }

    public static boolean contains(double[] dArray, double d) {
        for (double d2 : dArray) {
            if (d2 != d) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(double[] dArray, double d) {
        return Doubles.indexOf(dArray, d, 0, dArray.length);
    }

    private static int indexOf(double[] dArray, double d, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (dArray[i] != d) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(double[] dArray, double[] dArray2) {
        Preconditions.checkNotNull(dArray, "array");
        Preconditions.checkNotNull(dArray2, "target");
        if (dArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < dArray.length - dArray2.length + 1; ++i) {
            for (int j = 0; j < dArray2.length; ++j) {
                if (dArray[i + j] != dArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(double[] dArray, double d) {
        return Doubles.lastIndexOf(dArray, d, 0, dArray.length);
    }

    private static int lastIndexOf(double[] dArray, double d, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (dArray[i] != d) continue;
            return i;
        }
        return 1;
    }

    public static double min(double ... dArray) {
        Preconditions.checkArgument(dArray.length > 0);
        double d = dArray[0];
        for (int i = 1; i < dArray.length; ++i) {
            d = Math.min(d, dArray[i]);
        }
        return d;
    }

    public static double max(double ... dArray) {
        Preconditions.checkArgument(dArray.length > 0);
        double d = dArray[0];
        for (int i = 1; i < dArray.length; ++i) {
            d = Math.max(d, dArray[i]);
        }
        return d;
    }

    @Beta
    public static double constrainToRange(double d, double d2, double d3) {
        Preconditions.checkArgument(d2 <= d3, "min (%s) must be less than or equal to max (%s)", (Object)d2, (Object)d3);
        return Math.min(Math.max(d, d2), d3);
    }

    public static double[] concat(double[] ... dArray) {
        int n = 0;
        for (double[] dArray2 : dArray) {
            n += dArray2.length;
        }
        double[] dArray3 = new double[n];
        int n2 = 0;
        for (double[] dArray4 : dArray) {
            System.arraycopy(dArray4, 0, dArray3, n2, dArray4.length);
            n2 += dArray4.length;
        }
        return dArray3;
    }

    @Beta
    public static Converter<String, Double> stringConverter() {
        return DoubleConverter.INSTANCE;
    }

    public static double[] ensureCapacity(double[] dArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return dArray.length < n ? Arrays.copyOf(dArray, n + n2) : dArray;
    }

    public static String join(String string, double ... dArray) {
        Preconditions.checkNotNull(string);
        if (dArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(dArray.length * 12);
        stringBuilder.append(dArray[0]);
        for (int i = 1; i < dArray.length; ++i) {
            stringBuilder.append(string).append(dArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<double[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static double[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof DoubleArrayAsList) {
            return ((DoubleArrayAsList)collection).toDoubleArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        double[] dArray = new double[n];
        for (int i = 0; i < n; ++i) {
            dArray[i] = ((Number)Preconditions.checkNotNull(objectArray[i])).doubleValue();
        }
        return dArray;
    }

    public static List<Double> asList(double ... dArray) {
        if (dArray.length == 0) {
            return Collections.emptyList();
        }
        return new DoubleArrayAsList(dArray);
    }

    @GwtIncompatible
    private static Pattern fpPattern() {
        String string = "(?:\\d++(?:\\.\\d*+)?|\\.\\d++)";
        String string2 = string + "(?:[eE][+-]?\\d++)?[fFdD]?";
        String string3 = "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)";
        String string4 = "0[xX]" + string3 + "[pP][+-]?\\d++[fFdD]?";
        String string5 = "[+-]?(?:NaN|Infinity|" + string2 + "|" + string4 + ")";
        return Pattern.compile(string5);
    }

    @Nullable
    @CheckForNull
    @Beta
    @GwtIncompatible
    public static Double tryParse(String string) {
        if (FLOATING_POINT_PATTERN.matcher(string).matches()) {
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    static int access$000(double[] dArray, double d, int n, int n2) {
        return Doubles.indexOf(dArray, d, n, n2);
    }

    static int access$100(double[] dArray, double d, int n, int n2) {
        return Doubles.lastIndexOf(dArray, d, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class DoubleArrayAsList
    extends AbstractList<Double>
    implements RandomAccess,
    Serializable {
        final double[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        DoubleArrayAsList(double[] dArray) {
            this(dArray, 0, dArray.length);
        }

        DoubleArrayAsList(double[] dArray, int n, int n2) {
            this.array = dArray;
            this.start = n;
            this.end = n2;
        }

        @Override
        public int size() {
            return this.end - this.start;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Double get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Double && Doubles.access$000(this.array, (Double)object, this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Double && (n = Doubles.access$000(this.array, (Double)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Double && (n = Doubles.access$100(this.array, (Double)object, this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Double set(int n, Double d) {
            Preconditions.checkElementIndex(n, this.size());
            double d2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(d);
            return d2;
        }

        @Override
        public List<Double> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new DoubleArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof DoubleArrayAsList) {
                DoubleArrayAsList doubleArrayAsList = (DoubleArrayAsList)object;
                int n = this.size();
                if (doubleArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == doubleArrayAsList.array[doubleArrayAsList.start + i]) continue;
                    return true;
                }
                return false;
            }
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int n = 1;
            for (int i = this.start; i < this.end; ++i) {
                n = 31 * n + Doubles.hashCode(this.array[i]);
            }
            return n;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.size() * 12);
            stringBuilder.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                stringBuilder.append(", ").append(this.array[i]);
            }
            return stringBuilder.append(']').toString();
        }

        double[] toDoubleArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Double)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static enum LexicographicalComparator implements Comparator<double[]>
    {
        INSTANCE;


        @Override
        public int compare(double[] dArray, double[] dArray2) {
            int n = Math.min(dArray.length, dArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = Double.compare(dArray[i], dArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return dArray.length - dArray2.length;
        }

        public String toString() {
            return "Doubles.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((double[])object, (double[])object2);
        }
    }

    private static final class DoubleConverter
    extends Converter<String, Double>
    implements Serializable {
        static final DoubleConverter INSTANCE = new DoubleConverter();
        private static final long serialVersionUID = 1L;

        private DoubleConverter() {
        }

        @Override
        protected Double doForward(String string) {
            return Double.valueOf(string);
        }

        @Override
        protected String doBackward(Double d) {
            return d.toString();
        }

        public String toString() {
            return "Doubles.stringConverter()";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((Double)object);
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

