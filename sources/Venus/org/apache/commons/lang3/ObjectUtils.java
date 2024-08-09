/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.text.StrBuilder;

public class ObjectUtils {
    public static final Null NULL = new Null();

    public static <T> T defaultIfNull(T t, T t2) {
        return t != null ? t : t2;
    }

    public static <T> T firstNonNull(T ... TArray) {
        if (TArray != null) {
            for (T t : TArray) {
                if (t == null) continue;
                return t;
            }
        }
        return null;
    }

    public static boolean anyNotNull(Object ... objectArray) {
        return ObjectUtils.firstNonNull(objectArray) != null;
    }

    public static boolean allNotNull(Object ... objectArray) {
        if (objectArray == null) {
            return true;
        }
        for (Object object : objectArray) {
            if (object != null) continue;
            return true;
        }
        return false;
    }

    @Deprecated
    public static boolean equals(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        if (object == null || object2 == null) {
            return true;
        }
        return object.equals(object2);
    }

    public static boolean notEqual(Object object, Object object2) {
        return !ObjectUtils.equals(object, object2);
    }

    @Deprecated
    public static int hashCode(Object object) {
        return object == null ? 0 : object.hashCode();
    }

    @Deprecated
    public static int hashCodeMulti(Object ... objectArray) {
        int n = 1;
        if (objectArray != null) {
            for (Object object : objectArray) {
                int n2 = ObjectUtils.hashCode(object);
                n = n * 31 + n2;
            }
        }
        return n;
    }

    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        ObjectUtils.identityToString(stringBuilder, object);
        return stringBuilder.toString();
    }

    public static void identityToString(Appendable appendable, Object object) throws IOException {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        appendable.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    public static void identityToString(StrBuilder strBuilder, Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        strBuilder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    public static void identityToString(StringBuffer stringBuffer, Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        stringBuffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    public static void identityToString(StringBuilder stringBuilder, Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        stringBuilder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    @Deprecated
    public static String toString(Object object) {
        return object == null ? "" : object.toString();
    }

    @Deprecated
    public static String toString(Object object, String string) {
        return object == null ? string : object.toString();
    }

    public static <T extends Comparable<? super T>> T min(T ... TArray) {
        T t = null;
        if (TArray != null) {
            for (T t2 : TArray) {
                if (ObjectUtils.compare(t2, t, true) >= 0) continue;
                t = t2;
            }
        }
        return t;
    }

    public static <T extends Comparable<? super T>> T max(T ... TArray) {
        T t = null;
        if (TArray != null) {
            for (T t2 : TArray) {
                if (ObjectUtils.compare(t2, t, false) <= 0) continue;
                t = t2;
            }
        }
        return t;
    }

    public static <T extends Comparable<? super T>> int compare(T t, T t2) {
        return ObjectUtils.compare(t, t2, false);
    }

    public static <T extends Comparable<? super T>> int compare(T t, T t2, boolean bl) {
        if (t == t2) {
            return 1;
        }
        if (t == null) {
            return bl ? 1 : -1;
        }
        if (t2 == null) {
            return bl ? -1 : 1;
        }
        return t.compareTo(t2);
    }

    public static <T extends Comparable<? super T>> T median(T ... TArray) {
        Validate.notEmpty(TArray);
        Validate.noNullElements(TArray);
        TreeSet treeSet = new TreeSet();
        Collections.addAll(treeSet, TArray);
        Comparable comparable = (Comparable)treeSet.toArray()[(treeSet.size() - 1) / 2];
        return (T)comparable;
    }

    public static <T> T median(Comparator<T> comparator, T ... TArray) {
        Validate.notEmpty(TArray, "null/empty items", new Object[0]);
        Validate.noNullElements(TArray);
        Validate.notNull(comparator, "null comparator", new Object[0]);
        TreeSet<T> treeSet = new TreeSet<T>(comparator);
        Collections.addAll(treeSet, TArray);
        Object object = treeSet.toArray()[(treeSet.size() - 1) / 2];
        return (T)object;
    }

    /*
     * WARNING - void declaration
     */
    public static <T> T mode(T ... TArray) {
        if (ArrayUtils.isNotEmpty(TArray)) {
            void var2_4;
            HashMap<T, MutableInt> hashMap = new HashMap<T, MutableInt>(TArray.length);
            for (T t : TArray) {
                MutableInt mutableInt = (MutableInt)hashMap.get(t);
                if (mutableInt == null) {
                    hashMap.put(t, new MutableInt(1));
                    continue;
                }
                mutableInt.increment();
            }
            Object var2_3 = null;
            int n = 0;
            for (Map.Entry entry : hashMap.entrySet()) {
                int n2 = ((MutableInt)entry.getValue()).intValue();
                if (n2 == n) {
                    Object var2_5 = null;
                    continue;
                }
                if (n2 <= n) continue;
                n = n2;
                Object k = entry.getKey();
            }
            return var2_4;
        }
        return null;
    }

    public static <T> T clone(T t) {
        if (t instanceof Cloneable) {
            Object object;
            Class<?> clazz;
            if (t.getClass().isArray()) {
                clazz = t.getClass().getComponentType();
                if (!clazz.isPrimitive()) {
                    object = ((Object[])t).clone();
                } else {
                    int n = Array.getLength(t);
                    object = Array.newInstance(clazz, n);
                    while (n-- > 0) {
                        Array.set(object, n, Array.get(t, n));
                    }
                }
            } else {
                try {
                    clazz = t.getClass().getMethod("clone", new Class[0]);
                    object = ((Method)((Object)clazz)).invoke(t, new Object[0]);
                } catch (NoSuchMethodException noSuchMethodException) {
                    throw new CloneFailedException("Cloneable type " + t.getClass().getName() + " has no clone method", noSuchMethodException);
                } catch (IllegalAccessException illegalAccessException) {
                    throw new CloneFailedException("Cannot clone Cloneable type " + t.getClass().getName(), illegalAccessException);
                } catch (InvocationTargetException invocationTargetException) {
                    throw new CloneFailedException("Exception cloning Cloneable type " + t.getClass().getName(), invocationTargetException.getCause());
                }
            }
            clazz = object;
            return (T)clazz;
        }
        return null;
    }

    public static <T> T cloneIfPossible(T t) {
        T t2 = ObjectUtils.clone(t);
        return t2 == null ? t : t2;
    }

    public static boolean CONST(boolean bl) {
        return bl;
    }

    public static byte CONST(byte by) {
        return by;
    }

    public static byte CONST_BYTE(int n) throws IllegalArgumentException {
        if (n < -128 || n > 127) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + n + "]");
        }
        return (byte)n;
    }

    public static char CONST(char c) {
        return c;
    }

    public static short CONST(short s) {
        return s;
    }

    public static short CONST_SHORT(int n) throws IllegalArgumentException {
        if (n < Short.MIN_VALUE || n > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + n + "]");
        }
        return (short)n;
    }

    public static int CONST(int n) {
        return n;
    }

    public static long CONST(long l) {
        return l;
    }

    public static float CONST(float f) {
        return f;
    }

    public static double CONST(double d) {
        return d;
    }

    public static <T> T CONST(T t) {
        return t;
    }

    public static class Null
    implements Serializable {
        private static final long serialVersionUID = 7092611880189329093L;

        Null() {
        }

        private Object readResolve() {
            return NULL;
        }
    }
}

