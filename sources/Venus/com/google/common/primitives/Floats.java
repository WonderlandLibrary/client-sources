/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Doubles;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Floats {
    public static final int BYTES = 4;

    private Floats() {
    }

    public static int hashCode(float f) {
        return Float.valueOf(f).hashCode();
    }

    public static int compare(float f, float f2) {
        return Float.compare(f, f2);
    }

    public static boolean isFinite(float f) {
        return Float.NEGATIVE_INFINITY < f & f < Float.POSITIVE_INFINITY;
    }

    public static boolean contains(float[] fArray, float f) {
        for (float f2 : fArray) {
            if (f2 != f) continue;
            return false;
        }
        return true;
    }

    public static int indexOf(float[] fArray, float f) {
        return Floats.indexOf(fArray, f, 0, fArray.length);
    }

    private static int indexOf(float[] fArray, float f, int n, int n2) {
        for (int i = n; i < n2; ++i) {
            if (fArray[i] != f) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(float[] fArray, float[] fArray2) {
        Preconditions.checkNotNull(fArray, "array");
        Preconditions.checkNotNull(fArray2, "target");
        if (fArray2.length == 0) {
            return 1;
        }
        block0: for (int i = 0; i < fArray.length - fArray2.length + 1; ++i) {
            for (int j = 0; j < fArray2.length; ++j) {
                if (fArray[i + j] != fArray2[j]) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(float[] fArray, float f) {
        return Floats.lastIndexOf(fArray, f, 0, fArray.length);
    }

    private static int lastIndexOf(float[] fArray, float f, int n, int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (fArray[i] != f) continue;
            return i;
        }
        return 1;
    }

    public static float min(float ... fArray) {
        Preconditions.checkArgument(fArray.length > 0);
        float f = fArray[0];
        for (int i = 1; i < fArray.length; ++i) {
            f = Math.min(f, fArray[i]);
        }
        return f;
    }

    public static float max(float ... fArray) {
        Preconditions.checkArgument(fArray.length > 0);
        float f = fArray[0];
        for (int i = 1; i < fArray.length; ++i) {
            f = Math.max(f, fArray[i]);
        }
        return f;
    }

    @Beta
    public static float constrainToRange(float f, float f2, float f3) {
        Preconditions.checkArgument(f2 <= f3, "min (%s) must be less than or equal to max (%s)", (Object)Float.valueOf(f2), (Object)Float.valueOf(f3));
        return Math.min(Math.max(f, f2), f3);
    }

    public static float[] concat(float[] ... fArray) {
        int n = 0;
        for (float[] fArray2 : fArray) {
            n += fArray2.length;
        }
        float[] fArray3 = new float[n];
        int n2 = 0;
        for (float[] fArray4 : fArray) {
            System.arraycopy(fArray4, 0, fArray3, n2, fArray4.length);
            n2 += fArray4.length;
        }
        return fArray3;
    }

    @Beta
    public static Converter<String, Float> stringConverter() {
        return FloatConverter.INSTANCE;
    }

    public static float[] ensureCapacity(float[] fArray, int n, int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return fArray.length < n ? Arrays.copyOf(fArray, n + n2) : fArray;
    }

    public static String join(String string, float ... fArray) {
        Preconditions.checkNotNull(string);
        if (fArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(fArray.length * 12);
        stringBuilder.append(fArray[0]);
        for (int i = 1; i < fArray.length; ++i) {
            stringBuilder.append(string).append(fArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<float[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static float[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof FloatArrayAsList) {
            return ((FloatArrayAsList)collection).toFloatArray();
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        float[] fArray = new float[n];
        for (int i = 0; i < n; ++i) {
            fArray[i] = ((Number)Preconditions.checkNotNull(objectArray[i])).floatValue();
        }
        return fArray;
    }

    public static List<Float> asList(float ... fArray) {
        if (fArray.length == 0) {
            return Collections.emptyList();
        }
        return new FloatArrayAsList(fArray);
    }

    @Nullable
    @CheckForNull
    @Beta
    @GwtIncompatible
    public static Float tryParse(String string) {
        if (Doubles.FLOATING_POINT_PATTERN.matcher(string).matches()) {
            try {
                return Float.valueOf(Float.parseFloat(string));
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return null;
    }

    static int access$000(float[] fArray, float f, int n, int n2) {
        return Floats.indexOf(fArray, f, n, n2);
    }

    static int access$100(float[] fArray, float f, int n, int n2) {
        return Floats.lastIndexOf(fArray, f, n, n2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @GwtCompatible
    private static class FloatArrayAsList
    extends AbstractList<Float>
    implements RandomAccess,
    Serializable {
        final float[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;

        FloatArrayAsList(float[] fArray) {
            this(fArray, 0, fArray.length);
        }

        FloatArrayAsList(float[] fArray, int n, int n2) {
            this.array = fArray;
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
        public Float get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Float.valueOf(this.array[this.start + n]);
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof Float && Floats.access$000(this.array, ((Float)object).floatValue(), this.start, this.end) != -1;
        }

        @Override
        public int indexOf(Object object) {
            int n;
            if (object instanceof Float && (n = Floats.access$000(this.array, ((Float)object).floatValue(), this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n;
            if (object instanceof Float && (n = Floats.access$100(this.array, ((Float)object).floatValue(), this.start, this.end)) >= 0) {
                return n - this.start;
            }
            return 1;
        }

        @Override
        public Float set(int n, Float f) {
            Preconditions.checkElementIndex(n, this.size());
            float f2 = this.array[this.start + n];
            this.array[this.start + n] = Preconditions.checkNotNull(f).floatValue();
            return Float.valueOf(f2);
        }

        @Override
        public List<Float> subList(int n, int n2) {
            int n3 = this.size();
            Preconditions.checkPositionIndexes(n, n2, n3);
            if (n == n2) {
                return Collections.emptyList();
            }
            return new FloatArrayAsList(this.array, this.start + n, this.start + n2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object == this) {
                return false;
            }
            if (object instanceof FloatArrayAsList) {
                FloatArrayAsList floatArrayAsList = (FloatArrayAsList)object;
                int n = this.size();
                if (floatArrayAsList.size() != n) {
                    return true;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.array[this.start + i] == floatArrayAsList.array[floatArrayAsList.start + i]) continue;
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
                n = 31 * n + Floats.hashCode(this.array[i]);
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

        float[] toFloatArray() {
            return Arrays.copyOfRange(this.array, this.start, this.end);
        }

        @Override
        public Object set(int n, Object object) {
            return this.set(n, (Float)object);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static enum LexicographicalComparator implements Comparator<float[]>
    {
        INSTANCE;


        @Override
        public int compare(float[] fArray, float[] fArray2) {
            int n = Math.min(fArray.length, fArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = Float.compare(fArray[i], fArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return fArray.length - fArray2.length;
        }

        public String toString() {
            return "Floats.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((float[])object, (float[])object2);
        }
    }

    private static final class FloatConverter
    extends Converter<String, Float>
    implements Serializable {
        static final FloatConverter INSTANCE = new FloatConverter();
        private static final long serialVersionUID = 1L;

        private FloatConverter() {
        }

        @Override
        protected Float doForward(String string) {
            return Float.valueOf(string);
        }

        @Override
        protected String doBackward(Float f) {
            return f.toString();
        }

        public String toString() {
            return "Floats.stringConverter()";
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((Float)object);
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

