/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Comparator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompareToBuilder
implements Builder<Integer> {
    private int comparison = 0;

    public static int reflectionCompare(Object object, Object object2) {
        return CompareToBuilder.reflectionCompare(object, object2, false, null, new String[0]);
    }

    public static int reflectionCompare(Object object, Object object2, boolean bl) {
        return CompareToBuilder.reflectionCompare(object, object2, bl, null, new String[0]);
    }

    public static int reflectionCompare(Object object, Object object2, Collection<String> collection) {
        return CompareToBuilder.reflectionCompare(object, object2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }

    public static int reflectionCompare(Object object, Object object2, String ... stringArray) {
        return CompareToBuilder.reflectionCompare(object, object2, false, null, stringArray);
    }

    public static int reflectionCompare(Object object, Object object2, boolean bl, Class<?> clazz, String ... stringArray) {
        if (object == object2) {
            return 1;
        }
        if (object == null || object2 == null) {
            throw new NullPointerException();
        }
        Class<?> clazz2 = object.getClass();
        if (!clazz2.isInstance(object2)) {
            throw new ClassCastException();
        }
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        CompareToBuilder.reflectionAppend(object, object2, clazz2, compareToBuilder, bl, stringArray);
        while (clazz2.getSuperclass() != null && clazz2 != clazz) {
            clazz2 = clazz2.getSuperclass();
            CompareToBuilder.reflectionAppend(object, object2, clazz2, compareToBuilder, bl, stringArray);
        }
        return compareToBuilder.toComparison();
    }

    private static void reflectionAppend(Object object, Object object2, Class<?> clazz, CompareToBuilder compareToBuilder, boolean bl, String[] stringArray) {
        AccessibleObject[] accessibleObjectArray = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(accessibleObjectArray, true);
        for (int i = 0; i < accessibleObjectArray.length && compareToBuilder.comparison == 0; ++i) {
            AccessibleObject accessibleObject = accessibleObjectArray[i];
            if (ArrayUtils.contains(stringArray, ((Field)accessibleObject).getName()) || ((Field)accessibleObject).getName().contains("$") || !bl && Modifier.isTransient(((Field)accessibleObject).getModifiers()) || Modifier.isStatic(((Field)accessibleObject).getModifiers())) continue;
            try {
                compareToBuilder.append(((Field)accessibleObject).get(object), ((Field)accessibleObject).get(object2));
                continue;
            } catch (IllegalAccessException illegalAccessException) {
                throw new InternalError("Unexpected IllegalAccessException");
            }
        }
    }

    public CompareToBuilder appendSuper(int n) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = n;
        return this;
    }

    public CompareToBuilder append(Object object, Object object2) {
        return this.append(object, object2, null);
    }

    public CompareToBuilder append(Object object, Object object2, Comparator<?> comparator) {
        if (this.comparison != 0) {
            return this;
        }
        if (object == object2) {
            return this;
        }
        if (object == null) {
            this.comparison = -1;
            return this;
        }
        if (object2 == null) {
            this.comparison = 1;
            return this;
        }
        if (object.getClass().isArray()) {
            this.appendArray(object, object2, comparator);
        } else if (comparator == null) {
            Comparable comparable = (Comparable)object;
            this.comparison = comparable.compareTo(object2);
        } else {
            Comparator<?> comparator2 = comparator;
            this.comparison = comparator2.compare(object, object2);
        }
        return this;
    }

    private void appendArray(Object object, Object object2, Comparator<?> comparator) {
        if (object instanceof long[]) {
            this.append((long[])object, (long[])object2);
        } else if (object instanceof int[]) {
            this.append((int[])object, (int[])object2);
        } else if (object instanceof short[]) {
            this.append((short[])object, (short[])object2);
        } else if (object instanceof char[]) {
            this.append((char[])object, (char[])object2);
        } else if (object instanceof byte[]) {
            this.append((byte[])object, (byte[])object2);
        } else if (object instanceof double[]) {
            this.append((double[])object, (double[])object2);
        } else if (object instanceof float[]) {
            this.append((float[])object, (float[])object2);
        } else if (object instanceof boolean[]) {
            this.append((boolean[])object, (boolean[])object2);
        } else {
            this.append((Object[])object, (Object[])object2, comparator);
        }
    }

    public CompareToBuilder append(long l, long l2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = l < l2 ? -1 : (l > l2 ? 1 : 0);
        return this;
    }

    public CompareToBuilder append(int n, int n2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = n < n2 ? -1 : (n > n2 ? 1 : 0);
        return this;
    }

    public CompareToBuilder append(short s, short s2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = s < s2 ? -1 : (s > s2 ? 1 : 0);
        return this;
    }

    public CompareToBuilder append(char c, char c2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = c < c2 ? -1 : (c > c2 ? 1 : 0);
        return this;
    }

    public CompareToBuilder append(byte by, byte by2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = by < by2 ? -1 : (by > by2 ? 1 : 0);
        return this;
    }

    public CompareToBuilder append(double d, double d2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = Double.compare(d, d2);
        return this;
    }

    public CompareToBuilder append(float f, float f2) {
        if (this.comparison != 0) {
            return this;
        }
        this.comparison = Float.compare(f, f2);
        return this;
    }

    public CompareToBuilder append(boolean bl, boolean bl2) {
        if (this.comparison != 0) {
            return this;
        }
        if (bl == bl2) {
            return this;
        }
        this.comparison = !bl ? -1 : 1;
        return this;
    }

    public CompareToBuilder append(Object[] objectArray, Object[] objectArray2) {
        return this.append(objectArray, objectArray2, (Comparator<?>)null);
    }

    public CompareToBuilder append(Object[] objectArray, Object[] objectArray2, Comparator<?> comparator) {
        if (this.comparison != 0) {
            return this;
        }
        if (objectArray == objectArray2) {
            return this;
        }
        if (objectArray == null) {
            this.comparison = -1;
            return this;
        }
        if (objectArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (objectArray.length != objectArray2.length) {
            this.comparison = objectArray.length < objectArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < objectArray.length && this.comparison == 0; ++i) {
            this.append(objectArray[i], objectArray2[i], comparator);
        }
        return this;
    }

    public CompareToBuilder append(long[] lArray, long[] lArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (lArray == lArray2) {
            return this;
        }
        if (lArray == null) {
            this.comparison = -1;
            return this;
        }
        if (lArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (lArray.length != lArray2.length) {
            this.comparison = lArray.length < lArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < lArray.length && this.comparison == 0; ++i) {
            this.append(lArray[i], lArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(int[] nArray, int[] nArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (nArray == nArray2) {
            return this;
        }
        if (nArray == null) {
            this.comparison = -1;
            return this;
        }
        if (nArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (nArray.length != nArray2.length) {
            this.comparison = nArray.length < nArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < nArray.length && this.comparison == 0; ++i) {
            this.append(nArray[i], nArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(short[] sArray, short[] sArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (sArray == sArray2) {
            return this;
        }
        if (sArray == null) {
            this.comparison = -1;
            return this;
        }
        if (sArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (sArray.length != sArray2.length) {
            this.comparison = sArray.length < sArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < sArray.length && this.comparison == 0; ++i) {
            this.append(sArray[i], sArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(char[] cArray, char[] cArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (cArray == cArray2) {
            return this;
        }
        if (cArray == null) {
            this.comparison = -1;
            return this;
        }
        if (cArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (cArray.length != cArray2.length) {
            this.comparison = cArray.length < cArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < cArray.length && this.comparison == 0; ++i) {
            this.append(cArray[i], cArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(byte[] byArray, byte[] byArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (byArray == byArray2) {
            return this;
        }
        if (byArray == null) {
            this.comparison = -1;
            return this;
        }
        if (byArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (byArray.length != byArray2.length) {
            this.comparison = byArray.length < byArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < byArray.length && this.comparison == 0; ++i) {
            this.append(byArray[i], byArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(double[] dArray, double[] dArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (dArray == dArray2) {
            return this;
        }
        if (dArray == null) {
            this.comparison = -1;
            return this;
        }
        if (dArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (dArray.length != dArray2.length) {
            this.comparison = dArray.length < dArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < dArray.length && this.comparison == 0; ++i) {
            this.append(dArray[i], dArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(float[] fArray, float[] fArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (fArray == fArray2) {
            return this;
        }
        if (fArray == null) {
            this.comparison = -1;
            return this;
        }
        if (fArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (fArray.length != fArray2.length) {
            this.comparison = fArray.length < fArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < fArray.length && this.comparison == 0; ++i) {
            this.append(fArray[i], fArray2[i]);
        }
        return this;
    }

    public CompareToBuilder append(boolean[] blArray, boolean[] blArray2) {
        if (this.comparison != 0) {
            return this;
        }
        if (blArray == blArray2) {
            return this;
        }
        if (blArray == null) {
            this.comparison = -1;
            return this;
        }
        if (blArray2 == null) {
            this.comparison = 1;
            return this;
        }
        if (blArray.length != blArray2.length) {
            this.comparison = blArray.length < blArray2.length ? -1 : 1;
            return this;
        }
        for (int i = 0; i < blArray.length && this.comparison == 0; ++i) {
            this.append(blArray[i], blArray2[i]);
        }
        return this;
    }

    public int toComparison() {
        return this.comparison;
    }

    @Override
    public Integer build() {
        return this.toComparison();
    }

    @Override
    public Object build() {
        return this.build();
    }
}

