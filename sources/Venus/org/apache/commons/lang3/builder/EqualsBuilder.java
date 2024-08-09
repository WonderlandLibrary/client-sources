/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.IDKey;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EqualsBuilder
implements Builder<Boolean> {
    private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal();
    private boolean isEquals = true;

    static Set<Pair<IDKey, IDKey>> getRegistry() {
        return REGISTRY.get();
    }

    static Pair<IDKey, IDKey> getRegisterPair(Object object, Object object2) {
        IDKey iDKey = new IDKey(object);
        IDKey iDKey2 = new IDKey(object2);
        return Pair.of(iDKey, iDKey2);
    }

    static boolean isRegistered(Object object, Object object2) {
        Set<Pair<IDKey, IDKey>> set = EqualsBuilder.getRegistry();
        Pair<IDKey, IDKey> pair = EqualsBuilder.getRegisterPair(object, object2);
        Pair<IDKey, IDKey> pair2 = Pair.of(pair.getLeft(), pair.getRight());
        return set != null && (set.contains(pair) || set.contains(pair2));
    }

    private static void register(Object object, Object object2) {
        Set<Pair<IDKey, IDKey>> set = EqualsBuilder.getRegistry();
        if (set == null) {
            set = new HashSet<Pair<IDKey, IDKey>>();
            REGISTRY.set(set);
        }
        Pair<IDKey, IDKey> pair = EqualsBuilder.getRegisterPair(object, object2);
        set.add(pair);
    }

    private static void unregister(Object object, Object object2) {
        Set<Pair<IDKey, IDKey>> set = EqualsBuilder.getRegistry();
        if (set != null) {
            Pair<IDKey, IDKey> pair = EqualsBuilder.getRegisterPair(object, object2);
            set.remove(pair);
            if (set.isEmpty()) {
                REGISTRY.remove();
            }
        }
    }

    public static boolean reflectionEquals(Object object, Object object2, Collection<String> collection) {
        return EqualsBuilder.reflectionEquals(object, object2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }

    public static boolean reflectionEquals(Object object, Object object2, String ... stringArray) {
        return EqualsBuilder.reflectionEquals(object, object2, false, null, stringArray);
    }

    public static boolean reflectionEquals(Object object, Object object2, boolean bl) {
        return EqualsBuilder.reflectionEquals(object, object2, bl, null, new String[0]);
    }

    public static boolean reflectionEquals(Object object, Object object2, boolean bl, Class<?> clazz, String ... stringArray) {
        Class<?> clazz2;
        if (object == object2) {
            return false;
        }
        if (object == null || object2 == null) {
            return true;
        }
        Class<?> clazz3 = object.getClass();
        Class<?> clazz4 = object2.getClass();
        if (clazz3.isInstance(object2)) {
            clazz2 = clazz3;
            if (!clazz4.isInstance(object)) {
                clazz2 = clazz4;
            }
        } else if (clazz4.isInstance(object)) {
            clazz2 = clazz4;
            if (!clazz3.isInstance(object2)) {
                clazz2 = clazz3;
            }
        } else {
            return true;
        }
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        try {
            if (clazz2.isArray()) {
                equalsBuilder.append(object, object2);
            } else {
                EqualsBuilder.reflectionAppend(object, object2, clazz2, equalsBuilder, bl, stringArray);
                while (clazz2.getSuperclass() != null && clazz2 != clazz) {
                    clazz2 = clazz2.getSuperclass();
                    EqualsBuilder.reflectionAppend(object, object2, clazz2, equalsBuilder, bl, stringArray);
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            return true;
        }
        return equalsBuilder.isEquals();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void reflectionAppend(Object object, Object object2, Class<?> clazz, EqualsBuilder equalsBuilder, boolean bl, String[] stringArray) {
        if (EqualsBuilder.isRegistered(object, object2)) {
            return;
        }
        try {
            EqualsBuilder.register(object, object2);
            AccessibleObject[] accessibleObjectArray = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(accessibleObjectArray, true);
            for (int i = 0; i < accessibleObjectArray.length && equalsBuilder.isEquals; ++i) {
                AccessibleObject accessibleObject = accessibleObjectArray[i];
                if (ArrayUtils.contains(stringArray, ((Field)accessibleObject).getName()) || ((Field)accessibleObject).getName().contains("$") || !bl && Modifier.isTransient(((Field)accessibleObject).getModifiers()) || Modifier.isStatic(((Field)accessibleObject).getModifiers()) || accessibleObject.isAnnotationPresent(EqualsExclude.class)) continue;
                try {
                    equalsBuilder.append(((Field)accessibleObject).get(object), ((Field)accessibleObject).get(object2));
                    continue;
                } catch (IllegalAccessException illegalAccessException) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        } finally {
            EqualsBuilder.unregister(object, object2);
        }
    }

    public EqualsBuilder appendSuper(boolean bl) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = bl;
        return this;
    }

    public EqualsBuilder append(Object object, Object object2) {
        if (!this.isEquals) {
            return this;
        }
        if (object == object2) {
            return this;
        }
        if (object == null || object2 == null) {
            this.setEquals(true);
            return this;
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isArray()) {
            this.isEquals = object.equals(object2);
        } else {
            this.appendArray(object, object2);
        }
        return this;
    }

    private void appendArray(Object object, Object object2) {
        if (object.getClass() != object2.getClass()) {
            this.setEquals(true);
        } else if (object instanceof long[]) {
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
            this.append((Object[])object, (Object[])object2);
        }
    }

    public EqualsBuilder append(long l, long l2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = l == l2;
        return this;
    }

    public EqualsBuilder append(int n, int n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = n == n2;
        return this;
    }

    public EqualsBuilder append(short s, short s2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = s == s2;
        return this;
    }

    public EqualsBuilder append(char c, char c2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = c == c2;
        return this;
    }

    public EqualsBuilder append(byte by, byte by2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = by == by2;
        return this;
    }

    public EqualsBuilder append(double d, double d2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Double.doubleToLongBits(d), Double.doubleToLongBits(d2));
    }

    public EqualsBuilder append(float f, float f2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Float.floatToIntBits(f), Float.floatToIntBits(f2));
    }

    public EqualsBuilder append(boolean bl, boolean bl2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = bl == bl2;
        return this;
    }

    public EqualsBuilder append(Object[] objectArray, Object[] objectArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (objectArray == objectArray2) {
            return this;
        }
        if (objectArray == null || objectArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (objectArray.length != objectArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < objectArray.length && this.isEquals; ++i) {
            this.append(objectArray[i], objectArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(long[] lArray, long[] lArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (lArray == lArray2) {
            return this;
        }
        if (lArray == null || lArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (lArray.length != lArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < lArray.length && this.isEquals; ++i) {
            this.append(lArray[i], lArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(int[] nArray, int[] nArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (nArray == nArray2) {
            return this;
        }
        if (nArray == null || nArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (nArray.length != nArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < nArray.length && this.isEquals; ++i) {
            this.append(nArray[i], nArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(short[] sArray, short[] sArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (sArray == sArray2) {
            return this;
        }
        if (sArray == null || sArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (sArray.length != sArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < sArray.length && this.isEquals; ++i) {
            this.append(sArray[i], sArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(char[] cArray, char[] cArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (cArray == cArray2) {
            return this;
        }
        if (cArray == null || cArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (cArray.length != cArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < cArray.length && this.isEquals; ++i) {
            this.append(cArray[i], cArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(byte[] byArray, byte[] byArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (byArray == byArray2) {
            return this;
        }
        if (byArray == null || byArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (byArray.length != byArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < byArray.length && this.isEquals; ++i) {
            this.append(byArray[i], byArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(double[] dArray, double[] dArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (dArray == dArray2) {
            return this;
        }
        if (dArray == null || dArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (dArray.length != dArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < dArray.length && this.isEquals; ++i) {
            this.append(dArray[i], dArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(float[] fArray, float[] fArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (fArray == fArray2) {
            return this;
        }
        if (fArray == null || fArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (fArray.length != fArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < fArray.length && this.isEquals; ++i) {
            this.append(fArray[i], fArray2[i]);
        }
        return this;
    }

    public EqualsBuilder append(boolean[] blArray, boolean[] blArray2) {
        if (!this.isEquals) {
            return this;
        }
        if (blArray == blArray2) {
            return this;
        }
        if (blArray == null || blArray2 == null) {
            this.setEquals(true);
            return this;
        }
        if (blArray.length != blArray2.length) {
            this.setEquals(true);
            return this;
        }
        for (int i = 0; i < blArray.length && this.isEquals; ++i) {
            this.append(blArray[i], blArray2[i]);
        }
        return this;
    }

    public boolean isEquals() {
        return this.isEquals;
    }

    @Override
    public Boolean build() {
        return this.isEquals();
    }

    protected void setEquals(boolean bl) {
        this.isEquals = bl;
    }

    public void reset() {
        this.isEquals = true;
    }

    @Override
    public Object build() {
        return this.build();
    }
}

